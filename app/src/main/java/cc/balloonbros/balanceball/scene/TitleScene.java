package cc.balloonbros.balanceball.scene;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.task.extender.TimerPlugin;
import cc.balloonbros.balanceball.lib.task.extender.TouchPlugin;
import cc.balloonbros.balanceball.task.Background;
import cc.balloonbros.balanceball.task.title.FadeIn;
import cc.balloonbros.balanceball.task.title.SampleCharacter;
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
        loadStyle(R.xml.title_scene_font_style);
        loadBitmaps(
            R.drawable.character,
            R.drawable.ball3
        );

        getTaskManager().register(
            new Background(),
            new Title(),
            new Start().with(new TouchPlugin()).with(new TimerPlugin()),
            new FadeIn().with(new TimerPlugin())
        );
    }
}
