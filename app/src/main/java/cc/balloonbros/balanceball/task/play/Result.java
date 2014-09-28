package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Color;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

public class Result extends AbstractTask implements Drawable {
    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_.i(R.integer.priority_result));
    }

    @Override
    public void onDraw(Canvas canvas, Surface surface) {
        canvas.drawColor(Color.BLACK & 0xaa << 24);
    }
}
