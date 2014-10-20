package cc.balloonbros.balanceball.task.play;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Sprite;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;

/**
 * ボールタスク
 */
public class Ball extends PositionableTask implements Drawable {
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
        mBall = new Sprite(getImage(R.drawable.ball3));
        relate(mBall);

        // 最初は真ん中に配置
        mBall.moveToCenter();
    }

    @Override
    public void onDraw(Surface surface) {
        surface.draw(mBall);
    }
}
