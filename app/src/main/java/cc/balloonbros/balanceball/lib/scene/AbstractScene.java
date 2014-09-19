package cc.balloonbros.balanceball.lib.scene;

import android.graphics.Point;

import cc.balloonbros.balanceball.lib.AssetManager;
import cc.balloonbros.balanceball.lib.GameLoop;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.task.TaskManager;

public class AbstractScene {
    private GameMain mGame;
    private AssetManager mAssetManager;
    private TaskManager mTaskManager;

    public TaskManager getTaskManager() { return mTaskManager; }
    public AssetManager getAssetManager() { return mAssetManager; }
    public GameMain getGame() { return mGame; }
    public GameLoop getGameLoop() { return mGame.getGameLoop(); }

    public AbstractScene(GameMain game) {
        mGame = game;
        mAssetManager = new AssetManager(game.getContext().getResources());
        mTaskManager  = new TaskManager(this);
    }

    public void onInitialize() { }

    public void changeFps(long fps) {
        mGame.getGameLoop().changeFps(fps);
    }
    public void loadAssets(int... assetId) {
        mAssetManager.loadAssets(assetId);
    }

    public void dispose() {
        mAssetManager.dispose();
    }
}
