package cc.balloonbros.balanceball.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cc.balloonbros.balanceball.GameSurfaceView;

abstract public class TaskBase {
    private GameSurfaceView mView = null;

    abstract public void execute(Canvas canvas);

    public void onRegistered() { }

    public void setView(GameSurfaceView view) {
        mView = view;
    }
    protected Bitmap getImage(int assetId) {
        return mView.getAssetManager().getImage(assetId);
    }
    protected Context getContext() {
        return mView.getContext();
    }
}
