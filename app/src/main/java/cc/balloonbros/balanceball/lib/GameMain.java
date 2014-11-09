package cc.balloonbros.balanceball.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.scene.SceneChanger;

abstract public class GameMain {
    private GameLoop mGameLoop = null;
    private Context mContext = null;
    private ViewGroup mContainer = null;
    private GameSurfaceView mView = null;
    private GameSurfaceView mBackView = null;
    private GameDisplay mGameDisplay = null;
    private AbstractScene mCurrentScene = null;
    private AbstractScene mReservedScene = null;
    private SceneChanger mSceneChanger;

    public Context getContext() { return mContext; }
    public ViewGroup getContainer() { return mContainer; }
    public GameSurfaceView getView() { return mView; }
    public GameSurfaceView getBackView() { return mBackView; }
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
        mGameDisplay = GameDisplay.getInstance();
        mGameDisplay.setContext(mContext);
        _.set(context.getResources());
    }

    /**
     * コンストラクタ。
     * ゲーム画面の幅と高さも同時に指定して
     * 各端末の解像度に合わせて自動的に伸縮する。
     * @param context ゲームを配置するActivity
     * @param width ゲーム画面の幅
     * @param height ゲーム画面の高さ
     */
    public GameMain(Context context, int width, int height) {
        this(context);
        mGameDisplay.setGameDisplaySize(width, height);
    }

    /**
     * ゲーム初期化処理。
     * 必要であれば継承先でオーバーライドする
     */
    public void onInitialize() {
        Activity activity = (Activity)mContext;
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

        GameActivity activity = (GameActivity) mContext;
        mContainer = (ViewGroup) activity.findViewById(R.id.container);
        mView      = (GameSurfaceView) activity.findViewById(R.id.main_surface);
        mBackView  = (GameSurfaceView) activity.findViewById(R.id.back_surface);
        mGameLoop  = new GameLoop(this);

        CurrentGame.set(this);

        if (fps > 0) {
            mGameLoop.changeFps(fps);
        }
        mCurrentScene = startScene;
        mCurrentScene.belongsTo(this);

        mGameDisplay.updateDisplaySize();
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
    public SceneChanger changeScene(AbstractScene scene) {
        mReservedScene = scene;
        mSceneChanger  = new SceneChanger(mCurrentScene, mReservedScene);
        return mSceneChanger;
    }

    /**
     * シーン切り替え中かどうかを確認する
     * @return シーン切り替え中であればtrue
     */
    public boolean whileChangingScene() {
        if (mSceneChanger == null) {
            return false;
        }

        boolean finished = mSceneChanger.finishedSceneChange();
        if (finished) {
            mSceneChanger = null;
        }
        return !finished;
    }

    public SceneChanger getSceneChanger() {
        return mSceneChanger;
    }

    /**
     * ゲームシーンの切り替え予定があるかどうかをチェックする
     * @return 切り替え予定があればtrue
     */
    boolean hasReservedScene() {
        return mReservedScene != null;
    }

    void updateCurrentScene() {
        mCurrentScene = mReservedScene;
        mCurrentScene.belongsTo(this);
        mReservedScene = null;
    }
}
