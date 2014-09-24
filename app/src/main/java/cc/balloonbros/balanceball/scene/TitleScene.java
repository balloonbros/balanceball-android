package cc.balloonbros.balanceball.scene;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.task.title.FadeIn;
import cc.balloonbros.balanceball.task.title.Title;

/**
 * タイトルシーン
 */
public class TitleScene extends AbstractScene {
    @Override
    protected void onInitialize() {
        loadFonts(
            _.s(R.string.open_sans_bold),
            _.s(R.string.open_sans_light)
        );
        getTaskManager().register(new Title(), new FadeIn());
    }
}
