package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.TaskPriority;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.Drawable;
import cc.balloonbros.balanceball.lib.Updateable;
import cc.balloonbros.balanceball.task.message.LabelMessage;

/**
 * 風
 */
public class Wind extends AbstractTask implements Updateable, Drawable {
    /**
     * 風の座標
     */
    private Point mCoordinates = new Point();

    /**
     * 風の画像
     */
    private Bitmap mWind = null;

    @Override
    public void onRegistered() {
        mWind = getImage(R.drawable.ic_launcher);
        mCoordinates.set(0, 200);
    }

    @Override
    public void onUpdate() {
        // ボールに風の影響を与える
        Ball ball = (Ball)find(TaskPriority.BALL);
        ball.move((getDisplaySize().x - mCoordinates.x) / 100, 0);

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
