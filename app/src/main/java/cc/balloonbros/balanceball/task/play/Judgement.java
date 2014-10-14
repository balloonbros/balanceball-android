package cc.balloonbros.balanceball.task.play;

import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.message.BasicMessage;

public class Judgement extends AbstractTask {
    /** ボール */
    private Ball mBall = null;

    /** 中央の円 */
    private CenterCircle mCenterCircle;

    /** スコアタスク */
    private Score mScore;

    /** スコアタスクに送信するメッセージ */
    private BasicMessage mMessageToScore = new BasicMessage("into_the_circle");

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_judgement));

        mCenterCircle = (CenterCircle)find(_i(R.integer.priority_center_circle));
        mBall         = (Ball)        find(_i(R.integer.priority_ball));
        mScore        = (Score)       find(_i(R.integer.priority_score));
    }

    @Override
    public void update() {
        Point bp = mBall.getPosition();
        Point cp = mCenterCircle.getPosition();
        int dx = bp.x - cp.x;
        int dy = bp.y - cp.y;

        int collisionDistance = mCenterCircle.getRadius() - (mBall.getRadius() / 2);
        if (dx * dx + dy * dy < collisionDistance * collisionDistance) {
            sendMessage(mScore, mMessageToScore);
        }
    }
}
