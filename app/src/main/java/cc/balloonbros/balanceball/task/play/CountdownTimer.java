package cc.balloonbros.balanceball.task.play;

import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

public class CountdownTimer extends AbstractTask implements Drawable {
    private int mRestTime = 0;
    private DrawString mDisplayRestTime = new DrawString(5);

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_countdown_timer));

        Point p = getDisplaySize();
        mDisplayRestTime.setStyle(getStyle("countdown_timer")).setPosition(p.x / 2, p.y / 2 + 100);

        mRestTime = _i(R.integer.game_time) * (int)getFps();
    }

    @Override
    public void update() {
        mRestTime--;
        if (mRestTime == 0) {
            registerTask(new Result());
            kill();
            find(_i(R.integer.priority_orientation)).kill();
            find(_i(R.integer.priority_wind_out_breaker)).kill();
            find(_i(R.integer.priority_ball)).stop();
            find(_i(R.integer.priority_judgement)).stop();
            for (AbstractTask task: findByTag("wind")) {
                task.stop();
            }
        }
    }

    @Override
    public void onDraw(Surface surface) {
        mDisplayRestTime.format("%d.%02d", mRestTime / (int)getFps(), mRestTime % (int)getFps());
        surface.draw(mDisplayRestTime);
    }
}
