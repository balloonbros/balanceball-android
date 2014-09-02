package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.TaskPriority;
import cc.balloonbros.balanceball.lib.Drawable;
import cc.balloonbros.balanceball.lib.TaskBase;
import cc.balloonbros.balanceball.lib.Updateable;

/**
 * 風
 */
public class Wind extends TaskBase implements Updateable, Drawable {
    /**
     * 風の座標
     */
    private Point mCoordinates = new Point();

    /**
     * 風の画像
     */
    private Bitmap mWind = null;

    /**
     * 風の発生源
     */
    private WindOutBreaker mParent = null;

    /**
     * コンストラクタ。
     * 風の発生源を受け取って保存しておく
     *
     * @param parent 風の発生源タスク
     */
    public Wind(WindOutBreaker parent) {
        mParent = parent;
    }

    @Override
    public void onRegistered() {
        mWind = getImage(R.drawable.ic_launcher);
        mCoordinates.set(0, 200);
    }

    @Override
    public void onUpdate() {
        mCoordinates.x += 10;

        Ball ball = (Ball)find(TaskPriority.BALL);
        ball.move((getDisplaySize().x - mCoordinates.x) / 100, 0);

        if (mCoordinates.x >= getDisplaySize().x) {
            sendMessage(mParent, null);
            kill();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(mWind, mCoordinates.x, mCoordinates.y, null);
    }
}
