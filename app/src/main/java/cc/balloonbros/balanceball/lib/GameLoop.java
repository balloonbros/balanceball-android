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
    /**
     * FPS
     */
    private static final long FPS = 60;

    /**
     * 1フレームの秒数
     */
    private static final long FRAME_TIME = 1000 / FPS;

    /**
     * フレームの実行にかかった時間
     */
    private long mLoopTime = 1;

    /**
     * 現在までに実行されたフレーム数
     */
    private long mFrameCount = 1;

    /**
     * ゲームループを処理するゲーム本体。
     */
    private GameMain mGame = null;

    /**
     * ゲームループ用のスレッド
     */
    private Thread mGameLoopThread = null;

    /**
     * ゲーム用Viewへアクセスするためのオブジェクト。
     * このHolderを通じてダブルバッファリングしながら描画する。
     */
    private SurfaceHolder mHolder = null;

    /**
     * コンストラクタ。
     * ループを処理するゲームを受け取ります。
     *
     * @param game ゲーム
     */
    public GameLoop(GameMain game) {
        mGame   = game;
        mHolder = game.getView().getHolder();
        mHolder.addCallback(this);
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
            if (waitTime < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            mLoopTime = System.currentTimeMillis() - startTime;
        }

        taskManager.leaveLoop();
    }

    /**
     * サーフェイス生成時に実行
     *
     * @param surfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // ゲームループ用のスレッドを作って実行を開始する
        mGameLoopThread = new Thread(this);
        mGameLoopThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    /**
     * サーフェイス破棄時に実行
     *
     * @param surfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // スレッドを停止
        mGameLoopThread = null;
    }

    /**
     * 実際のFPSを取得する
     *
     * @return FPS
     */
    public float getRealFps() {
        return 1000 / mLoopTime;
    }

    /**
     * 現在のフレーム数を取得する
     *
     * @return フレーム数
     */
    public long getFrameCount() {
        return mFrameCount;
    }
}
