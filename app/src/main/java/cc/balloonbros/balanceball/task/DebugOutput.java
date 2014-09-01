package cc.balloonbros.balanceball.task;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

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
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText("task count: " + getTaskManager().getTaskCount(), 10, 10, mPaint);
        canvas.drawText("FPS: " + getGame().getFps(), 10, 20, mPaint);
        if (orientationValues != null) {
            canvas.drawText("z-axis: " + orientationValues[0], 10, 30, mPaint);
            canvas.drawText("x-axis: " + orientationValues[1], 10, 40, mPaint);
            canvas.drawText("y-axis: " + orientationValues[2], 10, 50, mPaint);
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
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR);
            SensorManager.getOrientation(outR, orientationValues);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
