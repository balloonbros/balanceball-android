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
        loadBitmaps(R.drawable.launch_logo);
        getTaskManager().register(new Logo());
    }
}
