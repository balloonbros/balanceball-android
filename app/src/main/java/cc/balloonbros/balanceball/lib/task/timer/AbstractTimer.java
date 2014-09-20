package cc.balloonbros.balanceball.lib.task.timer;

/**
 * startで指定したカウントに達した時点でコールバックを呼び出すタイマー。
 * このクラスを継承して、継承先で必要なメソッドをオーバーライドして
 * 何をカウント対象にするかを決定する。
 *
 * @see AbstractTimer#onStart()
 * @see AbstractTimer#onInvoked()
 * @see AbstractTimer#onFrame()
 */
abstract public class AbstractTimer implements Timer {
    /** タイマー起動条件が満たされている場合に呼び出されるリスナー */
    private TimerEventListener mListener = null;

    /** タイマーカウント。タイマー起動時に指定される */
    private long mTimerCount = 0;

    /** タイマーが起動されているかどうか */
    private boolean mStarted = false;

    /** タイマー終了後、再度同じ起動条件でタイマーをループするかどうか */
    private boolean mLoop = false;

    /**
     * タイマー起動時に指定したタイマーカウントを取得する
     * @return タイマーカウント
     */
    public long getTimerCount() { return mTimerCount; }

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
    public void process() {
        onFrame();
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

    /* ==============================================
     *           オーバーライド専用メソッド
     * ============================================== */

    /**
     * タイマー開始時に呼ばれる
     */
    public void onStart() { }

    /**
     * タイマーコールバック呼び出し後に呼ばれる
     */
    public void onInvoked() { }

    /**
     * 毎フレーム呼ばれる
     */
    public void onFrame() { }

    /**
     * タイマー起動条件を記述する
     * @return trueを返すとタイマーコールバックが呼び出される
     */
    public boolean condition() { return true; }
}
