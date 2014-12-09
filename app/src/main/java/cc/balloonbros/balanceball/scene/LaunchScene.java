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
        loadTexture(R.drawable.launch_logo8);
        loadTexture(R.drawable.ic_launcher_nodpi);
        loadTexture(R.drawable.shape1);
        registerTasks(new Logo().with(new TouchPlugin()).with(new TimerPlugin()));
        //registerTasks(new Logo().with(new TimerPlugin()));
    }
}
