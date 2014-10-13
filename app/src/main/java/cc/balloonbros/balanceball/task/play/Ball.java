package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawableObject;
import cc.balloonbros.balanceball.lib.graphic.Sprite;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.DrawableAttachment;

/**
 * ボールタスク
 */
public class Ball extends AbstractTask implements Drawable, DrawableAttachment {
    /** ボール画像 */
    private Sprite mBall = null;

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
        setPriority(_i(R.integer.priority_ball));
        mBall = Sprite.from(getImage(R.drawable.ball3));

        // 最初は真ん中に配置
        Point displaySize = getDisplaySize();
        int x = (displaySize.x / 2) - (mBall.getWidth()  / 2);
        int y = (displaySize.y / 2) - (mBall.getHeight() / 2);
        mBall.moveTo(x, y);
    }

    @Override
    public void onDraw(Canvas canvas, Surface surface) {
        surface.draw(mBall);
    }

    @Override
    public DrawableObject getDrawableObject() {
        return mBall;
    }
}
