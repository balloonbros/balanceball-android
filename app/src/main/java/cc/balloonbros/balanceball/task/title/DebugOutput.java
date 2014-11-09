package cc.balloonbros.balanceball.task.title;

import android.graphics.Color;
import android.graphics.Paint;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.message.IntegerMessage;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;
import cc.balloonbros.balanceball.lib.task.message.TaskMessageListener;
import cc.balloonbros.balanceball.task.message.OrientationMessage;

/**
 * デバッグ用タスク
 */
public class DebugOutput extends AbstractTask implements Drawable {
    private Paint mPaint = new Paint();

    @Override
    public void onRegister() {
        setPriority(_.i(R.integer.priority_debug));
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(130);
    }

    @Override
    public void onDraw(Surface surface) {
        surface.getCanvas().drawText("RealFPS: " + getGame().getRealFps(), 30, 70, mPaint);
    }
}
