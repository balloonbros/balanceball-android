package cc.balloonbros.balanceball.task.play;

import java.util.Random;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.lib.task.AbstractTask;

/**
 * 風の発生源タスク
 */
public class WindOutBreaker extends AbstractTask implements TimerEventListener {
    private Random mRandom = new Random();

    @Override
    public void onRegistered() {
        //setFrameTimer(mRandom.nextInt((int)getGame().getFps() * 5), this);
    }

    @Override
    public void onTimer() {
        Wind wind = new Wind(mRandom.nextInt(360));
        wind.setPriority(getInteger(R.integer.priority_wind));
        registerChild(wind);

        setFrameTimer(mRandom.nextInt((int)getGame().getFps() * 5), this);
    }

    @Override
    public void onUpdate() {

    }
}
