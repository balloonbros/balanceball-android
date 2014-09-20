package cc.balloonbros.balanceball.task.title;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cc.balloonbros.balanceball.lib.task.DrawableTask;

/**
 * タイトルタスク
 */
public class Title extends DrawableTask {
    private Paint mPaint = new Paint();

    @Override
    public void onRegistered() {
        mPaint.setColor(Color.GRAY);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText("Balanceball", 10, 10, mPaint);
    }

    @Override
    public void onUpdate() {

    }
}
