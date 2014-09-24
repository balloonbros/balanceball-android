package cc.balloonbros.balanceball.lib.task.basic;

import android.graphics.Point;

import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

abstract public class PositionableTask extends AbstractTask implements Drawable {
    /** オブジェクトの座標 */
    Point mCoordinate = new Point();

    @Override
    public void onRegister() {
        super.onRegister();
        mCoordinate.set(0, 0);
    }

    /**
     * オブジェクトを指定した座標に設置する
     * @param x x座標
     * @param y y座標
     */
    public void position(int x, int y) {
        mCoordinate.set(x, y);
    }

    /**
     * 現在の座標を取得する
     * @return 現在の座標
     */
    public Point getPosition() {
        return mCoordinate;
    }
}
