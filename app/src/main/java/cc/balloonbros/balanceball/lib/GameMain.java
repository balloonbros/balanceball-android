package cc.balloonbros.balanceball.lib;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.view.WindowManager;

import cc.balloonbros.balanceball.lib.task.TaskManager;

abstract public class GameMain {
    /**
     * コンテキスト
     */
    private Context mContext = null;

    /**
     * ゲームを描画するサーフェイス
     */
    private GameSurfaceView mView = null;

    /**
     * ゲームループ
     */
    private GameLoop mGameLoop = null;

    /**
     *  ゲーム中のタスクを管理するタスクマネージャー
     */
    private TaskManager mTaskManager = null;

    /**
     * ゲーム中のアセット(画像/SE/音楽/etc)を管理するアセットマネージャー
     */
    private AssetManager mAssetManager = null;

    /**
     * ウィンドウマネージャー
     */
    private WindowManager mWindowManager = null;

    /**
     * センサーマネージャ
     */
    private SensorManager mSensorManager = null;

    /**
     * コンテキストを取得する
     *
     * @return コンテキスト
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * ゲームを描画しているサーフェイスビューを取得する
     *
     * @return サーフェイスビュー
     */
    public GameSurfaceView getView() {
        return mView;
    }

    /**
     * タスクマネージャーを取得する
     *
     * @return タスクマネージャー
     */
    public TaskManager getTaskManager() {
        return mTaskManager;
    }

    /**
     * アセットマネージャーを取得する
     *
     * @return アセットマネージャー
     */
    public AssetManager getAssetManager() {
        return mAssetManager;
    }

    /**
     * ウィンドウマネージャーを取得する
     *
     * @return ウィンドウマネージャー
     */
    public WindowManager getWindowManager() {
        return mWindowManager;
    }

    /**
     * センサーマネージャーを取得する
     *
     * @return センサーマネージャー
     */
    public SensorManager getSensorManager() {
        return mSensorManager;
    }

    /**
     * コンストラクタ
     *
     * @param context ゲームを配置するActivity
     */
    public GameMain(Context context) {
        mContext = context;
    }

    /**
     * ゲーム初期化処理。
     * 必要であれば継承先でオーバーライドする
     */
    public void onInitialize() { }

    /**
     * ゲームを開始する
     */
    public void start() {
        mTaskManager   = new TaskManager(this);
        mAssetManager  = new AssetManager(getContext().getResources());
        mView          = new GameSurfaceView(mContext);
        mWindowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        mSensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        mGameLoop      = new GameLoop(this);

        onInitialize();

        ((Activity)mContext).setContentView(mView);
    }

    /**
     * FPSを取得する
     *
     * @return FPS
     */
    public float getFps() {
        return mGameLoop.getRealFps();
    }

    /**
     * フレーム数を取得する
     *
     * @return フレーム数
     */
    public long getFrameCount() {
        return mGameLoop.getFrameCount();
    }
}
