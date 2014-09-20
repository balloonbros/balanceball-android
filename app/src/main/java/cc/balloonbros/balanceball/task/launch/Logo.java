package cc.balloonbros.balanceball.task.launch;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.DrawableTask;
import cc.balloonbros.balanceball.lib.task.basic.TouchTask;
import cc.balloonbros.balanceball.lib.task.timer.Timer;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.scene.PlayScene;
import cc.balloonbros.balanceball.scene.TitleScene;

/**
 * ロゴ表示タスク
 */
public class Logo extends TouchTask implements Drawable, TimerEventListener {
    /** ロゴ画像の転送元矩形 */
    private Rect mSource = new Rect();

    /** ロゴ画像の転送先矩形 */
    private Rect mDestination = new Rect();

    /** ロゴ画像 */
    private Bitmap mLogo = null;

    /** タイマー */
    private Timer mTimer = null;

    @Override
    public void onRegistered() {
        super.onRegistered();

        mLogo = getImage(R.drawable.launch_logo);
        mSource.set(0, 0, mLogo.getWidth(), mLogo.getHeight());
        mDestination.set(0, 0, getDisplaySize().x, getDisplaySize().y);
        mTimer = setTimer(5000, this);
    }

    @Override
    public void onUpdate() { }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(mLogo, mSource, mDestination, null);
    }

    @Override
    public void onTimer() {
        changeScene(new TitleScene(getGame()));
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        mTimer.stop();
        onTimer();
        return true;
    }
}
