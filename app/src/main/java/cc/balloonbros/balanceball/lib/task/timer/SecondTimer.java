package cc.balloonbros.balanceball.lib.task.timer;

/**
 * 秒数でカウントするタイマー
 */
public class SecondTimer extends AbstractTimer {
    private long mStartSecond = -1;

    @Override
    public void onStart() {
        mStartSecond = System.currentTimeMillis();
    }

    @Override
    public boolean condition() {
        return System.currentTimeMillis() - mStartSecond >= getTimerCount();
    }

    @Override
    public void onInvoked() {
        mStartSecond = System.currentTimeMillis();
    }
}
