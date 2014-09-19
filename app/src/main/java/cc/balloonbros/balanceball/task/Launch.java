package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.task.DrawableTask;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.scene.PlayScene;

public class Launch extends DrawableTask implements TimerEventListener {
    private Rect mSource = new Rect();
    private Rect mDestination = new Rect();
    private Bitmap mLogo = null;

    @Override
    public void onRegistered() {
        mLogo = getImage(R.drawable.launch_logo);
        setTimer(5000, this);
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onDraw(Canvas canvas) {
        mSource.set(0, 0, mLogo.getWidth(), mLogo.getHeight());
        mDestination.set(0, 0, getDisplaySize().x, getDisplaySize().y);
        canvas.drawBitmap(mLogo, mSource, mDestination, null);
    }

    @Override
    public void onTimer() {
        changeScene(new PlayScene(getGame()));
    }
}
