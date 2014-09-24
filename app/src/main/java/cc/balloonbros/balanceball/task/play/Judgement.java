package cc.balloonbros.balanceball.task.play;

import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;

public class Judgement extends AbstractTask {
    /** ボール */
    private Ball mBall = null;

    /** 中央の円 */
    private CenterCircle mCenterCircle;

    @Override
    public void onRegistered() {
        super.onRegistered();
        setPriority(_.i(R.integer.priority_judgement));

        mCenterCircle = (CenterCircle)find(_.i(R.integer.priority_center_circle));
        mBall         = (Ball)        find(_.i(R.integer.priority_ball));
    }

    @Override
    public void update() {
        Point bp = mBall.getPosition();
        Point cp = mCenterCircle.getPosition();
        int dx = bp.x - cp.x;
        int dy = bp.y - cp.y;

        int collisionDistance = mBall.getRadius() + mCenterCircle.getRadius() - 10;
        if (dx * dx + dy * dy < collisionDistance * collisionDistance) {

        }
    }
}
