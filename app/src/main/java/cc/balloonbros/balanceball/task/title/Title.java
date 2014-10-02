package cc.balloonbros.balanceball.task.title;

import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Style;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;

/**
 * タイトルタスク
 */
public class Title extends PositionableTask implements Drawable {
    private DrawString mTitle;

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_title));

        Style titleStyle = Style.getDefault().size(_i(R.integer.title_font_size)).color(_c(R.color.title_font_color)).font(getFont(_s(R.string.open_sans_bold)));
        mTitle = new DrawString(_s(R.string.app_name), titleStyle);

        Point displaySize = getDisplaySize();
        int x = displaySize.x / 2;
        int y = displaySize.y / 3;
        position(x, y);
    }

    @Override
    public void onDraw(Canvas canvas, Surface surface) {
        surface.draw(mTitle);
    }
}
