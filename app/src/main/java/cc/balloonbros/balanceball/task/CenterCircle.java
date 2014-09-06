package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.Drawable;

public class CenterCircle extends AbstractTask implements Drawable {
    private Bitmap mCircle = null;
    private Point mCoordinates = new Point();

    @Override
    public void onRegistered() {
        mCircle = getImage(R.drawable.circle);

        Point displaySize = getDisplaySize();
        int x = (displaySize.x / 2) - (mCircle.getWidth()  / 2);
        int y = (displaySize.y / 2) - (mCircle.getHeight() / 2);
        mCoordinates.set(x, y);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(mCircle, mCoordinates.x, mCoordinates.y, null);
    }
}