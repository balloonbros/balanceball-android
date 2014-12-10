package cc.balloonbros.balanceball.lib.graphic.old;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;

import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.graphic.DrawObject;

public class Shape extends DrawObject {
    private Bitmap mBitmap;

    public Shape(int resourceId) {
        GradientDrawable shape = (GradientDrawable) _.d(resourceId);
        int width  = shape.getIntrinsicWidth();
        int height = shape.getIntrinsicHeight();

        if (width == -1) {
            width = 1;
        }
        if (height == -1) {
            height = 1;
        }

        mBitmap = createBitmap(shape, width, height);
    }

    public Shape(int resourceId, int width, int height) {
        GradientDrawable shape = (GradientDrawable) _.d(resourceId);
        mBitmap = createBitmap(shape, width, height);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    private Bitmap createBitmap(GradientDrawable shape, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        shape.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        shape.draw(canvas);

        return bitmap;
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
