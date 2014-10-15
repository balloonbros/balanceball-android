package cc.balloonbros.balanceball.scene;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.task.extender.TimerPlugin;
import cc.balloonbros.balanceball.lib.task.extender.TouchPlugin;
import cc.balloonbros.balanceball.task.launch.Logo;

/**
 * 起動シーン
 */
public class LaunchScene extends AbstractScene {
    @Override
    protected void onInitialize() {
        loadBitmaps(R.drawable.launch_logo);
        registerTasks(new Logo().with(new TouchPlugin()).with(new TimerPlugin()));
    }
}
