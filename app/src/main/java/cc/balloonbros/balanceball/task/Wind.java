package cc.balloonbros.balanceball.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;

public class Wind extends TaskBase implements Updateable, Drawable {
    private Point mCoordinates = new Point();
    private Bitmap mWind = null;
    private WindOutBreaker mParent = null;

    public Wind(WindOutBreaker parent) {
        mParent = parent;
    }

    @Override
    public void onRegistered() {
        mWind = getImage(R.drawable.ic_launcher);
        mCoordinates.set(0, 200);
    }

    @Override
    public void onUpdate() {
        mCoordinates.x += 10;

        if (mCoordinates.x >= getDisplaySize().x) {
            sendMessage(mParent, null);
            kill();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(mWind, mCoordinates.x, mCoordinates.y, null);
    }
}
