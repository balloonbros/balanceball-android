package cc.balloonbros.balanceball.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.WindowManager;

import cc.balloonbros.balanceball.lib.task.TaskManager;

abstract public class GameMain {
    private GameLoop mGameLoop = null;

    private Context         mContext       = null;
    private GameSurfaceView mView          = null;
    private TaskManager     mTaskManager   = null;
    private AssetManager    mAssetManager  = null;
    private GameDisplay     mGameDisplay   = null;

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

    /**
     * コンストラクタ
     * @param context ゲームを配置するActivity
     */
    public GameMain(Context context) {
        mContext = context;
        mGameDisplay = new GameDisplay(this);
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
    public GameDisplay getGameDisplay() {
        return mGameDisplay;
    }

    /**
     * ゲーム初期化処理。
     * 必要であれば継承先でオーバーライドする
     */
    public void onInitialize() {
        mTaskManager  = new TaskManager(this);
        mAssetManager = new AssetManager(getContext().getResources());
        mView         = new GameSurfaceView(mContext);
        mGameLoop     = new GameLoop(this);

        // 画面の明るさをキープしたまま暗くならないようにする
        ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

    /**
     * ゲームのディスプレイのサイズを取得する
     * @return ディスプレイサイズ
     */
    public Point getDisplaySize() {
        return mGameDisplay.getDisplaySize();
    }
}
