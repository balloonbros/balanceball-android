package cc.balloonbros.balanceball.scene;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.task.play.Ball;
import cc.balloonbros.balanceball.task.play.CenterCircle;
import cc.balloonbros.balanceball.task.play.DebugOutput;
import cc.balloonbros.balanceball.task.play.Orientation;
import cc.balloonbros.balanceball.task.play.WindOutBreaker;

public class PlayScene extends AbstractScene {
    public PlayScene(GameMain game) {
        super(game);
    }

    @Override
    public void onInitialize() {
        loadAssets(R.drawable.ball3, R.drawable.area3, R.drawable.wind3);
        Ball initialTask1 = new Ball();
        Orientation initialTask2 = new Orientation();
        WindOutBreaker initialTask3 = new WindOutBreaker();
        DebugOutput initialTask4 = new DebugOutput();
        CenterCircle initialTask5 = new CenterCircle();

        initialTask1.setPriority(_.i(R.integer.priority_ball));
        initialTask3.setPriority(_.i(R.integer.priority_wind_out_breaker));
        initialTask4.setPriority(_.i(R.integer.priority_debug));
        initialTask5.setPriority(_.i(R.integer.priority_center_circle));

        getTaskManager().register(initialTask1, initialTask2, initialTask3, initialTask4, initialTask5);
    }
}
