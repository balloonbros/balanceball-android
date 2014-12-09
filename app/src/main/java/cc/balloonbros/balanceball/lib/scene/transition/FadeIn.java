package cc.balloonbros.balanceball.lib.scene.transition;

import android.graphics.Color;
import android.opengl.GLES20;

import cc.balloonbros.balanceball.lib.display.DisplaySize;
import cc.balloonbros.balanceball.lib.display.GameDisplay;
import cc.balloonbros.balanceball.lib.graphic.opengl.FrameBuffer;
import cc.balloonbros.balanceball.lib.graphic.shape.Rectangle;
import cc.balloonbros.balanceball.lib.util.GameUtil;
import cc.balloonbros.balanceball.lib.graphic.Surface;

public class FadeIn implements Transitionable {
    private final int MAX_ALPHA = 0xff;
    private int mAlpha = MAX_ALPHA;
    private int mTransitionValue;
    private Rectangle mFadingRect;

    public FadeIn(int duration) {
        DisplaySize displaySize = GameDisplay.getInstance().getDisplaySize();
        mFadingRect = new Rectangle(displaySize.getWidth(), displaySize.getHeight());
        mFadingRect.setDepth(100);
        mTransitionValue = MAX_ALPHA / GameUtil.milliSecondToFrameCount(duration);
    }

    @Override
    public FrameBuffer transit(FrameBuffer currentFrameBuffer, FrameBuffer nextFrameBuffer) {
        mAlpha -= mTransitionValue;
        if (mAlpha < 0) {
            mAlpha = 0;
        }

        nextFrameBuffer.bind();
        mFadingRect.setColor(Color.BLACK & mAlpha << 24);
        mFadingRect.draw();
        nextFrameBuffer.end();
        return nextFrameBuffer;
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
