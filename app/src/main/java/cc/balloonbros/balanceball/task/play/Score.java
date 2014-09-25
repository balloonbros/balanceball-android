package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;
import cc.balloonbros.balanceball.lib.task.message.TaskEventListener;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;

public class Score extends PositionableTask implements TaskEventListener {
    /** ボールがサークルの中にとどまっていたフレーム数 */
    private int mFrameCountInCircle = 0;

    /** スコア文字用 */
    private Paint mPaint = new Paint();

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_.i(R.integer.priority_score));
        Point displaySize = getDisplaySize();
        int x = displaySize.x / 2;
        int y = displaySize.y / 2 + 150;
        position(x, y);

        mPaint.setColor(_.c(R.color.score_color));
        mPaint.setTextSize(26F);
        mPaint.setTypeface(getFont(_.s(R.string.open_sans_light)));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Point p = getPosition();
        canvas.drawText("keep      sec", p.x, p.y, mPaint);
        canvas.drawText(String.valueOf(getScore()), p.x + 10, p.y, mPaint);
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
