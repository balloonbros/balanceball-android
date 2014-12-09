package cc.balloonbros.balanceball.task.launch;

import android.view.MotionEvent;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Sprite;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.scene.transition.FadeIn;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.extender.TimerPlugin;
import cc.balloonbros.balanceball.lib.task.extender.TouchPlugin;
import cc.balloonbros.balanceball.lib.task.extender.Touchable;
import cc.balloonbros.balanceball.lib.task.timer.Timer;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.scene.TitleScene;

/**
 * Balloonbrosのロゴを画面いっぱいに表示する。
 * 画面タッチまたは3秒経過でタイトルシーンに遷移する。
 */
public class Logo extends AbstractTask implements Drawable, Touchable, TimerEventListener {
    private Sprite mLogo;
    private Timer mTimer;

    @Override
    public void onRegister() {
        super.onRegister();

        mLogo = getTexture(R.drawable.launch_logo8).toSprite();
        mTimer = plugin(TimerPlugin.class).setTimer(_i(R.integer.display_time_for_logo), this);
    }

    @Override
    public void onDraw(Surface surface) {
        mLogo.drawStretch();
    }

    @Override
    public void onTimer() {
        goToNextScene();
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        mTimer.stop();
        goToNextScene();
        return false;
    }

    private void goToNextScene() {
        removePlugin(TouchPlugin.class);
        changeScene(new TitleScene()).with(new FadeIn(1000));
    }
}
