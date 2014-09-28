package cc.balloonbros.balanceball.task.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;

public class CenterCircle extends PositionableTask {
    /** 中央円の画像 */
    private Bitmap mCircle = null;

    /**
     * 中央円の半径を取得する
     * @return 中央円の半径
     */
    public int getRadius() {
        return mCircle.getWidth() / 2;
    }

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
    public void onDraw(Canvas canvas, Surface surface) {
        Point p = getPosition();
        canvas.drawBitmap(mCircle, p.x, p.y, null);
    }
}
