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
import cc.balloonbros.balanceball.lib.TaskEventListener;

public class DebugOutput extends TaskBase implements Drawable, TaskEventListener {
    private Paint mPaint = new Paint();
    private float[] mOrientationValues;

    @Override
    public void onRegistered() {
        mPaint.setColor(Color.WHITE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText("task count: " + getTaskManager().getTaskCount(), 10, 10, mPaint);
        canvas.drawText("FPS: " + getGame().getFps(), 10, 20, mPaint);
        if (mOrientationValues != null) {
            canvas.drawText("z-axis: " + Math.floor(Math.toDegrees(mOrientationValues[0])), 10, 30, mPaint);
            canvas.drawText("x-axis: " + Math.floor(Math.toDegrees(mOrientationValues[1])), 10, 40, mPaint);
            canvas.drawText("y-axis: " + Math.floor(Math.toDegrees(mOrientationValues[2])), 10, 50, mPaint);
        }
    }

    @Override
    public void onMessage(Object message) {
        mOrientationValues = (float[])message;
    }
}
