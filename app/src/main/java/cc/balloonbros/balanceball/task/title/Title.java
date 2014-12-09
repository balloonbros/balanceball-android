package cc.balloonbros.balanceball.task.title;

import android.graphics.Color;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Sprite;
import cc.balloonbros.balanceball.lib.graphic.Text;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.graphic.shape.Rectangle;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

/**
 * タイトルを描画する
 */
public class Title extends AbstractTask implements Drawable {
    private Text mTitle = new Text(_s(R.string.app_name));
    private Sprite mSprite;

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_title));

        Point displaySize = getDisplaySize();
        int x = displaySize.x / 2;
        int y = displaySize.y / 3;

        mTitle.setStyle("title").setPosition(x, y);
        mSprite = getTexture(R.drawable.circle_nodpi).toSprite();
    }

    @Override
    public void onDraw(Surface surface) {
        //surface.draw(mTitle);
        mSprite.draw();
        //mRect.draw();
    }
}
