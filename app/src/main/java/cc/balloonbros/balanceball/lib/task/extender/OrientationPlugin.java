package cc.balloonbros.balanceball.lib.task.extender;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.TaskPlugin;

public class OrientationPlugin extends TaskPlugin implements SensorEventListener {
    /** 端末の傾きの計算をするために必要な変数 */
    private float[] mMagneticFieldValues = null;
    private float[] mAccelerometerValues = null;
    private float[] inR = new float[16];
    private float[] outR = new float[16];
    private float[] I = new float[16];
    private float[] mOrientationValues = new float[3];

    private SensorManager mSensorManager;

    @Override
    protected void onRegister() {
        onEnterLoop();
    }

    @Override
    protected void onEnterLoop() {
        if (mSensorManager != null) {
            return;
        }

        mSensorManager = (SensorManager) getTask().getGame().getContext().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor: sensors) {
            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD || sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }

    @Override
    protected void onLeaveLoop() {
        mSensorManager = (SensorManager) getTask().getGame().getContext().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onKilled() {
        onLeaveLoop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD: {
                mMagneticFieldValues = sensorEvent.values;
                break;
            }
            case Sensor.TYPE_ACCELEROMETER: {
                mAccelerometerValues = sensorEvent.values;
                break;
            }
        }

        // 傾きを計算する
        if (mMagneticFieldValues != null && mAccelerometerValues != null) {
            SensorManager.getRotationMatrix(inR, I, mAccelerometerValues, mMagneticFieldValues);
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Y, outR);
            SensorManager.getOrientation(outR, mOrientationValues);

            AbstractTask task = getTask();
            if (task instanceof Orientationable) {
                ((Orientationable) task).onOriented(mOrientationValues[0], mOrientationValues[1], mOrientationValues[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}
