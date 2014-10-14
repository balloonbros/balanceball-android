package cc.balloonbros.balanceball.scene;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.task.extender.OrientationPlugin;
import cc.balloonbros.balanceball.task.play.Ball;
import cc.balloonbros.balanceball.task.play.CenterCircle;
import cc.balloonbros.balanceball.task.play.CountdownTimer;
import cc.balloonbros.balanceball.task.play.DebugOutput;
import cc.balloonbros.balanceball.task.play.Judgement;
import cc.balloonbros.balanceball.task.play.Orientation;
import cc.balloonbros.balanceball.task.play.Score;
import cc.balloonbros.balanceball.task.play.WindOutBreaker;

public class PlayScene extends AbstractScene {
    @Override
    protected void onInitialize() {
        loadBitmaps(
                R.drawable.ball3,
                R.drawable.area3,
                R.drawable.wind3
        );
        loadFonts(_.s(R.string.open_sans_light));
        loadStyle(R.xml.play_scene_font_style);

        registerTasks(
            new Ball(),
            new Orientation().with(new OrientationPlugin()),
            new WindOutBreaker(),
            new DebugOutput(),
            new CenterCircle(),
            new CountdownTimer(),
            new Score(),
            new Judgement()
        );
    }
}
