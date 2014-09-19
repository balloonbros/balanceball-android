package cc.balloonbros.balanceball.lib.task.timer;

abstract public class AbstractTimer implements Timer {
    private TimerEventListener mListener = null;
    private long mTimerCount = 1;

    /**
     * タイマーを開始しているかどうか
     */
    private boolean mStarted = false;

    /**
     * タイマーをループするかどうか
     */
    private boolean mLoop = false;

    public long getTimerCount() { return mTimerCount; }

    public void onStart() { }
    public void onInvoked() { }
    public boolean condition() { return true; }

    @Override
    public Timer start(long timerCount, TimerEventListener listener) {
        return start(timerCount, listener, false);
    }

    @Override
    public Timer start(long timerCount, TimerEventListener listener, boolean loop) {
        mTimerCount = timerCount;
        mListener   = listener;
        mStarted    = true;
        mLoop       = loop;

        onStart();
        return this;
    }

    @Override
    public boolean ready() {
        return mStarted && condition();
    }

    @Override
    public void invoke() {
        mListener.onTimer();

        onInvoked();
        if (!mLoop) {
            mStarted = false;
        }
    }

    @Override
    public void stop() {
        mStarted = false;
        mLoop = false;
    }

    @Override
    public boolean isRemovable() {
        return mLoop;
    }
}
