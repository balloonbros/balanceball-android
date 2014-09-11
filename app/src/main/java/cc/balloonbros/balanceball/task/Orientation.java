package cc.balloonbros.balanceball.task;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

import cc.balloonbros.balanceball.BalanceBall;
import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.task.message.IntegerMessage;
import cc.balloonbros.balanceball.task.message.OrientationMessage;

/**
 * 端末の傾きを検知するタスク
 */
public class Orientation extends AbstractTask implements SensorEventListener {
    private SensorManager mSensorManager = null;

    /**
     * 端末の傾きの計算をするために必要な変数
     */
    private float[] mMagneticFieldValues = new float[3];
    private float[] mAccelerometerValues = new float[3];
    private float[] inR  = new float[16];
    private float[] outR = new float[16];
    private float[] I    = new float[16];

    /**
     * 端末の傾き
     */
    private float[] mOrientationValues = new float[3];

    /**
     * x方向とy方向のボールの移動スピード
     */
    private int[] mSpeeds = new int[2];

    @Override
    public void onRegistered() {
        mSensorManager = ((BalanceBall)getGame()).getSensorManager();
    }

    @Override
    public void onEnterLoop() {
        // 傾きの計算に地磁気センサーと加速度センサーを使うのでイベントを登録
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor: sensors) {
            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD || sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }

    @Override
    public void onLeaveLoop() {
        // ゲームループ中以外はセンサー不要なのでリスナー解除しておく
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onUpdate() {
        // 傾きに合わせてボールを動かす
        Ball ball = (Ball)find(getInteger(R.integer.priority_ball));

        // y軸方向の傾きは(2Dではx軸方向の移動に使う)
        //   1. 右に傾けた場合はプラス
        //   2. 左に傾けた場合はマイナス
        //   3. -180〜180の値を取る
        //   4. 画面を上方向にして水平にしたら0
        //   5. 左側に傾けていって画面が下側に向いて水平になれば-180
        //   6. 右側に傾けていって画面が下側に向いて水平になれば180
        //   7. 画面を下方向にした場合-180と180が一気に切り替わる
        if (ball.isBorderLeftEdge() || ball.isBorderRightEdge()) {
            mSpeeds[0] = 0;
        }
        if (ball.isBorderTopEdge() || ball.isBorderBottomEdge()) {
            mSpeeds[1] = 0;
        }
        int accelerationX = calculateAcceleration(mSpeeds[0], mOrientationValues[2], 0);
        int accelerationY = calculateAcceleration(mSpeeds[1], -mOrientationValues[1], 1);
        mSpeeds[0] += accelerationX;
        mSpeeds[1] += accelerationY;

        int dx = calculateDistanceX(ball, mSpeeds[0], accelerationX);
        int dy = calculateDistanceY(ball, mSpeeds[1], accelerationY);
        ball.move(dx, dy);

        // デバッグ用に現在の傾きを画面に表示する
        DebugOutput debug = (DebugOutput)find(getInteger(R.integer.priority_debug));
        sendMessage(debug, new OrientationMessage(mOrientationValues));
        sendMessage(debug, new IntegerMessage("speed", mSpeeds[0]));
    }

    private static final int SPEED_RATIO = 20;
    private static final int ORIENTATION_RATIO = 3;
    private boolean[] directionChanging = new boolean[2];

    private int calculateAcceleration(int currentSpeed, float orientationValue, int axis) {
        // 加速度算出に使うので、端末の傾きをラジアンから角度に変換
        int orientation = (int)Math.floor(Math.toDegrees(orientationValue));

        // 速度と傾きの符号を算出
        int signOfCurrentSpeed = (int)Math.signum(currentSpeed);
        int signOfOrientation  = (int)Math.signum(orientation);

        // 今の速度と逆方向への傾きの場合は速度を急激に落とすために加速度を多めに設定する
        if (signOfCurrentSpeed != 0 && signOfCurrentSpeed != signOfOrientation) {
            directionChanging[axis] = true;
            return orientation;
        }
        // そして速度の符号が反転して傾きと同じ方向になった場合は勢いをつけた加速度を急激に元に戻す
        if (directionChanging[axis]) {
            directionChanging[axis] = false;
            return -(currentSpeed / 2);
        }

        // 傾きが3以内の時は前のフレームの傾きの差分が2以上だったら1マス移動させるように速度を調整する

        // あまり傾いてない時は徐々に速度を落としていくように速度とは逆の符号の加速度を与える
        if (-3 < orientation && orientation < 3) {
            return -(signOfCurrentSpeed * orientation);
        }

        // 傾きをそのまま加速度にすると値が大きすぎるので適当な数で割って小さく調整する
        return orientation / ORIENTATION_RATIO;
    }

    private int calculateDistanceX(Ball ball, int currentSpeed, int acceleration) {
        int distance = currentSpeed / SPEED_RATIO;

        if (distance == 0) {
            if (ball.isBorderLeftEdge() && acceleration > 0) {
                distance = 1;
            } else if (ball.isBorderRightEdge() && acceleration < 0) {
                distance = -1;
            }
        }

        return distance;
    }

    private int calculateDistanceY(Ball ball, int currentSpeed, int acceleration) {
        int distance = currentSpeed / 40;

        if (distance == 0) {
            if (ball.isBorderTopEdge() && acceleration > 0) {
                distance = 1;
            } else if (ball.isBorderBottomEdge() && acceleration < 0) {
                distance = -1;
            }
        }

        return distance;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD: {
                mMagneticFieldValues = sensorEvent.values.clone();
                break;
            }
            case Sensor.TYPE_ACCELEROMETER: {
                mAccelerometerValues = sensorEvent.values.clone();
                break;
            }
        }

        // 傾きを計算する
        if (mMagneticFieldValues != null && mAccelerometerValues != null) {
            SensorManager.getRotationMatrix(inR, I, mAccelerometerValues, mMagneticFieldValues);
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Y, outR);
            SensorManager.getOrientation(outR, mOrientationValues);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
