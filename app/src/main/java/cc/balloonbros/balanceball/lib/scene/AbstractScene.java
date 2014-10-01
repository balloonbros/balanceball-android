package cc.balloonbros.balanceball.lib.scene;

import android.graphics.Bitmap;
import android.graphics.Typeface;

import cc.balloonbros.balanceball.lib.AssetManager;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.ResourceBase;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.TaskManager;

/**
 * シーンの基底クラス。
 */
public class AbstractScene extends ResourceBase {
    private GameMain mGame;
    private AssetManager mAssetManager;
    private TaskManager mTaskManager;

    public TaskManager getTaskManager() { return mTaskManager; }
    public AssetManager getAssetManager() { return mAssetManager; }
    public GameMain getGame() { return mGame; }

    /**
     * コンストラクタ
     */
    public AbstractScene() {
        mTaskManager = new TaskManager(this);
    }

    /**
     * シーンが属するゲームをセットする
     * @param game シーンが属するゲーム
     */
    public void belongsTo(GameMain game) {
        mAssetManager = new AssetManager(game.getContext().getResources());
        mGame = game;
        onInitialize();
    }

    /**
     * FPSを変更する
     * @param fps 変更後のFPS
     */
    public void changeFps(long fps) { mGame.getGameLoop().changeFps(fps); }

    /**
     * タスクを新しく登録する
     * @param tasks 登録するタスク。複数指定可能
     */
    public void registerTasks(AbstractTask... tasks) { getTaskManager().register(tasks); }

    /**
     * 画像を読み込む
     * @param assetId 素材のID
     */
    public void loadBitmaps(int... assetId) { mAssetManager.loadBitmaps(assetId); }

    /**
     * フォントを読み込む
     * @param fonts フォント名。複数指定可能
     */
    public void loadFonts(String... fonts) { mAssetManager.loadFonts(fonts); }

    /**
     * ロードされたリソースから画像を取得する
     * @param assetId 画像ID
     * @return 画像
     */
    public Bitmap getImage(int assetId) { return getAssetManager().getImage(assetId); }

    /**
     * フォントを取得する
     * @param font フォント名
     * @return フォント
     */
    public Typeface getFont(String font) { return getAssetManager().getFont(font); }

    /**
     * シーンを破棄する
     */
    public void dispose() {
        mTaskManager.dispose();
        mAssetManager.dispose();
    }

    /* ==============================================
     *           オーバーライド専用メソッド
     * ============================================== */

    /**
     * シーンが切り替わる際に最初に実行される
     */
    protected void onInitialize() { }
}
