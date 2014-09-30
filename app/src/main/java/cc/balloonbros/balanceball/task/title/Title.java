package cc.balloonbros.balanceball.task.title;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Style;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.basic.TouchTask;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.scene.PlayScene;

/**
 * タイトルタスク
 */
public class Title extends TouchTask implements Drawable, TimerEventListener {
    private Point mTitlePosition = new Point();
    private int mAlpha = 0xff;
    private DrawString mTitle;
    private DrawString mLabel;

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_title));

        Style titleStyle = new Style().color(_c(R.color.title_font_color)).size(_i(R.integer.title_font_size)).font(getFont(_s(R.string.open_sans_bold))).center();
        mTitle = new DrawString(_s(R.string.app_name), titleStyle);

        Style labelStyle = new Style().color(_c(R.color.base_font_color)).size(_i(R.integer.game_start_label_font_size)).font(getFont(_s(R.string.open_sans_light))).center();
        mLabel = new DrawString(_s(R.string.game_start_label), labelStyle);

        Point displaySize = getDisplaySize();
        float x = displaySize.x / 2;
        float y = displaySize.y / 3;
        mTitlePosition.set((int) x, (int) y);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        changeScene(new PlayScene());
        return false;
    }

    @Override
    public void update() {
        mAlpha -= 5;
        if (mAlpha < 0) {
            mAlpha = 255;
            changeTask(null);
            setTimer(500, this);
        }
    }

    @Override
    public void onTimer() {
        changeTask(this);
    }

    @Override
    public void onDraw(Canvas canvas, Surface surface) {
        mLabel.setTemplate(mLabel.getTemplate().alpha(mAlpha));
        surface.draw(mLabel, mTitlePosition.x, 300);
        surface.draw(mTitle, mTitlePosition.x, mTitlePosition.y);
    }
}
