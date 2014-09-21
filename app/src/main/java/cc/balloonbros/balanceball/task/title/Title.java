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
    private Paint mTitlePaint = null;
    private Paint mBasePaint = new Paint();
    private Point mTitlePosition = new Point();

    private Paint createTitlePaint() {
        Typeface typeface = Typeface.createFromAsset(getGame().getContext().getAssets(), "fonts/opensans-bold.ttf");

        Paint p = new Paint();
        p.setColor(Color.rgb(0xfc, 0x50, 0x41));
        p.setTextSize(66.0F);
        p.setTextAlign(Paint.Align.CENTER);
        p.setAntiAlias(true);
        p.setTypeface(typeface);

        return p;
    }

    private Paint createBasePaint() {
        Typeface typeface = Typeface.createFromAsset(getGame().getContext().getAssets(), "fonts/opensans-light.ttf");

        Paint p = new Paint();
        p.setColor(Color.GRAY);
        p.setTextSize(20.0F);
        p.setTextAlign(Paint.Align.CENTER);
        p.setAntiAlias(true);
        p.setTypeface(typeface);

        return p;
    }
    @Override
    public void onRegistered() {
        mTitlePaint = createTitlePaint();
        mBasePaint  = createBasePaint();

        Point displaySize = getDisplaySize();
        float x = displaySize.x / 2;
        float y = displaySize.y / 3;
        mTitlePosition.set((int) x, (int) y);
    }

    @Override
    public void onUpdate() {  }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(getString(R.string.game_start_label), mTitlePosition.x, 300, mBasePaint);
        canvas.drawText(getString(R.string.app_name), mTitlePosition.x, mTitlePosition.y, mTitlePaint);
    }
}
