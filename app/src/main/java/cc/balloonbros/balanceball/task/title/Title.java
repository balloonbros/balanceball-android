package cc.balloonbros.balanceball.task.title;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.task.DrawableTask;

/**
 * タイトルタスク
 */
public class Title extends DrawableTask {
    private Paint mPaint = new Paint();
    private Point mTextPosition = new Point();

    @Override
    public void onRegistered() {
        Typeface typeface = Typeface.createFromAsset(getGame().getContext().getAssets(), "fonts/opensans-bold.ttf");

        mPaint.setColor(Color.rgb(0xfc, 0x50, 0x41));
        mPaint.setTextSize(66.0F);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
        mPaint.setTypeface(typeface);

        Point displaySize = getDisplaySize();
        float x = displaySize.x / 2;
        float y = displaySize.y / 3;
        mTextPosition.set((int)x, (int)y);
    }

    @Override
    public void onUpdate() {  }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(getString(R.string.app_name), mTextPosition.x, mTextPosition.y, mPaint);
    }
}
