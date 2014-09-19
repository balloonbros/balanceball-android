package cc.balloonbros.balanceball.lib.task.timer;

import cc.balloonbros.balanceball.lib.GameMain;

/**
 * フレームでカウントするタイマー
 */
public class FrameTimer extends AbstractTimer {
    private GameMain mGame = null;
    private long mStartFrame = -1;

    /**
     * コンストラクタ
     * @param game フレームタイマーをセットするゲーム
     */
    public FrameTimer(GameMain game) {
        mGame = game;
    }

    @Override
    public void onStart() {
        mStartFrame = mGame.getFrameCount();
    }

    @Override
    public boolean condition() {
        return mGame.getFrameCount() - mStartFrame >= getTimerCount();
    }

    @Override
    public void onInvoked() {
        mStartFrame = mGame.getFrameCount();
    }
}
