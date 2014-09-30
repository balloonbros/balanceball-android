package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Style;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;

public class CountdownTimer extends PositionableTask {
    private int mRestTime = 0;
    private DrawString mDisplayRestTime = new DrawString(5);

    @Override
    public void onRegister() {
        super.onRegister();

        Style t = new Style();
        t.color(_.c(R.color.countdown_color)).size(_.i(R.integer.countdown_font_size)).font(getFont(_.s(R.string.open_sans_light))).align(Paint.Align.CENTER);
        mDisplayRestTime.setTemplate(t);

        mRestTime = _.i(R.integer.game_time) * (int)getFps();

        Point p = getDisplaySize();
        position(p.x / 2, p.y / 2 + 100);
    }

    @Override
    public void update() {
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
    public void onDraw(Canvas canvas, Surface surface) {
        int integerPart = mRestTime / (int)getFps();
        int decimalPart = mRestTime % (int)getFps();

        mDisplayRestTime.set(integerPart).append('.');
        if (decimalPart < 10) {
            mDisplayRestTime.append('0');
        }
        mDisplayRestTime.append(decimalPart);

        surface.draw(mDisplayRestTime);
    }
}
