package cc.balloonbros.balanceball.task.play;

import android.graphics.Color;
import android.graphics.Paint;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.message.TaskMessageListener;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;
import cc.balloonbros.balanceball.lib.task.message.IntegerMessage;
import cc.balloonbros.balanceball.task.message.OrientationMessage;

/**
 * デバッグ用タスク
 */
public class DebugOutput extends AbstractTask implements Drawable, TaskMessageListener {
    private Paint mPaint = new Paint();
    private float[] mOrientationValues;
    private int mSpeed = 0;

    @Override
    public void onRegister() {
        setPriority(_.i(R.integer.priority_debug));
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);
    }

    @Override
    public void onDraw(Surface surface) {
        surface.getCanvas().drawText("TaskCount: " + getTaskManager().getTaskCount(), 10, 10, mPaint);
        surface.getCanvas().drawText("FrameCount: " + getGame().getFrameCount(), 10, 40, mPaint);
        surface.getCanvas().drawText("RealFPS: " + getGame().getRealFps(), 10, 70, mPaint);
        surface.getCanvas().drawText("FPS: " + getGame().getFps(), 10, 100, mPaint);
        if (mOrientationValues != null) {
            surface.getCanvas().drawText("z-axis: " + Math.floor(Math.toDegrees(mOrientationValues[0])), 10, 130, mPaint);
            surface.getCanvas().drawText("x-axis: " + Math.floor(Math.toDegrees(mOrientationValues[1])), 10, 160, mPaint);
            surface.getCanvas().drawText("y-axis: " + Math.floor(Math.toDegrees(mOrientationValues[2])), 10, 190, mPaint);
        }
        surface.getCanvas().drawText("Speed :" + mSpeed, 10, 220, mPaint);
    }

    @Override
    public void onMessage(AbstractTask sender, TaskMessage message) {
        if (message instanceof OrientationMessage) {
            mOrientationValues = ((OrientationMessage)message).getOrientation();
        } else if (message.getLabel().equals("speed")) {
            mSpeed = ((IntegerMessage)message).getMessage();
        }
    }
}
