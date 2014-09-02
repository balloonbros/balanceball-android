package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.Drawable;
import cc.balloonbros.balanceball.lib.TaskBase;
import cc.balloonbros.balanceball.lib.Updateable;

/**
 * ボールタスク
 */
public class Ball extends TaskBase implements Drawable {
    /**
     * ボールの座標
     */
    private Point mCoordinates = new Point();

    /**
     * ボール画像
     */
    private Bitmap mBall = null;

    @Override
    public void onRegistered() {
        mCoordinates.set(0, 100);
        mBall = getImage(R.drawable.ic_launcher);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBall, mCoordinates.x, mCoordinates.y, null);
    }

    /**
     * ボールを現在の位置から指定したオフセットだけ動かす
     * @param dx
     * @param dy
     */
    public void move(int dx, int dy) {
        mCoordinates.offset(dx, dy);

        // ボールが画面外にはみ出してたら位置を調整する
        if (mCoordinates.x < 0) {
            mCoordinates.x = 0;
        } else if (mCoordinates.x + mBall.getWidth() >= getDisplaySize().x) {
            mCoordinates.x = getDisplaySize().x - mBall.getWidth();
        }

        if (mCoordinates.y < 0) {
            mCoordinates.y = 0;
        } else if (mCoordinates.y + mBall.getHeight() >= getDisplaySize().y) {
            mCoordinates.y = getDisplaySize().y - mBall.getHeight();
        }
    }
}
