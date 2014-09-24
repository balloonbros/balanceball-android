package cc.balloonbros.balanceball.lib.task.basic;

import android.graphics.Point;
import android.graphics.Rect;

abstract public class MovableTask extends PositionableTask {
    /** オブジェクトが動き回れる範囲 */
    private Rect mMovableArea = new Rect();

    @Override
    public void onRegister() {
        super.onRegister();

        Point displaySize = getDisplaySize();
        mMovableArea.set(0, 0, displaySize.x, displaySize.y);
    }

    /**
     * オブジェクトの移動可能範囲をセットする
     * @param area 移動可能範囲
     */
    public void setMovableArea(Rect area) {
        mMovableArea = area;
    }

    /**
     * 指定した座標に移動する
     * @param x x座標
     * @param y y座標
     */
    public void moveTo(int x, int y) {
        position(x, y);
    }

    /**
     * 指定した距離だけ移動する
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    public void move(int dx, int dy) {
        mCoordinate.offset(dx, dy);
    }

    /**
     * 指定した距離だけ移動する。
     * 移動可能範囲からはみ出た場合は自動的に範囲内に位置が調整される
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    public void moveInArea(int dx, int dy) {
        move(dx, dy);

        // ボールが移動可能範囲外にはみ出してたら位置を調整する
        if (isBorderLeftEdge()) {
            mCoordinate.x = mMovableArea.left;
        } else if (isBorderRightEdge()) {
            mCoordinate.x = mMovableArea.right - getWidth();
        }

        if (isBorderTopEdge()) {
            mCoordinate.y = mMovableArea.top;
        } else if (isBorderBottomEdge()) {
            mCoordinate.y = mMovableArea.bottom - getHeight();
        }
    }

    /**
     * 画面から見えなくなったかどうかをチェックする
     * @return 画面から見えない状態であればtrue
     */
    public boolean isInvisible() {
        Point displaySize = getDisplaySize();
        return mCoordinate.x + getWidth()  <= 0             ||
               mCoordinate.x               >= displaySize.x ||
               mCoordinate.y + getHeight() <= 0             ||
               mCoordinate.y               >= displaySize.y;
    }

    /**
     * オブジェクトが左端に位置しているかどうかをチェックする
     * @return 左端に位置していればtrue
     */
    public boolean isBorderLeftEdge() {
        return getPosition().x <= mMovableArea.left;
    }

    /**
     * オブジェクトが右端に位置しているかどうかをチェックする
     * @return 右端に位置していればtrue
     */
    public boolean isBorderRightEdge() {
        return getPosition().x + getWidth() >= mMovableArea.right;
    }

    /**
     * オブジェクトが上端に位置しているかどうかをチェックする
     * @return 上端に位置していればtrue
     */
    public boolean isBorderTopEdge() {
        return getPosition().y <= mMovableArea.top;
    }

    /**
     * オブジェクトが下端に位置しているかどうかをチェックする
     * @return 下端に位置していればtrue
     */
    public boolean isBorderBottomEdge() {
        return getPosition().y + getHeight() >= mMovableArea.bottom;
    }

    /* ==============================================
     *           オーバーライド専用メソッド
     * ============================================== */

    /**
     * このオブジェクトの幅を取得する。
     * @return 幅
     */
    abstract public int getWidth();

    /**
     * このオブジェクトの高さを取得する
     * @return 高さ
     */
    abstract public int getHeight();
}
