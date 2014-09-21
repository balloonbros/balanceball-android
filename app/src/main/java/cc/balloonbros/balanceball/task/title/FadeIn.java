package cc.balloonbros.balanceball.task.title;

import android.graphics.Canvas;
import android.graphics.Color;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.DrawableTask;

public class FadeIn extends DrawableTask {
    private int mAlpha = 0xff;

    @Override
    public void onRegistered() {
        setPriority(_.i(R.integer.priority_fade_in));
    }

    @Override
    public void update() {
        mAlpha -= 10;
        if (mAlpha <= 0) {
            mAlpha = 0;
            kill();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.argb(mAlpha, 0, 0, 0));
    }
}
