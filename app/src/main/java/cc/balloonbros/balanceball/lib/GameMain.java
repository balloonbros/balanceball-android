package cc.balloonbros.balanceball.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Window;
import android.view.WindowManager;

import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.task.TaskManager;

abstract public class GameMain {
    private GameLoop mGameLoop = null;
    private Context mContext = null;
    private GameSurfaceView mView = null;
    private GameDisplay mGameDisplay = null;
    private AbstractScene mCurrentScene = null;

    public Context getContext() { return mContext; }
    public GameSurfaceView getView() { return mView; }
    public float getRealFps() { return mGameLoop.getRealFps(); }
    public long getFps() { return mGameLoop.getFps(); }
    public long getFrameCount() { return mGameLoop.getFrameCount(); }
    public Resources getResources() { return mContext.getResources(); }
    public GameDisplay getGameDisplay() { return mGameDisplay; }
    public GameLoop getGameLoop() { return mGameLoop; }

    /**
     * コンストラクタ
     * @param context ゲームを配置するActivity
     */
    public GameMain(Context context) {
        mContext     = context;
        mGameDisplay = new GameDisplay(this);
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
        changeScene(startScene);

        if (fps > 0) {
            mGameLoop.changeFps(fps);
        }
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
     * ゲームのシーンを切り替える
     * @param scene 切り替える先のシーン
     */
    public void changeScene(AbstractScene scene) {
        if (mCurrentScene != null) {
            mCurrentScene.dispose();
        }
        mCurrentScene = scene;
        mCurrentScene.onInitialize();
    }
    public AbstractScene getCurrentScene() { return mCurrentScene; }
}
