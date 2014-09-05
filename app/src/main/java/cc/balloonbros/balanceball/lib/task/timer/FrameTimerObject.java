package cc.balloonbros.balanceball.lib.task.timer;

import cc.balloonbros.balanceball.lib.GameMain;

public class FrameTimerObject {
    private GameMain mGame = null;
    private long mCurrentFrame = -1;
    private long mWaitFrame = -1;
    private FrameTimerEventListener mListener = null;
    private boolean waiting = false;

    public FrameTimerObject(GameMain game) {
        mGame = game;
    }

    public void start(long waitFrame, FrameTimerEventListener listener) {
        start(waitFrame, listener, true);
    }

    public void start(long waitFrame, FrameTimerEventListener listener, boolean loop) {
        mCurrentFrame = mGame.getFrameCount();
        mWaitFrame = waitFrame;
        mListener = listener;
        waiting = true;
    }

    public boolean ready() {
        return waiting && mGame.getFrameCount() - mCurrentFrame >= mWaitFrame;
    }

    public void invoke() {
        mListener.onFrameTimer();
        waiting = false;
    }
}
