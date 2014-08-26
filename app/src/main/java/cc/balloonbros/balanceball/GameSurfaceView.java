package cc.balloonbros.balanceball;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Thread mGameLoopThread = null;
    private GameMain mGame = null;

    public GameSurfaceView(Context context, GameMain game) {
        super(context);

        mGame = game;

        getHolder().addCallback(this);
    }
    
    public Thread getGameLoopThread() {
        return mGameLoopThread;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        GameLoop loop = new GameLoop(mGame);
        mGameLoopThread = new Thread(loop);
        mGameLoopThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mGameLoopThread = null;
    }
}
