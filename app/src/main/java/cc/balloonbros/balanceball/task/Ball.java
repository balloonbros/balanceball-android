package cc.balloonbros.balanceball.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.WindowManager;

import cc.balloonbros.balanceball.R;

public class Ball extends TaskBase {
    private Point mCoordinates;
    private int mMovingDirection;

    @Override
    public void onRegistered() {
        mCoordinates = new Point();
        mCoordinates.set(0, 0);

        mMovingDirection = 1;
    }

    @Override
    public void execute(Canvas canvas) {
        Point displaySize = new Point();
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(displaySize);

        Bitmap ic = getImage(R.drawable.ic_launcher);

        if (mCoordinates.x + ic.getWidth() >= displaySize.x) {
            mMovingDirection = -1;
        } else if (mCoordinates.x <= 0){
            mMovingDirection = 1;
        }

        mCoordinates.x += (mMovingDirection * 4);
        canvas.drawBitmap(ic, mCoordinates.x, 100, null);
    }
}
