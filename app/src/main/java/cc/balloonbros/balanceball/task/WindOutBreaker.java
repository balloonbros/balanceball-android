package cc.balloonbros.balanceball.task;

import java.util.Random;

import cc.balloonbros.balanceball.lib.TaskBase;
import cc.balloonbros.balanceball.lib.TaskEventListener;
import cc.balloonbros.balanceball.lib.Updateable;

public class WindOutBreaker extends TaskBase implements Updateable, TaskEventListener {
    private Wind mWind = null;

    @Override
    public void onUpdate() {
        Random random = new Random();
        int randomValue = random.nextInt(60 * 5);
        if (mWind == null && randomValue == 0) {
            mWind = new Wind(this);
            getTaskManager().reserve(mWind);
        }
    }

    @Override
    public void onMessage(Object message) {
        mWind = null;
    }
}
