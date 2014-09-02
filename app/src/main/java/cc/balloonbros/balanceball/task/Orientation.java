package cc.balloonbros.balanceball.task;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

import cc.balloonbros.balanceball.TaskPriority;
import cc.balloonbros.balanceball.lib.TaskBase;
import cc.balloonbros.balanceball.lib.Updateable;

/**
 * 端末の傾きを検知するタスク
 */
public class Orientation extends TaskBase implements Updateable, SensorEventListener {
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

    @Override
    public void onRegistered() {
        SensorManager manager = getGame().getSensorManager();

        // 傾きの計算に地磁気センサーと加速度センサーを使うのでイベントを登録
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor: sensors) {
            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD || sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }

    @Override
    public void onUpdate() {
        // 傾きに合わせてボールを動かす
        Ball ball = (Ball)find(TaskPriority.BALL);
        ball.move((int)Math.floor(Math.toDegrees(mOrientationValues[2])) / 10, (int)Math.floor(Math.toDegrees(-mOrientationValues[1])) / 10);

        DebugOutput debug = (DebugOutput)find(TaskPriority.DEBUG);
        sendMessage(debug, mOrientationValues);
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
