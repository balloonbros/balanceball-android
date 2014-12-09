package cc.balloonbros.balanceball.lib.display;

/**
 * Representation of the display size.
 */
public class DisplaySize {
    /** Display width */
    private int mWidth;
    /** Display height */
    private int mHeight;

    public DisplaySize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
