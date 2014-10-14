package cc.balloonbros.balanceball.task.launch;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.MotionEvent;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.extender.TimerPlugin;
import cc.balloonbros.balanceball.lib.task.extender.Touchable;
import cc.balloonbros.balanceball.lib.task.timer.Timer;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.scene.TitleScene;

/**
 * ロゴ表示タスク
 */
public class Logo extends AbstractTask implements Drawable, Touchable, TimerEventListener {
    /** ロゴ画像の転送元矩形 */
    private Rect mSource = new Rect();

    /** ロゴ画像の転送先矩形 */
    private Rect mDestination = new Rect();

    /** ロゴ画像 */
    private Bitmap mLogo = null;

    /** タイマー */
    private Timer mTimer = null;

    @Override
    public void onRegister() {
        super.onRegister();

        mLogo = getImage(R.drawable.launch_logo);
        mSource.set(0, 0, mLogo.getWidth(), mLogo.getHeight());
        mDestination.set(0, 0, getDisplaySize().x, getDisplaySize().y);
        mTimer = plugin(TimerPlugin.class).setTimer(_i(R.integer.display_time_for_logo), this);
    }

    @Override
    public void onDraw(Surface surface) {
        surface.getCanvas().drawBitmap(mLogo, mSource, mDestination, null);
    }

    @Override
    public void onTimer() {
        changeScene(new TitleScene());
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        mTimer.stop();
        onTimer();
        return false;
    }
}
