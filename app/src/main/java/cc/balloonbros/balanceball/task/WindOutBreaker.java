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
public class WindOutBreaker extends AbstractTask implements Updateable, TaskEventListener, FrameTimerEventListener {
    /**
     * 発生させる風タスク
     */
    private Wind mWind = null;

    private Random mRandom = new Random();

    @Override
    public void onUpdate() {
        setFrameTimer(mRandom.nextInt((int)getGame().getFps() * 2), this);
    }

    @Override
    public void onMessage(AbstractTask sender, TaskMessage message) {
        if (message.getLabel().equals("wind_leaved")) {
            mWind = null;
        }
    }

    @Override
    public void onFrameTimer() {
        if (mWind == null) {
            mWind = new Wind();
            mWind.setPriority(TaskPriority.WIND);
            registerChild(mWind);

            setFrameTimer(mRandom.nextInt((int) getGame().getFps() * 2), this);
        }
    }
}
