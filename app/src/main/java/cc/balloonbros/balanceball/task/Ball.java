package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.Random;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.AbstractTask;
import cc.balloonbros.balanceball.lib.Drawable;
import cc.balloonbros.balanceball.lib.Updateable;

/**
 * ボールタスク
 */
public class Ball extends AbstractTask implements Updateable, Drawable {
    /**
     * ボールの座標
     */
    private Point mCoordinates = new Point();

    /**
     * ボール画像
     */
    private Bitmap mBall = null;

    private Random mRandom = new Random();

    @Override
    public void onRegistered() {
        mCoordinates.set(0, 100);
        mBall = getImage(R.drawable.ic_launcher);
    }

    @Override
    public void onUpdate() {
        // ランダムでぶれさせる
        move(mRandom.nextInt(3) - 1, mRandom.nextInt(3) - 1);
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

        Point displaySize = getDisplaySize();

        // ボールが画面外にはみ出してたら位置を調整する
        if (mCoordinates.x < 0) {
            mCoordinates.x = 0;
        } else if (mCoordinates.x + mBall.getWidth() >= displaySize.x) {
            mCoordinates.x = displaySize.x - mBall.getWidth();
        }

        if (mCoordinates.y < 0) {
            mCoordinates.y = 0;
        } else if (mCoordinates.y + mBall.getHeight() >= displaySize.y) {
            mCoordinates.y = displaySize.y - mBall.getHeight();
        }
    }
}
