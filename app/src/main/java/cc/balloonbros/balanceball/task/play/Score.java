package cc.balloonbros.balanceball.task.play;

import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Text;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.message.TaskMessageListener;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;

public class Score extends AbstractTask implements Drawable, TaskMessageListener {
    /** ボールがサークルの中にとどまっていたフレーム数 */
    private int mFrameCountInCircle = 0;

    /** スコア用文字 */
    private Text mScore = new Text(11);

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_score));

        Point displaySize = getDisplaySize();
        int x = displaySize.x / 2;
        int y = displaySize.y / 2 + 150;
        mScore.setStyle(getStyle("score")).setPosition(x, y);
    }

    @Override
    public void onDraw(Surface surface) {
        mScore.format("keep %d sec", getScore());
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
