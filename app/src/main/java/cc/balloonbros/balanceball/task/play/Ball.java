package cc.balloonbros.balanceball.task.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.basic.MovableTask;

/**
 * ボールタスク
 */
public class Ball extends MovableTask {
    /** ボール画像 */
    private Bitmap mBall = null;

    /**
     * ボールの半径を取得する
     * @return ボールの半径
     */
    public int getRadius() {
        return mBall.getWidth() / 2;
    }

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_.i(R.integer.priority_ball));
        mBall = getImage(R.drawable.ball3);

        // 最初は真ん中に配置
        Point displaySize = getDisplaySize();
        int x = (displaySize.x / 2) - (mBall.getWidth()  / 2);
        int y = (displaySize.y / 2) - (mBall.getHeight() / 2);
        moveTo(x, y);
    }

    @Override
    public int getWidth() {
        return mBall.getWidth();
    }

    @Override
    public int getHeight() {
        return mBall.getHeight();
    }

    @Override
    public void onDraw(Canvas canvas, Surface surface) {
        Point p = getPosition();
        canvas.drawBitmap(mBall, p.x, p.y, null);
    }
}
