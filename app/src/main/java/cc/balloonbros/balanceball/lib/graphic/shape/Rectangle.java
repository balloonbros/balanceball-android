package cc.balloonbros.balanceball.lib.graphic.shape;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import cc.balloonbros.balanceball.lib.graphic.DrawObject;
import cc.balloonbros.balanceball.lib.graphic.opengl.Polygon;
import cc.balloonbros.balanceball.lib.util.BufferUtil;
import cc.balloonbros.balanceball.lib.util.SharedBuffer;
import cc.balloonbros.balanceball.lib.util.VertexUtils;

/**
 * Rectangle shape.
 */
public class Rectangle extends DrawObject {
    private Polygon mPolygon;
    private float[] mColor = new float[16];
    private FloatBuffer mColorBuffer;
    private int mWidth;
    private int mHeight;

    /**
     * Constructor.
     * @param width Width of this rectangle.
     * @param height Height of this rectangle.
     */
    public Rectangle(int width, int height) {
        mWidth  = width;
        mHeight = height;

        FloatBuffer vertexBuffer = BufferUtil.convert(VertexUtils.generateRectangle(0, 0, 0, mWidth, mHeight));
        ShortBuffer indexBuffer  = SharedBuffer.squareIndices();
        mColorBuffer = BufferUtil.convert(mColor);
        mPolygon = new Polygon(vertexBuffer, indexBuffer);
    }

    public void setColor(int color) {
        mColorBuffer.clear();
        for (int i = 0; i < 4; i++) {
            mColor[i * 4 + 0] = (float) Color.red(color)   / 255f;
            mColor[i * 4 + 1] = (float) Color.green(color) / 255f;
            mColor[i * 4 + 2] = (float) Color.blue(color)  / 255f;
            mColor[i * 4 + 3] = (float) Color.alpha(color) / 255f;
        }
        mColorBuffer.put(mColor);
        mColorBuffer.position(0);
    }

    /**
     * Draw a rectangle shape.
     */
    public void draw() {
        Point p = getPosition();
        mPolygon.draw(p.x, p.y, getWorldDepth(), mColorBuffer);
    }

    @Override
    public int getWidth() {
        return mWidth;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }
}
