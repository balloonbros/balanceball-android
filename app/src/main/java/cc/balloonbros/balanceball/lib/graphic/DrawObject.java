package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Point;

/**
 * The object that is able to be drawn into the frame buffer.
 */
abstract public class DrawObject {
    private final static int MIN_DEPTH = -10000;
    private final static int MAX_DEPTH = 10000;

    /** Coordinates of this draw object */
    private Point mPosition = new Point(0, 0);
    /** Z depth of this draw object */
    private int mDepth = 0;

    public void setDepth(int depth) {
        if (depth < MIN_DEPTH) {
            mDepth = MIN_DEPTH;
        } else if (depth > MAX_DEPTH) {
            mDepth = MAX_DEPTH;
        } else {
            mDepth = depth;
        }
    }

    public int getDepth() {
        return mDepth;
    }

    public float getWorldDepth() {
        return (float) mDepth / (float) MAX_DEPTH;
    }

    public Point getPosition() {
        return mPosition;
    }

    public void moveTo(int x, int y) {
        mPosition.set(x, y);
    }

    public void move(int dx, int dy) {
        mPosition.offset(dx, dy);
    }
}
