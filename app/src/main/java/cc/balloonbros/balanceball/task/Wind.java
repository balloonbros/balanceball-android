package cc.balloonbros.balanceball.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.WindowManager;

import cc.balloonbros.balanceball.R;

public class Wind extends TaskBase {
    private Point mCoordinates = new Point();

    @Override
    public void onRegistered() {
        mCoordinates.set(0, 200);
    }

    @Override
    public void execute(Canvas canvas) {
        mCoordinates.x += 10;

        Point displaySize = new Point();
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(displaySize);

        Bitmap ic = getImage(R.drawable.ic_launcher);

        if (mCoordinates.x >= displaySize.x) {
        }

        canvas.drawBitmap(ic, mCoordinates.x, mCoordinates.y, null);
    }
}
