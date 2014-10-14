package cc.balloonbros.balanceball.task.play;

import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Sprite;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

/**
 * 風
 */
public class Wind extends AbstractTask implements Drawable {
    /** 風画像 */
    private Sprite mWind = null;

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

    /**
     * 画面から見えなくなったかどうかをチェックする
     * @return 画面から見えない状態であればtrue
     */
    public boolean isInvisible() {
        Point position = mWind.getPosition();
        Point displaySize = getDisplaySize();

        return position.x + mWind.getWidth()  <= 0             ||
               position.x                     >= displaySize.x ||
               position.y + mWind.getHeight() <= 0             ||
               position.y                     >= displaySize.y;
    }

    @Override
    public void onRegister() {
        super.onRegister();
        setTag("wind");
        setPriority(_i(R.integer.priority_wind));
        mWind = Sprite.from(getImage(R.drawable.wind3));
    }

    @Override
    public void update() {
        // ボールに風の影響を与える
        // 風の方向と移動距離からボールの次の位置を計算して移動させる
        Ball ball = (Ball)find(_i(R.integer.priority_ball));
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

        mWind.moveTo(10, 0);
        if (isInvisible()) {
            kill();
        }
    }

    @Override
    public void onDraw(Surface surface) {
        surface.draw(mWind);
    }
}
