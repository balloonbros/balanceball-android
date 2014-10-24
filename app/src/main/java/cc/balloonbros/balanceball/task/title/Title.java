package cc.balloonbros.balanceball.task.title;

import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

/**
 * タイトルタスク
 */
public class Title extends AbstractTask implements Drawable {
    private DrawString mTitle = new DrawString(_s(R.string.app_name));

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_title));

        Point displaySize = getDisplaySize();
        int x = displaySize.x / 2;
        int y = displaySize.y / 3;

        mTitle.setStyle("title").setPosition(x, y);
    }

    @Override
    public void onDraw(Surface surface) {
        surface.draw(mTitle);
    }
}
