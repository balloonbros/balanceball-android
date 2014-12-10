package cc.balloonbros.balanceball.lib.task.basic;

import android.graphics.Point;
import android.graphics.Rect;

import cc.balloonbros.balanceball.lib.graphic.DrawObject;
import cc.balloonbros.balanceball.lib.graphic.Positionable;
import cc.balloonbros.balanceball.lib.task.AbstractTask;

abstract public class PositionableTask extends AbstractTask implements Positionable {
    private DrawObject mDrawObject;

    /**
     * タスクにDrawObjectを関連付ける
     * @return 関連付けるDrawObject
     */
    public void relate(DrawObject drawObject) {
        mDrawObject = drawObject;
    }

    /**
     * オブジェクトの矩形を取得する
     * @return 矩形
     */
    public Rect getRect() {
        return mDrawObject.getRect();
    }

    /**
     * オブジェクトの描画位置をセットする
     * @param position 文字列の描画位置
     */
    @Override
    public Positionable setPosition(Point position) {
        return mDrawObject.setPosition(position);
    }

    /**
     * 文字列の描画位置をセットする
     * @param x x座標
     * @param y y座標
     */
    @Override
    public Positionable setPosition(int x, int y) {
        return mDrawObject.setPosition(x, y);
    }

    /**
     * 文字列の描画位置を取得する
     * @return 文字列の描画位置
     */
    @Override
    public Point getPosition() {
        return mDrawObject.getPosition();
    }

    /**
     * オブジェクトの移動可能範囲をリセットする
     */
    @Override
    public Positionable resetMovableArea() {
        return mDrawObject.resetMovableArea();
    }

    /**
     * オブジェクトの移動可能範囲をセットする
     * @param area 移動可能範囲
     */
    @Override
    public Positionable setMovableArea(Rect area) {
        return mDrawObject.setMovableArea(area);
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
        return mDrawObject.setMovableArea(x, y, width, height);
    }

    /**
     * 指定した座標に移動する。setPositionのエイリアス
     * @see #setPosition(int, int)
     * @param x x座標
     * @param y y座標
     */
    public void moveTo(int x, int y) {
        mDrawObject.moveTo(x, y);
    }

    /**
     * 指定した距離だけ移動する
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    @Override
    public Positionable move(int dx, int dy) {
        return mDrawObject.move(dx, dy);
    }

    /**
     * 指定した距離だけ移動する。
     * 移動可能範囲からはみ出た場合は自動的に範囲内に位置が調整される
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    @Override
    public Positionable moveInArea(int dx, int dy) {
        return mDrawObject.moveInArea(dx, dy);
    }

    /**
     * エリア中央に位置をセットする
     * @param area このエリアの中央位置にセットする
     */
    public Positionable moveToCenter(Rect area) {
        return mDrawObject.moveToCenter(area);
    }

    /**
     * 画面中央に位置をセットする
     */
    public Positionable moveToCenter() {
        return mDrawObject.moveToCenter();
    }

    /**
     * オブジェクトが左端に位置しているかどうかをチェックする
     * @return 左端に位置していればtrue
     */
    @Override
    public boolean isBorderLeftEdge() {
        return mDrawObject.isBorderLeftEdge();
    }

    /**
     * オブジェクトが右端に位置しているかどうかをチェックする
     * @return 右端に位置していればtrue
     */
    @Override
    public boolean isBorderRightEdge() {
        return mDrawObject.isBorderRightEdge();
    }

    /**
     * オブジェクトが上端に位置しているかどうかをチェックする
     * @return 上端に位置していればtrue
     */
    @Override
    public boolean isBorderTopEdge() {
        return mDrawObject.isBorderTopEdge();
    }

    /**
     * オブジェクトが下端に位置しているかどうかをチェックする
     * @return 下端に位置していればtrue
     */
    @Override
    public boolean isBorderBottomEdge() {
        return mDrawObject.isBorderBottomEdge();
    }

    @Override
    public int getWidth() {
        return mDrawObject.getWidth();
    }

    @Override
    public int getHeight() {
        return mDrawObject.getHeight();
    }
}
