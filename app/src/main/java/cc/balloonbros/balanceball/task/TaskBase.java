package cc.balloonbros.balanceball.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.WindowManager;

import cc.balloonbros.balanceball.GameMain;
import cc.balloonbros.balanceball.GameSurfaceView;

abstract public class TaskBase {
    private GameMain mGame = null;

    abstract public void execute(Canvas canvas);

    public void onRegistered() { }

    public void setGame(GameMain game) {
        mGame = game;
    }
    protected Bitmap getImage(int assetId) {
        return mGame.getAssetManager().getImage(assetId);
    }
    protected Context getContext() {
        return mGame.getContext();
    }
    protected TaskManager getTaskManager() {
        return mGame.getTaskManager();
    }
    protected Point getDisplaySize() {
        Point displaySize = new Point();
        mGame.getWindowManager().getDefaultDisplay().getSize(displaySize);
        return displaySize;
    }
}
