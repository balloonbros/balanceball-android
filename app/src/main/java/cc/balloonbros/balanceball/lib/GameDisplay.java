package cc.balloonbros.balanceball.lib;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.WindowManager;

/**
 * ゲームのディスプレイに関する情報を扱うクラス。
 * シングルトン。
 */
public class GameDisplay {
    /** シングルトン用インスタンス */
    private static GameDisplay sInstance = new GameDisplay();

    /** ディスプレイサイズ(Point) */
    private Point mDisplaySize = new Point();
    /** ディスプレイサイズ(Rect) */
    private Rect mDisplayRect = new Rect();

    /** ディスプレイサイズを計算するためのWindowManager */
    private WindowManager mWindowManager;

    public static GameDisplay getInstance() {
        return sInstance;
    }

    /**
     * ディスプレイサイズ計算のためにWindowManagerをセットする
     * @param windowManager WindowManager
     */
    protected void setWindowManager(WindowManager windowManager) {
        mWindowManager = windowManager;
        updateDisplaySize();
    }

    public Point getDisplaySize() {
        return mDisplaySize;
    }

    protected void updateDisplaySize() {
        mWindowManager.getDefaultDisplay().getSize(mDisplaySize);
        mDisplayRect.set(0, 0, mDisplaySize.x, mDisplaySize.y);
    }

    public Rect getDisplayRect() {
        return mDisplayRect;
    }
}
