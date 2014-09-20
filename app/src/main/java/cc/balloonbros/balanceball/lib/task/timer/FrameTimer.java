package cc.balloonbros.balanceball.lib.task.timer;

/**
 * フレームでカウントするタイマー
 */
public class FrameTimer extends AbstractTimer {
    private long mFrameCount = 0;

    @Override
    public boolean condition() {
        return mFrameCount >= getTimerCount();
    }

    @Override
    public void onFrame() {
        mFrameCount++;
    }

    @Override
    public void onInvoked() {
        mFrameCount = 0;
    }
}
