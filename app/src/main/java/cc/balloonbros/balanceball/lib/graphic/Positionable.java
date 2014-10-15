package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Point;
import android.graphics.Rect;

public interface Positionable {
    /**
     * オブジェクトの描画位置をセットする
     * @param position 文字列の描画位置
     */
    public Positionable setPosition(Point position);

    /**
     * 文字列の描画位置をセットする
     * @param x x座標
     * @param y y座標
     */
    public Positionable setPosition(int x, int y);

    /**
     * 文字列の描画位置を取得する
     * @return 文字列の描画位置
     */
    public Point getPosition();

    /**
     * オブジェクトの移動可能範囲をセットする
     * @param area 移動可能範囲
     */
    public void setMovableArea(Rect area);

    /**
     * オブジェクトの移動可能範囲をセットする
     * @param x x座標
     * @param y y座標
     * @param width 幅
     * @param height 高さ
     */
    public void setMovableArea(int x, int y, int width, int height);

    /**
     * 指定した距離だけ移動する
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    public void move(int dx, int dy);

    /**
     * 指定した距離だけ移動する。
     * 移動可能範囲からはみ出た場合は自動的に範囲内に位置が調整される
     * @param dx x方向の移動量
     * @param dy y方向の移動量
     */
    public void moveInArea(int dx, int dy);

    /**
     * 画面中央に位置をセットする
     * @param area このエリアの中央位置にセットする
     */
    public void moveToCenter(Rect area);

    /**
     * オブジェクトが左端に位置しているかどうかをチェックする
     * @return 左端に位置していればtrue
     */
    public boolean isBorderLeftEdge();

    /**
     * オブジェクトが右端に位置しているかどうかをチェックする
     * @return 右端に位置していればtrue
     */
    public boolean isBorderRightEdge();

    /**
     * オブジェクトが上端に位置しているかどうかをチェックする
     * @return 上端に位置していればtrue
     */
    public boolean isBorderTopEdge();

    /**
     * オブジェクトが下端に位置しているかどうかをチェックする
     * @return 下端に位置していればtrue
     */
    public boolean isBorderBottomEdge();

    /**
     * このオブジェクトの幅を取得する。
     * @return 幅
     */
    public int getWidth();

    /**
     * このオブジェクトの高さを取得する
     * @return 高さ
     */
    public int getHeight();
}
