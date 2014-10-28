package cc.balloonbros.balanceball.task;

import android.graphics.Color;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

public class Background extends AbstractTask implements Drawable {
    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_background));
    }

    @Override
    public void onDraw(Surface surface) {
        surface.fill(Color.WHITE);
    }
}
