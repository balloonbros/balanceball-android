package cc.balloonbros.balanceball.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Window;
import android.view.WindowManager;

import cc.balloonbros.balanceball.lib.scene.AbstractScene;

abstract public class GameMain {
    private GameLoop mGameLoop = null;
    private Context mContext = null;
    private GameSurfaceView mView = null;
    private GameDisplay mGameDisplay = null;
    private AbstractScene mCurrentScene = null;
    private AbstractScene mReservedScene = null;

    public Context getContext() { return mContext; }
    public GameSurfaceView getView() { return mView; }
    public float getRealFps() { return mGameLoop.getRealFps(); }
    public long getFps() { return mGameLoop.getFps(); }
    public long getFrameCount() { return mGameLoop.getFrameCount(); }
    public Resources getResources() { return mContext.getResources(); }
    public GameDisplay getGameDisplay() { return mGameDisplay; }
    public GameLoop getGameLoop() { return mGameLoop; }
    public AbstractScene getCurrentScene() { return mCurrentScene; }

    /**
     * コンストラクタ
     * @param context ゲームを配置するActivity
     */
    public GameMain(Context context) {
        mContext     = context;
        mGameDisplay = new GameDisplay(this);
        _.set(context.getResources());
    }

    /**
     * ゲーム初期化処理。
     * 必要であれば継承先でオーバーライドする
     */
    public void onInitialize() {
        mView     = new GameSurfaceView(mContext);
        mGameLoop = new GameLoop(this);

        Activity activity = (Activity)mContext;

        // 画面の明るさをキープしたまま暗くならないようにする
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // アプリのタイトルを非表示にする
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    /**
     * ゲームを開始する
     */
    public void start(AbstractScene startScene) { start(startScene, 0); }

    /**
     * FPSを指定してゲームを開始する
     * @param startScene 最初のシーン
     * @param fps FPS。0以下の場合は無視される
     */
    public void start(AbstractScene startScene, long fps) {
        onInitialize();
        if (fps > 0) {
            mGameLoop.changeFps(fps);
        }
        changeScene(startScene);
        executeChangingScene();

        ((Activity)mContext).setContentView(mView);
    }

    /**
     * ゲームのディスプレイのサイズを取得する
     * @return ディスプレイサイズ
     */
    public Point getDisplaySize() {
        return mGameDisplay.getDisplaySize();
    }

    /**
     * ゲームのシーンを切り替える。
     * 実際には現在実行中のフレームが終了してから切り替わる
     * @param scene 切り替える先のシーン
     */
    public void changeScene(AbstractScene scene) { mReservedScene = scene; }

    /**
     * ゲームシーンの切り替え予定があるかどうかをチェックする
     * @return 切り替え予定があればtrue
     */
    boolean hasReservedScene() { return mReservedScene != null; }

    /**
     * ゲームのシーン切り替えを実行する
     */
    void executeChangingScene() {
        if (mCurrentScene != null) {
            mCurrentScene.dispose();
        }
        mCurrentScene = mReservedScene;
        mCurrentScene.belongsTo(this);
        mCurrentScene.onInitialize();
        mReservedScene = null;
    }
}
