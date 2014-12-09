package cc.balloonbros.balanceball.lib;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import cc.balloonbros.balanceball.lib.display.GameDisplay;
import cc.balloonbros.balanceball.lib.graphic.Surface;

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
        //mHolder.addCallback(this);

        changeFps(DEFAULT_FPS);
    }

    /**
     * FPSを変更する
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
        Surface surface = new Surface();

        GameDisplay display = GameDisplay.getInstance();
        float scale = display.getScale();
        Rect scaledRect = display.getScaledRect();

        while (mGameLoopThread != null) {
            long startTime = System.currentTimeMillis();
            mFrameCount++;

            Canvas canvas = lockCanvasAndPrepare(scale, scaledRect);
            if (canvas == null) {
                return;
            }
            Surface flipSurface = draw(surface);
            mHolder.unlockCanvasAndPost(flipSurface.forwardBitmap(canvas));

            if (mGame.hasReservedScene()) {
                mGame.updateCurrentScene();
            }

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
    }

    /**
     * キャンバスの準備をする
     * @param scale 拡大率
     * @return キャンバス
     */
    private Canvas lockCanvasAndPrepare(float scale, Rect scaledRect) {
        Canvas canvas = mHolder.lockCanvas();
        if (canvas == null) {
            return null;
        }

        if (scale != 1) {
            canvas.translate(scaledRect.left, scaledRect.top);
            canvas.scale(scale, scale);
        }

        return canvas;
    }

    /**
     * タスクを全て実行して描画する
     * @param surface 描画先のサーフェイス
     * @return キャンバスに転送するサーフェイス
     */
    private Surface draw(Surface surface) {
        Surface flipSurface;

        if (mGame.whileChangingScene()) {
            flipSurface = mGame.getSceneChanger().execute(surface);
        } else {
            flipSurface = mGame.getCurrentScene().execute(surface);
        }

        return flipSurface;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mGameLoopThread = new Thread(this);
        mGameLoopThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        //mGame.getGameDisplay().updateDeviceDisplaySize();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mGameLoopThread = null;
    }
}
