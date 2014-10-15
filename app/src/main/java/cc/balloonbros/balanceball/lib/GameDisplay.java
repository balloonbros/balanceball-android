package cc.balloonbros.balanceball.lib;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.WindowManager;

public class GameDisplay {
    private Point mDisplaySize = new Point();
    private Rect mDisplayRect = new Rect();
    private GameMain mGame;

    public GameDisplay(GameMain game) {
        mGame = game;
        updateDisplaySize();
    }

    public Point getDisplaySize() {
        return mDisplaySize;
    }

    protected void updateDisplaySize() {
        WindowManager wm = (WindowManager)mGame.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(mDisplaySize);
        mDisplayRect.set(0, 0, mDisplaySize.x, mDisplaySize.y);
    }

    public Rect getDisplayRect() {
        return mDisplayRect;
    }
}
