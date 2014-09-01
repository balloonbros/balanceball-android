package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import cc.balloonbros.balanceball.R;

/**
 * ボールタスク
 */
public class Ball extends TaskBase implements Updateable, Drawable {
    /**
     * ボールの座標
     */
    private Point mCoordinates = new Point();

    /**
     * ボールの動く方向
     */
    private int mMovingDirection = 1;

    /**
     * ボール画像
     */
    private Bitmap mBall = null;

    @Override
    public void onRegistered() {
        mCoordinates.set(0, 0);
        mBall = getImage(R.drawable.ic_launcher);
    }

    @Override
    public void onUpdate() {
        if (mCoordinates.x + mBall.getWidth() >= getDisplaySize().x) {
            mMovingDirection = -1;
        } else if (mCoordinates.x <= 0){
            mMovingDirection = 1;
        }

        //mCoordinates.x += (mMovingDirection * 4);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBall, mCoordinates.x, 100, null);
    }
}
