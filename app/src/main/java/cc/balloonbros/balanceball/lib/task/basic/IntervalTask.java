package cc.balloonbros.balanceball.lib.task.basic;

import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;

abstract public class IntervalTask extends AbstractTask {
    private int mInterval;
    private long mCurrentFrame;

    public IntervalTask(int interval) {
        mInterval = interval;
    }

    @Override
    public void onRegister() {
        super.onRegister();
        mCurrentFrame = getGame().getFrameCount();
    }

    @Override
    public void execute(Surface surface) {
        long frame = getGame().getFrameCount();
        if (frame - mCurrentFrame >= mInterval) {
            super.execute(surface);
            mCurrentFrame = frame;
        }
    }
}
