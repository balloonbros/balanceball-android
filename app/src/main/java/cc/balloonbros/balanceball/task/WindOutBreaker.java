package cc.balloonbros.balanceball.task;

import java.util.Random;

import cc.balloonbros.balanceball.TaskPriority;
import cc.balloonbros.balanceball.lib.task.timer.FrameTimerEventListener;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.TaskEventListener;
import cc.balloonbros.balanceball.lib.Updateable;
import cc.balloonbros.balanceball.lib.TaskMessage;

/**
 * 風の発生源タスク
 */
public class WindOutBreaker extends AbstractTask implements FrameTimerEventListener {
    private Random mRandom = new Random();

    @Override
    public void onRegistered() {
        //setFrameTimer(mRandom.nextInt((int)getGame().getFps() * 2), this);
    }

    @Override
    public void onFrameTimer() {
        Wind wind = new Wind(mRandom.nextInt(360));
        wind.setPriority(TaskPriority.WIND);
        registerChild(wind);

        setFrameTimer(mRandom.nextInt((int)getGame().getFps() * 2), this);
    }
}
