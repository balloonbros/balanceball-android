package cc.balloonbros.balanceball.lib;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
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

    /** ゲームディスプレイサイズ(Point) */
    private Point mGameDisplaySize;
    /** ゲームディスプレイサイズ(Rect) */
    private Rect mGameDisplayRect;
    private Rect mScaledDisplayRect;

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
     * ゲームの中の論理的な解像度を設定する。
     * この値を設定しておけば、ゲームを画面に描画する際に
     * 実際の端末の解像度に合わせて伸縮して描画するようになる。
     * @param width 幅
     * @param height 高さ
     */
    public void setGameDisplaySize(int width, int height) {
        mGameDisplaySize = new Point(width, height);
        mGameDisplayRect = new Rect(0, 0, width, height);
    }

    /**
     * ゲームのサイズを取得する。
     * @return サイズ
     */
    public Point getRealDisplaySize() {
        return mDisplaySize;
    }

    /**
     * ゲームのサイズ矩形を取得する。
     * @return サイズ矩形
     */
    public Rect getRealDisplayRect() {
        return mDisplayRect;
    }

    /**
     * ゲーム画面の伸縮率を取得する
     * @return 伸縮率
     */
    public float getScale() {
        if (mGameDisplaySize == null) {
            return 1;
        }

        float scaleX = (float) mDisplaySize.x / (float) mGameDisplaySize.x;
        float scaleY = (float) mDisplaySize.y / (float) mGameDisplaySize.y;
        return scaleX > scaleY ? scaleY : scaleX;
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
     * ゲーム画面サイズが端末画面サイズと同じかどうか
     * つまり伸縮が必要かどうかをチェックする
     * @return 伸縮されていればtrue
     */
    public boolean isFit() {
        return mGameDisplaySize == null || mGameDisplaySize.equals(mDisplaySize);
    }

    /**
     * ディスプレイサイズを取得する
     * @return ディスプレイサイズ
     */
    public Point getDisplaySize() {
        if (mGameDisplaySize != null) {
            return mGameDisplaySize;
        }

        return mDisplaySize;
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
     * ディスプレイサイズ計算のためにContextをセットする
     * @param context Context
     */
    protected void setContext(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDensity = context.getResources().getDisplayMetrics().density;
    }

    /**
     * ディスプレイサイズのキャッシュを更新する
     */
    protected void updateDisplaySize(int width, int height) {
        /*
        Display display = mWindowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(mDisplaySize);
        } else {
            mDisplaySize.set(display.getWidth(), display.getHeight());
        }
        */
        mDisplaySize.set(width, height);
        mDisplayRect.set(0, 0, mDisplaySize.x, mDisplaySize.y);

        if (mGameDisplaySize != null) {
            float scale = getScale();
            Point scaledSize = new Point((int) (mGameDisplaySize.x * scale), (int) (mGameDisplaySize.y * scale));

            int x = (mDisplaySize.x - scaledSize.x) / 2;
            int y = (mDisplaySize.y - scaledSize.y) / 2;
            mScaledDisplayRect = new Rect(x, y, x + scaledSize.x, y + scaledSize.y);
        }
    }
}
