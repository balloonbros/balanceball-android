package cc.balloonbros.balanceball.task.title;

import android.graphics.Canvas;
import android.view.MotionEvent;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Style;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.Touchable;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.scene.PlayScene;

public class Start extends PositionableTask implements Touchable, TimerEventListener {
    private int mAlpha = 0xff;
    private DrawString mLabel;

    @Override
    public void onRegister() {
        super.onRegister();

        Style labelStyle = Style.getDefault().size(_i(R.integer.game_start_label_font_size));
        mLabel = new DrawString(_s(R.string.game_start_label), labelStyle);

        position(getDisplaySize().x / 2, 300);
    }

    @Override
    public void update() {
        mAlpha -= 5;
        if (mAlpha < 0) {
            mAlpha = 0xff;
            changeTask(null);
            setTimer(500, this);
        }
    }

    @Override
    public void onDraw(Canvas canvas, Surface surface) {
        mLabel.getStyle().alpha(mAlpha);
        surface.draw(mLabel);
    }

    @Override
    public void onTimer() {
        changeTask(this);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        changeScene(new PlayScene());
        return false;
    }
}
