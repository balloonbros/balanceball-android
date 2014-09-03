package cc.balloonbros.balanceball.task;

import java.util.Random;

import cc.balloonbros.balanceball.TaskPriority;
import cc.balloonbros.balanceball.lib.AbstractTask;
import cc.balloonbros.balanceball.lib.TaskEventListener;
import cc.balloonbros.balanceball.lib.Updateable;
import cc.balloonbros.balanceball.lib.TaskMessage;

/**
 * 風の発生源タスク
 */
public class WindOutBreaker extends AbstractTask implements Updateable, TaskEventListener {
    /**
     * 発生させる風タスク
     */
    private Wind mWind = null;

    @Override
    public void onUpdate() {
        Random random = new Random();
        int randomValue = random.nextInt(60 * 5);

        if (mWind == null && randomValue == 0) {
            mWind = new Wind();
            mWind.setPriority(TaskPriority.WIND);
            registerChild(mWind);
        }
    }

    @Override
    public void onMessage(AbstractTask sender, TaskMessage message) {
        if (message.getLabel().equals("wind_leaved")) {
            mWind = null;
        }
    }
}
