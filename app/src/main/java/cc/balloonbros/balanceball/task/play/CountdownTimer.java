package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;

public class CountdownTimer extends PositionableTask implements TimerEventListener {
    private int mRestTime = _.i(R.integer.game_time);
    private Paint mPaint = new Paint();

    @Override
    public void onRegister() {
        super.onRegister();
        setFrameInterval((int)getFps(), this);

        mPaint.setColor(Color.rgb(0xe5, 0x40, 0x73));
        mPaint.setTextSize(36F);
        mPaint.setTypeface(getFont(_.s(R.string.open_sans_light)));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);

        Point p = getDisplaySize();
        position(p.x / 2, p.y / 2 + 100);
    }

    @Override
    public void onTimer() {
        mRestTime--;
        if (mRestTime == 0) {
            registerTask(new Result());
            kill();
            find(_.i(R.integer.priority_orientation)).kill();
            find(_.i(R.integer.priority_wind_out_breaker)).kill();
            find(_.i(R.integer.priority_ball)).stop();
            for (AbstractTask task: findByTag("wind")) {
                task.stop();
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        Point p = getPosition();
        canvas.drawText(String.valueOf(mRestTime), p.x, p.y, mPaint);
    }
}
