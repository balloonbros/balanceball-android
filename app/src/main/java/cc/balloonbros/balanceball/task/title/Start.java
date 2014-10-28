package cc.balloonbros.balanceball.task.title;

import android.graphics.Point;
import android.view.MotionEvent;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.extender.TimerPlugin;
import cc.balloonbros.balanceball.lib.task.extender.Touchable;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.scene.PlayScene;

/**
 * スタートボタン
 * 点滅アニメーションする
 */
public class Start extends AbstractTask implements Drawable, Touchable, TimerEventListener {
    private int mAlpha = 0xff;
    private DrawString mLabel = new DrawString(_s(R.string.game_start_label));

    @Override
    public void onRegister() {
        super.onRegister();

        Point displaySize = getDisplaySize();
        int x = displaySize.x / 2;
        int y = displaySize.y - (displaySize.y / 3);

        mLabel.setStyle("touch_to_start").setPosition(x, y);
    }

    @Override
    public void update() {
        mAlpha -= 5;
        if (mAlpha < 0) {
            mAlpha = 0xff;
            changeTask(null);
            plugin(TimerPlugin.class).setTimer(500, this);
        }
    }

    @Override
    public void onDraw(Surface surface) {
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
