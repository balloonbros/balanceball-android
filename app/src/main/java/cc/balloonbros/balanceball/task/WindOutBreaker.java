package cc.balloonbros.balanceball.task;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

public class WindOutBreaker extends TaskBase {
    private Wind mWind = null;

    @Override
    public void execute(Canvas canvas) {
        Random random = new Random();
        int randomValue = random.nextInt(60 * 5);
        Log.d("tag", String.valueOf(randomValue));
        if (mWind == null && randomValue == 0) {
            mWind = new Wind();
            getTaskManager().register(mWind);
        }
    }
}
