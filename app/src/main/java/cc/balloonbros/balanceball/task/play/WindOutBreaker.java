package cc.balloonbros.balanceball.task.play;

import java.util.Random;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.extender.TimerPlugin;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.lib.task.AbstractTask;

/**
 * 風の発生源タスク
 */
public class WindOutBreaker extends AbstractTask implements TimerEventListener {
    private Random mRandom = new Random();

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_wind_out_breaker));
        //setTimer(4000 * (mRandom.nextInt(2) + 1), this);
    }

    @Override
    public void onTimer() {
        Wind wind = new Wind(mRandom.nextInt(360));
        registerChild(wind);
        plugin(TimerPlugin.class).setTimer(4000 * (mRandom.nextInt(2) + 1), this);
    }
}
