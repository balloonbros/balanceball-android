package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.task.DrawableTask;

public class Launch extends DrawableTask {
    @Override
    public void onRegistered() {

    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onDraw(Canvas canvas) {
        Rect src = new Rect();
        Rect dest = new Rect();
        Bitmap logo = getImage(R.drawable.launch_logo);
        src.set(0, 0, logo.getWidth(), logo.getHeight());
        dest.set(0, 0, getDisplaySize().x, getDisplaySize().y);
        canvas.drawBitmap(logo, src, dest, null);
    }
}
