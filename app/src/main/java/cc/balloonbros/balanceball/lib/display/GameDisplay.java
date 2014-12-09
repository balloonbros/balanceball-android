package cc.balloonbros.balanceball.lib.display;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Manage the game display. This class is the singleton.
 */
public class GameDisplay {
    /** The singleton instance. */
    private static GameDisplay sInstance = new GameDisplay();

    /** The display size of the device. */
    private DisplaySize mDeviceDisplaySize;
    /** The display size of the game you create. */
    private DisplaySize mGameDisplaySize;
    /** The scaled display size. */
    private DisplaySize mScaledDisplaySize;

    /**
     * The scale value. This value is 1 if the device display size is
     * same as the game display size.
     */
    private float mScale = 1;

    /** ディスプレイサイズ(Point) */
    private Point mDisplaySizePoint = new Point();
    /** ディスプレイサイズ(Rect) */
    private Rect mDisplayRect = new Rect();

    /** ゲームディスプレイサイズ(Point) */
    private Point mGameDisplaySizePoint;
    private Point mScaledDisplaySizePoint;
    /** ゲームディスプレイサイズ(Rect) */
    private Rect mGameDisplayRect;
    private Rect mScaledDisplayRect;

    /**
     * Get the singleton instance of this class.
     * @return The singleton instance.
     */
    public static GameDisplay getInstance() {
        return sInstance;
    }

    /**
     * Get the display size. Return the device display size if the game
     * display size isn't set. Otherwise, return the game display size.
     * It means that the game that is drawn to the device display is not
     * scaled.
     * @return The display size.
     */
    public DisplaySize getDisplaySize() {
        if (mGameDisplaySize != null) {
            return mGameDisplaySize;
        }

        return mDeviceDisplaySize;
    }

    /**
     * Set the logical game display size. The game will be scaled
     * and drawn to the device display if the game display size was set.
     * @param width Game display width.
     * @param height Game display height.
     */
    public void setGameDisplaySize(int width, int height) {
        mGameDisplaySize = new DisplaySize(width, height);
        mGameDisplaySizePoint = new Point(width, height);
        mGameDisplayRect = new Rect(0, 0, width, height);
    }

    /**
     * Get the device (physical) display size.
     * @return Device display size.
     */
    public DisplaySize getDeviceDisplaySize() {
        return mDeviceDisplaySize;
    }

    /**
     * Get the scale. Return 1 if the device display size is same as the
     * game display size.
     * @return The scale.
     */
    public float getScale() {
        return mScale;
    }

    /**
     * 伸縮後の画面矩形を取得する
     * @return 画面矩形
     */
    public Rect getScaledRect() {
        if (mScaledDisplayRect == null) {
            return mDisplayRect;
        }

        return mScaledDisplayRect;
    }

    /**
     * Get the scaled display size.
     * @return The scaled display size.
     */
    public DisplaySize getScaledSize() {
        if (mScaledDisplaySize != null) {
            return mScaledDisplaySize;
        }

        return mDeviceDisplaySize;
    }

    /**
     * ディスプレイサイズを取得する
     * @return ディスプレイサイズ
     */
    public Point getDisplaySizePoint() {
        if (mGameDisplaySizePoint != null) {
            return mGameDisplaySizePoint;
        }

        return mDisplaySizePoint;
    }

    /**
     * ディスプレイ全体の矩形を取得する
     * @return 矩形
     */
    public Rect getDisplayRect() {
        if (mGameDisplayRect != null) {
            return mGameDisplayRect;
        }

        return mDisplayRect;
    }

    /**
     * Update the caches of the device display size.
     * @param width Device display width.
     * @param height Device display height.
     */
    public void updateDeviceDisplaySize(int width, int height) {
        mDeviceDisplaySize = new DisplaySize(width, height);

        if (mGameDisplaySize == null) {
            mScale = 1;
        } else {
            float scaleX = (float) mDeviceDisplaySize.getWidth()  / (float) mGameDisplaySize.getWidth();
            float scaleY = (float) mDeviceDisplaySize.getHeight() / (float) mGameDisplaySize.getHeight();
            mScale = scaleX > scaleY ? scaleY : scaleX;

            mScaledDisplaySize = new DisplaySize((int) (mGameDisplaySize.getWidth() * mScale), (int) (mGameDisplaySize.getHeight() * mScale));
            mScaledDisplaySizePoint = new Point((int) (mGameDisplaySizePoint.x * mScale), (int) (mGameDisplaySizePoint.y * mScale));
            int x = (mDisplaySizePoint.x - mScaledDisplaySizePoint.x) / 2;
            int y = (mDisplaySizePoint.y - mScaledDisplaySizePoint.y) / 2;
            mScaledDisplayRect = new Rect(x, y, x + mScaledDisplaySizePoint.x, y + mScaledDisplaySizePoint.y);
        }
    }
}
