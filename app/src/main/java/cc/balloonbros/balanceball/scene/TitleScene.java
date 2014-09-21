package cc.balloonbros.balanceball.scene;

import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.task.title.Title;

/**
 * タイトルシーン
 */
public class TitleScene extends AbstractScene {
    public TitleScene(GameMain game) {
        super(game);
    }

    @Override
    public void onInitialize() {
        loadFonts("opensans-bold.ttf", "opensans-light.ttf");
        getTaskManager().register(new Title());
    }
}
