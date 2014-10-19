package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Point;

import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.extender.TimerPlugin;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;

public class Animation extends DrawObject implements TimerEventListener {
    private SlicedSprite mSprites;
    private int mCurrentIndex = 0;
    private int[] mUnits;

    public Animation(AbstractTask task, int span, SlicedSprite sprites) {
        mSprites = sprites;

        if (!task.hasPlugin(TimerPlugin.class)) {
            task.with(new TimerPlugin());
        }
        task.plugin(TimerPlugin.class).setInterval(span, this);

        mUnits = new int[sprites.getCount()];
        for (int i = 0; i < mUnits.length; i++) {
            mUnits[i] = i;
        }
    }

    public void useUnits(int... units) {
        mUnits = units;
    }

    public void setAnimationIndex(int index) {
        mCurrentIndex = index;
    }

    public Sprite getCurrentSprite() {
        Sprite sprite = mSprites.getSprite();
        sprite.setSource(mSprites.getRectAt(mUnits[mCurrentIndex]));

        return sprite;
    }

    @Override
    public void onMove(Point position) {
        super.onMove(position);
        mSprites.getSprite().setPosition(position);
    }

    @Override
    public int getWidth() {
        return mSprites.getWidth();
    }

    @Override
    public int getHeight() {
        return mSprites.getHeight();
    }

    @Override
    public void onTimer() {
        mCurrentIndex++;
        if (mCurrentIndex >= mUnits.length) {
            mCurrentIndex = 0;
        }
    }
}
