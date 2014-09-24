package cc.balloonbros.balanceball.task.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;

public class CenterCircle extends PositionableTask {
    private Bitmap mCircle = null;

    @Override
    public void onRegister() {
        super.onRegister();
        mCircle = getImage(R.drawable.area3);
        setPriority(_.i(R.integer.priority_center_circle));

        Point displaySize = getDisplaySize();
        int x = (displaySize.x / 2) - (mCircle.getWidth()  / 2);
        int y = (displaySize.y / 2) - (mCircle.getHeight() / 2);
        position(x, y);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Point p = getPosition();
        canvas.drawBitmap(mCircle, p.x, p.y, null);
    }
}
