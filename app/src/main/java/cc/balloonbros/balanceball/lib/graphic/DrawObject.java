package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Point;
import android.graphics.Rect;

abstract public class DrawObject implements Positionable {
    /** 描画オブジェクトの位置 */
    private Point mPosition = null;
    /** オブジェクトが動き回れる範囲 */
    private Rect mMovableArea = null;

    /**
     * オブジェクトの描画位置をセットする
     * @param position 文字列の描画位置
     */
    @Override
    public DrawObject setPosition(Point position) {
        mPosition = position;
        return this;
    }

    /**
     * 文字列の描画位置をセットする
     * @param x x座標
     * @param y y座標
     */
    @Override
    public DrawObject setPosition(int x, int y) {
        if (mPosition == null) {
            mPosition = new Point();
        }
        mPosition.set(x, y);
        return this;
    }

    /**
     * 文字列の描画位置を取得する
     * @return 文字列の描画位置
     */
    @Override
    public Point getPosition() {
        if (mPosition == null) {
            setPosition(0, 0);
        }
        return mPosition;
    }

    /**
     * オブジェクトの移動可能範囲をセットする
     * @param area 移動可能範囲
     */
    @Override
    public void setMovableArea(Rect area) {
        mMovableArea = area;
    }

    /**
     * オブジェクトの移動可能範囲をセットする
     * @param x x座標
     * @param y y座標
     * @param width 幅
     * @param height 高さ
     */
    @Override
    public void setMovableArea(int x, int y, int width, int height) {
        if (mMovableArea == null) {
            mMovableArea = new Rect();
        }
        mMovableArea.set(x, y, x + width, y + height);
    }

    /**
     * 指定した座標に移動する。setPositionのエイリアス
     * @see #setPosition(int, int)
     * @param x x座標
     * @param y y座標
     */
    public void moveTo(int x, int y) {
        setPosition(x, y);
    }

    /**
     * 指定した距離だけ移動する
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    @Override
    public void move(int dx, int dy) {
        mPosition.offset(dx, dy);
    }

    /**
     * 指定した距離だけ移動する。
     * 移動可能範囲からはみ出た場合は自動的に範囲内に位置が調整される
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    @Override
    public void moveInArea(int dx, int dy) {
        move(dx, dy);

        if (mMovableArea != null) {
            // ボールが移動可能範囲外にはみ出してたら位置を調整する
            if (isBorderLeftEdge()) {
                mPosition.x = mMovableArea.left;
            } else if (isBorderRightEdge()) {
                mPosition.x = mMovableArea.right - getWidth();
            }

            if (isBorderTopEdge()) {
                mPosition.y = mMovableArea.top;
            } else if (isBorderBottomEdge()) {
                mPosition.y = mMovableArea.bottom - getHeight();
            }
        }
    }

    /**
     * オブジェクトが左端に位置しているかどうかをチェックする
     * @return 左端に位置していればtrue
     */
    @Override
    public boolean isBorderLeftEdge() {
        return mMovableArea != null && mPosition.x <= mMovableArea.left;
    }

    /**
     * オブジェクトが右端に位置しているかどうかをチェックする
     * @return 右端に位置していればtrue
     */
    @Override
    public boolean isBorderRightEdge() {
        return mMovableArea != null && mPosition.x + getWidth() >= mMovableArea.right;
    }

    /**
     * オブジェクトが上端に位置しているかどうかをチェックする
     * @return 上端に位置していればtrue
     */
    @Override
    public boolean isBorderTopEdge() {
        return mMovableArea != null && mPosition.y <= mMovableArea.top;
    }

    /**
     * オブジェクトが下端に位置しているかどうかをチェックする
     * @return 下端に位置していればtrue
     */
    @Override
    public boolean isBorderBottomEdge() {
        return mMovableArea != null && mPosition.y + getHeight() >= mMovableArea.bottom;
    }
}
