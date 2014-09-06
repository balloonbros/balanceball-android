package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.TaskPriority;
import cc.balloonbros.balanceball.lib.Drawable;
import cc.balloonbros.balanceball.lib.Updateable;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.task.message.LabelMessage;

/**
 * 風
 */
public class Wind extends AbstractTask implements Updateable, Drawable {
    private Point mCoordinates = new Point();
    private Bitmap mWind = null;

    /**
     * 風の方向と移動距離
     */
    private int mAngle = 0;
    private int mAcceleration = 0;
    private int mSpeed = 0;

    public Wind(int angle) {
        super();
        mAngle = angle;
        mSpeed = 1;
        mAcceleration = 1;
    }

    @Override
    public void onRegistered() {
        mWind = getImage(R.drawable.ic_launcher);
        mCoordinates.set(0, 200);
    }

    @Override
    public void onUpdate() {
        // ボールに風の影響を与える
        // 風の方向と移動距離からボールの次の位置を計算して移動させる
        Ball ball = (Ball)find(TaskPriority.BALL);
        if (mSpeed >= 0) {
            int distance = mSpeed / 4;
            int dx = (int)(Math.cos(Math.toRadians(mAngle)) * distance);
            int dy = (int)(Math.sin(Math.toRadians(mAngle)) * distance);
            ball.move(dx, dy);
            mSpeed += mAcceleration;
            if (mSpeed > 30) {
                mAcceleration = -1;
            }
        }
        //ball.move((getDisplaySize().x - mCoordinates.x) / 100, 0);

        mCoordinates.x += 10;
        if (mCoordinates.x >= getDisplaySize().x) {
            sendMessage((WindOutBreaker)getParent(), new LabelMessage("wind_leaved"));
            kill();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(mWind, mCoordinates.x, mCoordinates.y, null);
    }
}
