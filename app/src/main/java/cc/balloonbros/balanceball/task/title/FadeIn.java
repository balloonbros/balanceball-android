package cc.balloonbros.balanceball.task.title;

import android.graphics.Canvas;
import android.graphics.Color;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

public class FadeIn extends AbstractTask implements Drawable {
    private int mAlpha = 0xff;

    @Override
    public void onRegister() {
        setPriority(_i(R.integer.priority_fade_in));
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
    public void onDraw(Canvas canvas, Surface surface) {
        canvas.drawColor(Color.BLACK & mAlpha << 24);
    }
}
