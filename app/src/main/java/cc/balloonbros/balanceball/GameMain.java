package cc.balloonbros.balanceball;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

import cc.balloonbros.balanceball.task.TaskManager;

abstract public class GameMain {
    private Context         mContext      = null;
    private GameSurfaceView mView         = null;
    private TaskManager     mTaskManager  = null;
    private AssetManager    mAssetManager = null;
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
     *
     * @param context ゲームを配置するActivity
     */
    public GameMain(Context context) {
        mContext = context;
    }

    public void onBeforeStart() { }

    /**
     * ゲームを開始します。
     */
    public void start() {
        mTaskManager   = new TaskManager(this);
        mAssetManager  = new AssetManager(getContext().getResources());
        mView          = new GameSurfaceView(mContext, this);
        mWindowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);

        onBeforeStart();

        ((Activity)mContext).setContentView(mView);
    }

    /**
     * ゲームループが続いているかどうかを調べます。
     *
     * @return ループ中であればtrue。
     */
    public boolean isLoop() {
        return mView.getGameLoopThread() != null;
    }
}
