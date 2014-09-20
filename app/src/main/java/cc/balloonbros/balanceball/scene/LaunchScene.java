package cc.balloonbros.balanceball.scene;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.task.launch.Logo;

/**
 * 起動シーン
 */
public class LaunchScene extends AbstractScene {
    /**
     * コンストラクタ
     * @param game ゲーム
     */
    public LaunchScene(GameMain game) {
        super(game);
    }

    @Override
    public void onInitialize() {
        loadAssets(R.drawable.launch_logo);

        // 起動シーンはロゴを表示するだけなのでFPSは極限まで低くてよい
        changeFps(1);
        getTaskManager().register(new Logo());
    }
}
