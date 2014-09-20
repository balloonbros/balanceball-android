package cc.balloonbros.balanceball.lib.task;

import android.graphics.Point;

import cc.balloonbros.balanceball.lib.task.system.AbstractTask;

public abstract class DrawableTask extends AbstractTask implements Drawable {
    private Point mCoordinate = new Point();

    public DrawableTask() {
        super();
    }

    public DrawableTask(int priority) {
        super(priority);
    }

    @Override
    public void onRegistered() {
        super.onRegistered();

        mCoordinate.set(0, 0);
    }

    public void move(int dx, int dy) {
        mCoordinate.offset(dx, dy);
    }

    public void moveTo(int x, int y) {
        mCoordinate.set(x, y);
    }

    public Point getPosition() {
        return mCoordinate;
    }
}
