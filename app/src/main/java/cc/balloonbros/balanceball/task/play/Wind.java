package cc.balloonbros.balanceball.task.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.basic.MovableTask;

/**
 * 風
 */
public class Wind extends MovableTask {
    /** 風画像 */
    private Bitmap mWind = null;

    /** 風の方向と移動距離 */
    private int mAngle = 0;
    private int mAcceleration = 2;
    private int mSpeed = 1;

    /**
     * コンストラクタ。風の方向を指定する
     * @param angle 風の方向
     */
    public Wind(int angle) {
        super();
        mAngle = angle;
    }

    @Override
    public void onRegister() {
        super.onRegister();
        setTag("wind");
        setPriority(_.i(R.integer.priority_wind));
        mWind = getImage(R.drawable.wind3);
    }

    @Override
    public void update() {
        // ボールに風の影響を与える
        // 風の方向と移動距離からボールの次の位置を計算して移動させる
        Ball ball = (Ball)find(_.i(R.integer.priority_ball));
        if (mSpeed >= 0) {
            int distance = mSpeed / 10;
            int dx = (int)(Math.cos(Math.toRadians(mAngle)) * distance);
            int dy = (int)(Math.sin(Math.toRadians(mAngle)) * distance);
            ball.moveInArea(dx, dy);
            mSpeed += mAcceleration;
            if (mSpeed > 50) {
                mAcceleration = -1;
            }
        }

        move(10, 0);
        if (isInvisible()) {
            kill();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        Point p = getPosition();
        canvas.drawBitmap(mWind, p.x, p.y, null);
    }

    @Override
    public int getWidth() {
        return mWind.getWidth();
    }

    @Override
    public int getHeight() {
        return mWind.getHeight();
    }
}
