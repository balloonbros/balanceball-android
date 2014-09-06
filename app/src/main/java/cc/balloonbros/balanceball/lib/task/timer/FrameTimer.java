package cc.balloonbros.balanceball.lib.task.timer;

import cc.balloonbros.balanceball.lib.GameMain;

/**
 * フレームタイマー
 */
public class FrameTimer {
    private GameMain mGame = null;
    private FrameTimerEventListener mListener = null;

    /**
     * タイマー開始時のフレームと待ちフレーム数
     */
    private long mCurrentFrame = -1;
    private long mWaitFrame = -1;

    /**
     * タイマーを開始しているかどうか
     */
    private boolean mStarted = false;

    /**
     * タイマーをループするかどうか
     */
    private boolean mLoop = false;

    /**
     * コンストラクタ
     * @param game フレームタイマーをセットするゲーム
     */
    public FrameTimer(GameMain game) {
        mGame = game;
    }

    /**
     * タイマーを開始する
     * @param waitFrame 待ちフレーム数
     * @param listener コールバックリスナー
     */
    public FrameTimer start(long waitFrame, FrameTimerEventListener listener) {
        return start(waitFrame, listener, false);
    }

    /**
     * ループするタイマーを開始する
     * @param waitFrame 待ちフレーム数
     * @param listener コールバックリスナー
     */
    public FrameTimer start(long waitFrame, FrameTimerEventListener listener, boolean loop) {
        mCurrentFrame = mGame.getFrameCount();
        mWaitFrame    = waitFrame;
        mListener     = listener;
        mStarted      = true;
        mLoop         = loop;

        return this;
    }

    /**
     * 指定待ちフレームが経過してコールバックを起動していいかどうかチェックする
     * @return コールバックが起動できる場合はtrue
     */
    public boolean ready() {
        return mStarted && mGame.getFrameCount() - mCurrentFrame >= mWaitFrame;
    }

    /**
     * コールバックを起動させる
     */
    public void invoke() {
        mListener.onFrameTimer();

        if (mLoop) {
            mCurrentFrame = mGame.getFrameCount();
        } else {
            mStarted = false;
        }
    }

    /**
     * タイマーを止める
     */
    public void stop() {
        mStarted = false;
        mLoop = false;
    }

    /**
     * このタイマーの実行完了後に削除していいかどうかを確認する
     * @return 削除可能であればtrue
     */
    public boolean isRemovable() {
        return mLoop;
    }
}
