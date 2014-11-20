package cc.balloonbros.balanceball.task.play;

import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.SpriteForSurfaceView;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;

public class CenterCircle extends PositionableTask implements Drawable {
    /** 中央円の画像 */
    private SpriteForSurfaceView mCircle = null;

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
        mCircle = new SpriteForSurfaceView(getImage(R.drawable.area3));
        setPriority(_i(R.integer.priority_center_circle));
        relate(mCircle);

        Point displaySize = getDisplaySize();
        int x = (displaySize.x / 2) - (mCircle.getWidth()  / 2);
        int y = (displaySize.y / 2) - (mCircle.getHeight() / 2);
        mCircle.moveTo(x, y);
    }

    @Override
    public void onDraw(Surface surface) {
        surface.draw(mCircle);
    }
}
