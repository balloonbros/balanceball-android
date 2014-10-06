package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;
import cc.balloonbros.balanceball.lib.task.message.TaskMessageListener;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;

public class Score extends PositionableTask implements TaskMessageListener {
    /** ボールがサークルの中にとどまっていたフレーム数 */
    private int mFrameCountInCircle = 0;

    /** スコア用文字 */
    private DrawString mScore = new DrawString(11);

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_.i(R.integer.priority_score));

        Point displaySize = getDisplaySize();
        int x = displaySize.x / 2;
        int y = displaySize.y / 2 + 150;
        position(x, y);
    }

    @Override
    public void onDraw(Canvas canvas, Surface surface) {
        mScore.set("keep ").append(String.valueOf(getScore())).append(" sec");
        surface.draw(mScore);
    }

    @Override
    public void onMessage(AbstractTask sender, TaskMessage message) {
        if (message.getLabel().equals("into_the_circle")) {
            mFrameCountInCircle++;
        }
    }

    private int getScore() {
        return (int)(mFrameCountInCircle / getFps());
    }
}
