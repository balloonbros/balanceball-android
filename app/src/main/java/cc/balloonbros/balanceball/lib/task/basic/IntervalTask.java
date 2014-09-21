package cc.balloonbros.balanceball.lib.task.basic;

import android.graphics.Canvas;

import cc.balloonbros.balanceball.lib.task.AbstractTask;

abstract public class IntervalTask extends AbstractTask {
    private int mInterval;
    private long mCurrentFrame;

    public IntervalTask(int interval) {
        mInterval = interval;
    }

    @Override
    public void onRegistered() {
        super.onRegistered();
        mCurrentFrame = getGame().getFrameCount();
    }

    @Override
    public void execute(Canvas canvas) {
        long frame = getGame().getFrameCount();
        if (frame - mCurrentFrame >= mInterval) {
            super.execute(canvas);
            mCurrentFrame = frame;
        }
    }
}
