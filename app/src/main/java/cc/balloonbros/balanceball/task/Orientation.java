package cc.balloonbros.balanceball.task;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

import cc.balloonbros.balanceball.TaskPriority;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.Updateable;
import cc.balloonbros.balanceball.task.message.IntegerMessage;
import cc.balloonbros.balanceball.task.message.OrientationMessage;

/**
 * 端末の傾きを検知するタスク
 */
public class Orientation extends AbstractTask implements Updateable, SensorEventListener {
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
     * ボールの加速度
     */
    private int mAcceleration = 0;
    private int mSpeed = 0;

    @Override
    public void onRegistered() {
        mSensorManager = getGame().getSensorManager();
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
        Ball ball = (Ball)find(TaskPriority.BALL);
        int divider = 2;
        int dx = (int)Math.floor(Math.toDegrees(mOrientationValues[2])) / divider;
        int dy = (int)Math.floor(Math.toDegrees(mOrientationValues[1])) / divider;

        // y軸方向の傾きは(2Dではx軸方向の移動に使う)
        //   1. 右に傾けた場合はプラス
        //   2. 左に傾けた場合はマイナス
        //   3. -180〜180の値を取る
        //   4. 画面を上方向にして水平にしたら0
        //   5. 左側に傾けていって画面が下側に向いて水平になれば-180
        //   6. 右側に傾けていって画面が下側に向いて水平になれば180
        //   7. 画面を下方向にした場合-180と180が一気に切り替わる
        if (ball.isBorderLeftEdge() || ball.isBorderRightEdge()) {
            mSpeed = 0;
        }
        mAcceleration = (int)(Math.floor(Math.toDegrees(mOrientationValues[2])) / 3);
        mSpeed += mAcceleration;
        int distance = mSpeed / 10;

        if (distance == 0) {
            if (ball.isBorderLeftEdge() && mAcceleration > 0) {
                distance = 1;
            } else if (ball.isBorderRightEdge() && mAcceleration < 0) {
                distance = -1;
            }
        }

        ball.move(distance, 0);

        // デバッグ用に現在の傾きを画面に表示する
        DebugOutput debug = (DebugOutput)find(TaskPriority.DEBUG);
        sendMessage(debug, new OrientationMessage(mOrientationValues));
        sendMessage(debug, new IntegerMessage("speed", mSpeed));
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
