package cc.balloonbros.balanceball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

/**
 * ゲームループを処理します。
 * ゲームループは別スレッドで処理されます。
 */
public class GameLoop implements Runnable {
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
     * ゲームループを処理するゲーム本体。
     */
    private GameMain mGame = null;

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
    }

    /**
     * ゲームループ本体。
     */
    @Override
    public void run() {
        while (mGame.isLoop()) {
            long startTime = System.currentTimeMillis();

            // ダブルバッファリング開始
            Canvas canvas = mHolder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            // 全てのタスクを実行する
            mGame.getTaskManager().execute(canvas);

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
    }

    /**
     * 実際のFPSを取得する
     *
     * @return FPS
     */
    public float getRealFps() {
        return 1000 / mLoopTime;
    }
}
