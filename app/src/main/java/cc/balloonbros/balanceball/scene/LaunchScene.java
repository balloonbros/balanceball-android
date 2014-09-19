package cc.balloonbros.balanceball.scene;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.task.Launch;

public class LaunchScene extends AbstractScene {
    public LaunchScene(GameMain game) {
        super(game);
    }

    @Override
    public void onInitialize() {
        loadAssets(R.drawable.launch_logo);
    }
}
