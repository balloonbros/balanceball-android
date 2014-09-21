package cc.balloonbros.balanceball.task.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.task.DrawableTask;

/**
 * ボールタスク
 */
public class Ball extends DrawableTask {
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
        mBall = getImage(R.drawable.ball3);

        // 最初は真ん中に配置
        Point displaySize = getDisplaySize();
        int x = (displaySize.x / 2) - (mBall.getWidth()  / 2);
        int y = (displaySize.y / 2) - (mBall.getHeight() / 2);
        mCoordinates.set(x, y);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBall, mCoordinates.x, mCoordinates.y, null);
    }

    public boolean isBorderLeftEdge() {
        return mCoordinates.x <= 0;
    }
    public boolean isBorderRightEdge() {
        return mCoordinates.x + mBall.getWidth() >= getDisplaySize().x;
    }
    public boolean isBorderTopEdge() {
        return mCoordinates.y <= 0;
    }
    public boolean isBorderBottomEdge() {
        return mCoordinates.y + mBall.getHeight() >= getDisplaySize().y;
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
        if (isBorderLeftEdge()) {
            mCoordinates.x = 0;
        } else if (isBorderRightEdge()) {
            mCoordinates.x = displaySize.x - mBall.getWidth();
        }

        if (isBorderTopEdge()) {
            mCoordinates.y = 0;
        } else if (isBorderBottomEdge()) {
            mCoordinates.y = displaySize.y - mBall.getHeight();
        }
    }
}
