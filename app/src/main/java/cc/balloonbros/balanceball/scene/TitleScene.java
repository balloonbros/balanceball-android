package cc.balloonbros.balanceball.scene;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.task.title.FadeIn;
import cc.balloonbros.balanceball.task.title.Start;
import cc.balloonbros.balanceball.task.title.Title;

/**
 * タイトルシーン
 */
public class TitleScene extends AbstractScene {
    @Override
    protected void onInitialize() {
        loadFonts(
            _s(R.string.open_sans_bold),
            _s(R.string.open_sans_light)
        );
        loadStyle(R.xml.font_style);

        getTaskManager().register(new Title(), new Start(), new FadeIn());
    }
}
