package cc.balloonbros.balanceball.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

import cc.balloonbros.balanceball.lib.task.TaskManager;

/**
 * ゲームループを処理します。
 * ゲームループは別スレッドで処理されます。
 */
public class GameLoop implements Runnable, SurfaceHolder.Callback {
    private GameMain      mGame           = null;
    private Thread        mGameLoopThread = null;
    private SurfaceHolder mHolder         = null;

    /**
     * FPSと1フレームの秒数
     */
    private static final long DEFAULT_FPS = 60;
    private long mFps;
    private long mFrameTime;

    /**
     * フレームの実行にかかった時間
     */
    private long mLoopTime = 1;

    /**
     * 現在までに実行されたフレーム数
     */
    private long mFrameCount = 1;

    public float getRealFps() {
        return 1000 / mLoopTime;
    }
    public long getFps() {
        return mFps;
    }
    public long getFrameCount() {
        return mFrameCount;
    }

    /**
     * コンストラクタ
     *
     * @param game ゲーム
     */
    public GameLoop(GameMain game) {
        mGame   = game;
        mHolder = game.getView().getHolder();
        mHolder.addCallback(this);

        changeFps(DEFAULT_FPS);
    }

    /**
     * FPSを変更する
     *
     * @param fps FPS
     */
    public void changeFps(long fps) {
        mFps = fps;
        mFrameTime = 1000 / mFps;
    }

    /**
     * ゲームループ本体。
     */
    @Override
    public void run() {
        TaskManager taskManager = mGame.getTaskManager();
        taskManager.enterLoop();

        while (mGameLoopThread != null) {
            long startTime = System.currentTimeMillis();
            mFrameCount++;

            // ダブルバッファリング開始
            Canvas canvas = mHolder.lockCanvas();
            if (canvas == null) {
                break;
            }
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            // 全てのタスクを実行する
            taskManager.execute(canvas);

            // バッファ入れ替え。表側に描画する
            mHolder.unlockCanvasAndPost(canvas);

            // FPSを保つために処理が早く終わり過ぎたら1フレーム単位秒待つ
            long waitTime = System.currentTimeMillis() - startTime;
            if (waitTime < mFrameTime) {
                try {
                    Thread.sleep(mFrameTime - waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            mLoopTime = System.currentTimeMillis() - startTime;
        }

        taskManager.leaveLoop();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mGameLoopThread = new Thread(this);
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
