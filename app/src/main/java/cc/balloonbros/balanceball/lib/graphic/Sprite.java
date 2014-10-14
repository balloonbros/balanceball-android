package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Bitmap;

public class Sprite extends DrawObject {
    private Bitmap mBitmap = null;

    public static Sprite from(Bitmap bitmap) {
        Sprite sprite = new Sprite();
        sprite.mBitmap = bitmap;

        return sprite;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public int getWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return mBitmap.getHeight();
    }
}
