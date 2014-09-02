package cc.balloonbros.balanceball.task;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

import cc.balloonbros.balanceball.TaskPriority;
import cc.balloonbros.balanceball.lib.TaskBase;
import cc.balloonbros.balanceball.lib.Updateable;

public class Orientation extends TaskBase implements Updateable, SensorEventListener {
    private float[] magneticFieldValues = new float[3];
    private float[] accelerometerValues = new float[3];
    private float[] orientationValues   = new float[3];
    private float[] inR  = new float[16];
    private float[] outR = new float[16];
    private float[] I    = new float[16];

    @Override
    public void onRegistered() {
        SensorManager manager = getGame().getSensorManager();
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor: sensors) {
            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD ||
                    sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }

    @Override
    public void onUpdate() {
        Ball ball = (Ball)find(TaskPriority.BALL);
        ball.move((int)Math.floor(Math.toDegrees(orientationValues[2])) / 10, (int)Math.floor(Math.toDegrees(-orientationValues[1])) / 10);

        DebugOutput debug = (DebugOutput)find(TaskPriority.DEBUG);
        sendMessage(debug, orientationValues);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD: {
                magneticFieldValues = sensorEvent.values.clone();
                break;
            }
            case Sensor.TYPE_ACCELEROMETER: {
                accelerometerValues = sensorEvent.values.clone();
                break;
            }
        }

        if (magneticFieldValues != null && accelerometerValues != null) {
            SensorManager.getRotationMatrix(inR, I, accelerometerValues, magneticFieldValues);
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Y, outR);
            SensorManager.getOrientation(outR, orientationValues);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
