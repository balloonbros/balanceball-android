package cc.balloonbros.balanceball.task;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

import cc.balloonbros.balanceball.lib.Drawable;
import cc.balloonbros.balanceball.lib.TaskBase;

public class DebugOutput extends TaskBase implements Drawable, SensorEventListener {
    private Paint mPaint = new Paint();

    private float[] magneticFieldValues = new float[3];
    private float[] accelorometerValues = new float[3];
    private float[] orientationValues   = new float[3];
    private float[] inR  = new float[16];
    private float[] outR = new float[16];
    private float[] I    = new float[16];

    @Override
    public void onRegistered() {
        mPaint.setColor(Color.WHITE);

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
    public void onDraw(Canvas canvas) {
        canvas.drawText("task count: " + getTaskManager().getTaskCount(), 10, 10, mPaint);
        canvas.drawText("FPS: " + getGame().getFps(), 10, 20, mPaint);
        if (orientationValues != null) {
            canvas.drawText("z-axis: " + Math.floor(Math.toDegrees(orientationValues[0])), 10, 30, mPaint);
            canvas.drawText("x-axis: " + Math.floor(Math.toDegrees(orientationValues[1])), 10, 40, mPaint);
            canvas.drawText("y-axis: " + Math.floor(Math.toDegrees(orientationValues[2])), 10, 50, mPaint);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD: {
                magneticFieldValues = sensorEvent.values.clone();
                break;
            }
            case Sensor.TYPE_ACCELEROMETER: {
                accelorometerValues = sensorEvent.values.clone();
                break;
            }
        }

        if (magneticFieldValues != null && accelorometerValues != null) {
            SensorManager.getRotationMatrix(inR, I, accelorometerValues, magneticFieldValues);
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Y, outR);
            SensorManager.getOrientation(outR, orientationValues);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
