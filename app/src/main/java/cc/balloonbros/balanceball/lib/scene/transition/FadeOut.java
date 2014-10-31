package cc.balloonbros.balanceball.lib.scene.transition;

import android.graphics.Color;

import cc.balloonbros.balanceball.lib.GameUtil;
import cc.balloonbros.balanceball.lib.graphic.Surface;

public class FadeOut implements Transitionable {
    private final int MAX_ALPHA = 0xff;
    private int mAlpha = 0;
    private int mTransitionValue;

    public FadeOut(int duration) {
        mTransitionValue = MAX_ALPHA / GameUtil.milliSecondToFrameCount(duration);
    }

    @Override
    public Surface transit(Surface currentSurface, Surface nextSurface) {
        mAlpha += mTransitionValue;
        if (mAlpha > MAX_ALPHA) {
            mAlpha = MAX_ALPHA;
        }
        currentSurface.fill(Color.BLACK & mAlpha << 24);
        return currentSurface;
    }

    @Override
    public boolean isDrawingCurrentScene() {
        return true;
    }

    @Override
    public boolean isDrawingNextScene() {
        return false;
    }

    @Override
    public boolean completeTransition() {
        return mAlpha == MAX_ALPHA;
    }
}
