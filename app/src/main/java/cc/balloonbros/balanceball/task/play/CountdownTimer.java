package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.DrawableTask;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;

public class CountdownTimer extends DrawableTask implements TimerEventListener {
    private int mRestTime = _.i(R.integer.game_time);
    private Paint mPaint = new Paint();

    @Override
    public void onRegistered() {
        super.onRegistered();
        setFrameInterval((int)getFps(), this);

        mPaint.setColor(Color.rgb(0xe5, 0x40, 0x73));
        mPaint.setTextSize(36F);
        mPaint.setTypeface(getFont(_.s(R.string.open_sans_light)));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onTimer() {
        mRestTime--;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(String.valueOf(mRestTime), getDisplaySize().x / 2, getDisplaySize().y - 100, mPaint);
    }
}
