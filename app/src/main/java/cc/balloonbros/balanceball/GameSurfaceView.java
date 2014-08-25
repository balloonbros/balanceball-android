package cc.balloonbros.balanceball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cc.balloonbros.balanceball.task.Ball;
import cc.balloonbros.balanceball.task.TaskManager;

public class GameSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private SurfaceHolder mHolder = null;
    private Thread mGameLoop = null;
    private AssetManager mAssetManager = null;

    private static final long FPS = 40;
    private static final long FRAME_TIME = 1000 / FPS;

    public GameSurfaceView(Context context) {
        super(context);
        mAssetManager = new AssetManager(getResources());
        mAssetManager.loadAssets(R.drawable.ic_launcher);

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public AssetManager getAssetManager() {
        return mAssetManager;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mGameLoop = new Thread(this);
        mGameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mGameLoop = null;
    }

    @Override
    public void run() {
        TaskManager taskManager = new TaskManager(this);
        Ball ball = new Ball();
        taskManager.register(ball);

        while (mGameLoop != null) {
            long startTime = System.currentTimeMillis();

            Canvas canvas = mHolder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            taskManager.setCanvas(canvas);
            taskManager.execute();

            mHolder.unlockCanvasAndPost(canvas);

            try {
                long waitTime = System.currentTimeMillis() - startTime;
                if (waitTime < FRAME_TIME) {
                    Thread.sleep(FRAME_TIME - waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
