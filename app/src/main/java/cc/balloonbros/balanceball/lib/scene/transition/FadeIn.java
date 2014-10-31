package cc.balloonbros.balanceball.lib.scene.transition;

import android.graphics.Color;

import cc.balloonbros.balanceball.lib.GameUtil;
import cc.balloonbros.balanceball.lib.graphic.Surface;

public class FadeIn implements Transitionable {
    private final int MAX_ALPHA = 0xff;
    private int mAlpha = MAX_ALPHA;
    private int mTransitionValue;

    public FadeIn(int duration) {
        mTransitionValue = MAX_ALPHA / GameUtil.milliSecondToFrameCount(duration);
    }

    @Override
    public Surface transit(Surface currentSurface, Surface nextSurface) {
        mAlpha -= mTransitionValue;
        if (mAlpha < 0) {
            mAlpha = 0;
        }
        nextSurface.fill(Color.BLACK & mAlpha << 24);
        return nextSurface;
    }

    @Override
    public boolean isDrawingCurrentScene() {
        return false;
    }

    @Override
    public boolean isDrawingNextScene() {
        return true;
    }

    @Override
    public boolean completeTransition() {
        return mAlpha == 0;
    }
}
