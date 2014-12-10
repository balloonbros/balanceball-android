package cc.balloonbros.balanceball.lib;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;

import cc.balloonbros.balanceball.lib.display.GameDisplay;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.scene.SceneChanger;

abstract public class GameMain {
    private GameLoop mGameLoop;
    private Context mContext;
    private GameSurfaceView mView;
    private GameDisplay mGameDisplay;
    private AbstractScene mCurrentScene;
    private AbstractScene mReservedScene;
    private SceneChanger mSceneChanger;
    private GameStartListener mGameStartListener;

    public Context getContext() { return mContext; }
    public GameSurfaceView getView() { return mView; }
    public float getRealFps() { return mGameLoop.getRealFps(); }
    public long getFps() { return 60; }
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
    public void onInitialize() { }

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

        CurrentGame.set(this);
        mView = activity.buildSurface(this);
        mGameLoop = new GameLoop(this);

        if (fps > 0) {
            mGameLoop.changeFps(fps);
        }
        changeScene(startScene);
    }

    public void start(GameStartListener listener) {
        onInitialize();

        GameActivity activity = (GameActivity) mContext;

        CurrentGame.set(this);
        mView = activity.buildSurface(this);
        mGameStartListener = listener;
    }

    public GameStartListener getGameStartListener() {
        return mGameStartListener;
    }

    /**
     * ゲームのディスプレイのサイズを取得する
     * @return ディスプレイサイズ
     */
    public Point getDisplaySize() {
        return mGameDisplay.getDisplaySizePoint();
    }

    /**
     * ゲームのシーンを切り替える。
     * 実際には現在実行中のフレームが終了してから切り替わる
     * @param scene 切り替える先のシーン
     */
    public SceneChanger changeScene(AbstractScene scene) {
        mReservedScene = scene;
        mReservedScene.belongsTo(this);
        mSceneChanger = new SceneChanger(mCurrentScene, mReservedScene);
        return mSceneChanger;
    }

    /**
     * シーン切り替え中かどうかを確認する
     * @return シーン切り替え中であればtrue
     */
    public boolean whileChangingScene() {
        return mSceneChanger != null && !mSceneChanger.finishedSceneChange();
    }

    /**
     * Check if whether the game has the current scene.
     * @return Return true if the game has the current scene. Otherwise, false.
     */
    public boolean hasCurrentScene() {
        return mCurrentScene != null;
    }

    /**
     * Get the scene changer.
     * @return The scene changer
     */
    public SceneChanger getSceneChanger() {
        return mSceneChanger;
    }

    public boolean canChangeScene() {
        return mReservedScene != null && mSceneChanger != null && mSceneChanger.finishedSceneChange();
    }

    void updateCurrentScene() {
        mCurrentScene = mReservedScene;
        //mCurrentScene.belongsTo(this);
        mReservedScene = null;
        mSceneChanger = null;
    }
}
