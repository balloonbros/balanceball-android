package cc.balloonbros.balanceball;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ゲーム描画サーフェイス
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * ゲームループのスレッド
     */
    private Thread mGameLoopThread = null;

    /**
     * このサーフェイスが描画中のゲーム
     */
    private GameMain mGame = null;

    /**
     * コンストラクタ
     *
     * @param context このサーフェイスが属するコンテキスト
     */
    public GameSurfaceView(Context context) {
        super(context);

        getHolder().addCallback(this);
    }

    /**
     * このサーフェイスが描画する対象のゲームをセットする
     *
     * @param game ゲーム
     */
    public void setGame(GameMain game) {
        mGame = game;
    }

    /**
     * ゲームループのスレッドを取得する
     *
     * @return ゲームループのスレッド
     */
    public Thread getGameLoopThread() {
        return mGameLoopThread;
    }

    /**
     * サーフェイス生成時に実行
     *
     * @param surfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // ゲームループ用のスレッドを作って実行を開始する
        mGameLoopThread = new Thread(mGame.getGameLoop());
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
}
