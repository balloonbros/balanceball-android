package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.AbstractTask;
import cc.balloonbros.balanceball.lib.Drawable;

public class CenterCircle extends AbstractTask implements Drawable {
    private Bitmap mCircle = null;

    @Override
    public void onRegistered() {
        mCircle = getImage(R.drawable.circle);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Point displaySize = getDisplaySize();
        int x = (displaySize.x / 2) - (mCircle.getWidth()  / 2);
        int y = (displaySize.y / 2) - (mCircle.getHeight() / 2);
        Log.d("tkengo", String.valueOf(displaySize.y));
        Log.d("tkengo", String.valueOf(mCircle.getHeight()));
        canvas.drawBitmap(mCircle, x, y, null);
    }
}
