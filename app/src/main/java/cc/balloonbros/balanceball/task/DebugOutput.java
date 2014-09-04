package cc.balloonbros.balanceball.task;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.Drawable;
import cc.balloonbros.balanceball.lib.TaskEventListener;
import cc.balloonbros.balanceball.lib.TaskMessage;
import cc.balloonbros.balanceball.task.message.OrientationMessage;

/**
 * デバッグ用タスク
 */
public class DebugOutput extends AbstractTask implements Drawable, TaskEventListener {
    private Paint mPaint = new Paint();
    private float[] mOrientationValues;

    @Override
    public void onRegistered() {
        mPaint.setColor(Color.WHITE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText("TaskCount: " + getTaskManager().getTaskCount(), 10, 10, mPaint);
        canvas.drawText("FrameCount: " + getGame().getFrameCount(), 10, 20, mPaint);
        canvas.drawText("FPS: " + getGame().getFps(), 10, 30, mPaint);
        if (mOrientationValues != null) {
            canvas.drawText("z-axis: " + Math.floor(Math.toDegrees(mOrientationValues[0])), 10, 40, mPaint);
            canvas.drawText("x-axis: " + Math.floor(Math.toDegrees(mOrientationValues[1])), 10, 50, mPaint);
            canvas.drawText("y-axis: " + Math.floor(Math.toDegrees(mOrientationValues[2])), 10, 60, mPaint);
        }
    }

    @Override
    public void onMessage(AbstractTask sender, TaskMessage message) {
        if (message instanceof OrientationMessage) {
            mOrientationValues = ((OrientationMessage)message).getOrientation();
        }
    }
}
