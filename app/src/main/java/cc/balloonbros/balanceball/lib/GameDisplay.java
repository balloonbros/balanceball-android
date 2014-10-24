package cc.balloonbros.balanceball.lib;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
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
    /** ディスプレイの密度倍率 */
    private float mDensity;

    public static GameDisplay getInstance() {
        return sInstance;
    }

    /**
     * dpからpxに変換する
     * @return px値
     */
    public static int dp2px(float dp) {
        return getInstance()._dp2px(dp);
    }

    private int _dp2px(float dp) {
        return (int) (dp * mDensity + 0.5f);
    }

    /**
     * ディスプレイサイズを取得する
     * @return ディスプレイサイズ
     */
    public Point getDisplaySize() {
        return mDisplaySize;
    }

    /**
     * ディスプレイ全体の矩形を取得する
     * @return 矩形
     */
    public Rect getDisplayRect() {
        return mDisplayRect;
    }

    /**
     * ディスプレイサイズ計算のためにContextをセットする
     * @param context Context
     */
    protected void setContext(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDensity = context.getResources().getDisplayMetrics().density;
        updateDisplaySize();
    }

    /**
     * ディスプレイサイズのキャッシュを更新する
     */
    protected void updateDisplaySize() {
        Display display = mWindowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(mDisplaySize);
        } else {
            mDisplaySize.set(display.getWidth(), display.getHeight());
        }
        mDisplayRect.set(0, 0, mDisplaySize.x, mDisplaySize.y);
    }
}
