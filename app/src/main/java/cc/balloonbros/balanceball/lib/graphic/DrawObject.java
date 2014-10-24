package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Point;
import android.graphics.Rect;

import cc.balloonbros.balanceball.lib.GameDisplay;

abstract public class DrawObject implements Positionable {
    /** 描画オブジェクトの位置 */
    private Point mPosition = new Point(0, 0);
    /** オブジェクトの矩形 */
    private Rect mRect = null;
    /** オブジェクトが動き回れる範囲 */
    private Rect mMovableArea = null;

    /**
     * オブジェクト矩形を更新してその矩形を返す
     * @return 更新した矩形
     */
    protected Rect updateRect() {
        if (mRect == null) {
            mRect = new Rect();
        }
        mRect.set(mPosition.x, mPosition.y, mPosition.x + getWidth(), mPosition.y + getHeight());
        return mRect;
    }

    /**
     * オブジェクトの矩形を取得する
     * @return 矩形
     */
    @Override
    public Rect getRect() {
        return updateRect();
    }

    /**
     * オブジェクトが移動した時に呼び出されるコールバック
     * @param position 移動後の位置
     */
    protected void onMove(Point position) {
        updateRect();
    }

    /**
     * オブジェクトの描画位置をセットする
     * @param position 文字列の描画位置
     */
    @Override
    public Positionable setPosition(Point position) {
        return setPosition(position.x, position.y);
    }

    /**
     * 文字列の描画位置をセットする
     * @param x x座標
     * @param y y座標
     */
    @Override
    public Positionable setPosition(int x, int y) {
        mPosition.set(x, y);
        onMove(mPosition);
        return this;
    }

    /**
     * 文字列の描画位置を取得する
     * @return 文字列の描画位置
     */
    @Override
    public Point getPosition() {
        return mPosition;
    }

    /**
     * オブジェクトの移動可能範囲をリセットする
     */
    @Override
    public Positionable resetMovableArea() {
        mMovableArea = null;
        return this;
    }

    /**
     * オブジェクトの移動可能範囲をセットする
     * @param area 移動可能範囲
     */
    @Override
    public Positionable setMovableArea(Rect area) {
        return setMovableArea(area.left, area.top, area.right - area.left, area.bottom - area.top);
    }

    /**
     * オブジェクトの移動可能範囲をセットする
     * @param x x座標
     * @param y y座標
     * @param width 幅
     * @param height 高さ
     */
    @Override
    public Positionable setMovableArea(int x, int y, int width, int height) {
        if (mMovableArea == null) {
            mMovableArea = new Rect();
        }
        mMovableArea.set(x, y, x + width, y + height);
        return this;
    }

    /**
     * 指定した座標に移動する。setPositionのエイリアス
     * @see #setPosition(int, int)
     * @param x x座標
     * @param y y座標
     */
    public Positionable moveTo(int x, int y) {
        return setPosition(x, y);
    }

    /**
     * 指定した距離だけ移動する
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    @Override
    public Positionable move(int dx, int dy) {
        mPosition.offset(dx, dy);
        onMove(mPosition);
        return this;
    }

    /**
     * エリア中央に位置をセットする
     * @param area このエリアの中央位置にセットする
     */
    @Override
    public Positionable moveToCenter(Rect area) {
        int x = area.left + ((area.right  - area.left) / 2) - (getWidth()  / 2);
        int y = area.top  + ((area.bottom - area.top)  / 2) - (getHeight() / 2);
        return setPosition(x, y);
    }

    /**
     * 画面中央に位置をセットする
     */
    @Override
    public Positionable moveToCenter() {
        return moveToCenter(GameDisplay.getInstance().getDisplayRect());
    }

    /**
     * 指定した距離だけ移動する。
     * 移動可能範囲からはみ出た場合は自動的に範囲内に位置が調整される
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    @Override
    public Positionable moveInArea(int dx, int dy) {
        move(dx, dy);

        if (mMovableArea != null) {
            int x = mPosition.x;
            int y = mPosition.y;

            // ボールが移動可能範囲外にはみ出してたら位置を調整する
            if (isBorderLeftEdge()) {
                x = mMovableArea.left;
            } else if (isBorderRightEdge()) {
                x = mMovableArea.right - getWidth();
            }

            if (isBorderTopEdge()) {
                y = mMovableArea.top;
            } else if (isBorderBottomEdge()) {
                y = mMovableArea.bottom - getHeight();
            }

            if (x != mPosition.x || y != mPosition.y) {
                setPosition(x, y);
            }
        }

        return this;
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
