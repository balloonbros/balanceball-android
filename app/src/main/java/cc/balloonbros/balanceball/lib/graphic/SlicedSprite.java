package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Bitmap;
import android.graphics.Rect;

import cc.balloonbros.balanceball.lib.task.AbstractTask;

public class SlicedSprite {
    private SpriteForSurfaceView mSprite;
    private int mWidth;
    private int mHeight;
    private Rect[] mRects;

    public SlicedSprite(SpriteForSurfaceView sprite, int width, int height) {
        mSprite = sprite;
        mWidth  = width;
        mHeight = height;

        int horizontalCount = getBitmap().getWidth()  / mWidth;
        int verticalCount   = getBitmap().getHeight() / mHeight;

        mRects = new Rect[horizontalCount * verticalCount];
        for (int v = 0; v < verticalCount; v++) {
            for (int h = 0; h < horizontalCount; h++) {
                int i = h + horizontalCount * v;
                int x = h * width;
                int y = v * height;

                Rect rect = new Rect(x, y, x + width, y + height);
                mRects[i] = rect;
            }
        }
    }

    public Animation toAnimation(AbstractTask task, int interval) {
        return new Animation(task, interval, this);
    }

    public SpriteForSurfaceView getSprite() {
        return mSprite;
    }

    public int getCount() {
        return mRects.length;
    }

    public Rect getSourceRectAt(int index) {
        return mRects[index];
    }

    public Bitmap getBitmap() {
        return mSprite.getBitmap();
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
