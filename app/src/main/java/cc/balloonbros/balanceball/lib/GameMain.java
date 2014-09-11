package cc.balloonbros.balanceball.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

import cc.balloonbros.balanceball.lib.task.TaskManager;

abstract public class GameMain {
    private GameLoop mGameLoop = null;

    private Context         mContext       = null;
    private GameSurfaceView mView          = null;
    private TaskManager mTaskManager   = null;
    private AssetManager    mAssetManager  = null;
    private WindowManager   mWindowManager = null;

    public Context getContext() {
        return mContext;
    }
    public GameSurfaceView getView() {
        return mView;
    }
    public TaskManager getTaskManager() {
        return mTaskManager;
    }
    public AssetManager getAssetManager() {
        return mAssetManager;
    }
    public WindowManager getWindowManager() {
        return mWindowManager;
    }

    /**
     * コンストラクタ
     * @param context ゲームを配置するActivity
     */
    public GameMain(Context context) {
        mContext = context;
    }

    public float getRealFps() {
        return mGameLoop.getRealFps();
    }
    public long getFps() {
        return mGameLoop.getFps();
    }
    public long getFrameCount() {
        return mGameLoop.getFrameCount();
    }
    public Resources getResources() {
        return mContext.getResources();
    }

    /**
     * ゲーム初期化処理。
     * 必要であれば継承先でオーバーライドする
     */
    public void onInitialize() {
        mTaskManager   = new TaskManager(this);
        mAssetManager  = new AssetManager(getContext().getResources());
        mView          = new GameSurfaceView(mContext);
        mWindowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        mGameLoop      = new GameLoop(this);
    }

    /**
     * ゲームを開始する
     */
    public void start() {
        start(0);
    }

    /**
     * FPSを指定してゲームを開始する
     * @param fps FPS。0以下の場合は無視される
     */
    public void start(long fps) {
        onInitialize();

        if (fps > 0) {
            mGameLoop.changeFps(fps);
        }
        ((Activity)mContext).setContentView(mView);
    }
}
