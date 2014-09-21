package cc.balloonbros.balanceball.task.title;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.MotionEvent;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.TaskFunction;
import cc.balloonbros.balanceball.lib.task.basic.TouchTask;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.scene.PlayScene;

/**
 * タイトルタスク
 */
public class Title extends TouchTask implements Drawable, TimerEventListener {
    private Paint mTitlePaint = null;
    private Paint mBasePaint = new Paint();
    private Point mTitlePosition = new Point();
    private int mAlpha = 255;

    private Paint createTitlePaint() {
        Paint p = new Paint();
        p.setColor(Color.rgb(0xfc, 0x50, 0x41));
        p.setTextSize(66.0F);
        p.setTextAlign(Paint.Align.CENTER);
        p.setAntiAlias(true);
        p.setTypeface(getFont(_.s(R.string.open_sans_bold)));

        return p;
    }

    private Paint createBasePaint() {
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        p.setTextSize(20.0F);
        p.setTextAlign(Paint.Align.CENTER);
        p.setAntiAlias(true);
        p.setTypeface(getFont(_.s(R.string.open_sans_light)));

        return p;
    }

    @Override
    public void onRegistered() {
        super.onRegistered();
        setPriority(_.i(R.integer.priority_title));

        mTitlePaint = createTitlePaint();
        mBasePaint  = createBasePaint();

        Point displaySize = getDisplaySize();
        float x = displaySize.x / 2;
        float y = displaySize.y / 3;
        mTitlePosition.set((int) x, (int) y);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        changeScene(new PlayScene());
        return false;
    }

    @Override
    public void update() {
        mAlpha -= 5;
        if (mAlpha < 0) {
            mAlpha = 255;
            changeTask(null);
            setTimer(500, this);
        }
    }

    @Override
    public void onTimer() {
        changeTask(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        mBasePaint.setAlpha(mAlpha);
        canvas.drawText(_.s(R.string.game_start_label), mTitlePosition.x, 300, mBasePaint);
        canvas.drawText(_.s(R.string.app_name), mTitlePosition.x, mTitlePosition.y, mTitlePaint);
    }
}
