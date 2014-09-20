package cc.balloonbros.balanceball.task.title;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import cc.balloonbros.balanceball.lib.task.DrawableTask;

/**
 * タイトルタスク
 */
public class Title extends DrawableTask {
    static private final String TITLE = "Balanceball";

    private Paint mPaint = new Paint();
    private Point mTextPosition = new Point();

    @Override
    public void onRegistered() {
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(36.0F);
        mPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        Point displaySize = getDisplaySize();

        float fontHeight = fm.descent - fm.ascent;
        float x = displaySize.x / 2;
        float y = (displaySize.y / 2) + (fontHeight / 2) - fm.descent;
        mTextPosition.set((int)x, (int)y);
    }

    @Override
    public void onUpdate() {  }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(TITLE, mTextPosition.x, mTextPosition.y, mPaint);
    }
}
