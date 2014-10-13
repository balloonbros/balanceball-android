package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rectangle extends Shape {
    private float mWidth;
    private float mHeight;

    public Rectangle() {
        mWidth  = 0;
        mHeight = 0;
    }

    public Rectangle(float width, float height) {
        mWidth  = width;
        mHeight = height;
    }

    public void changeSize(float width, float height) {
        mWidth  = width;
        mHeight = height;
    }

    public void changeWidth(float width) {
        mWidth  = width;
    }

    public void changeHeight(float height) {
        mHeight = height;
    }

    @Override
    protected void draw(int x, int y, Paint paint, Canvas canvas) {
        canvas.drawRect(x, y, x + mWidth, y + mHeight, paint);
    }

    @Override
    public int getWidth() {
        return (int)mWidth;
    }

    @Override
    public int getHeight() {
        return (int)mHeight;
    }
}
