package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;

public class Score extends PositionableTask {
    @Override
    public void onRegistered() {
        Point displaySize = getDisplaySize();
        int x = displaySize.x / 2;
        int y = displaySize.y / 2 + 150;
        position(x, y);
    }

    @Override
    public void onDraw(Canvas canvas) {

    }
}
