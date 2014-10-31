package cc.balloonbros.balanceball.lib.task.extender;

import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.TaskPlugin;

public class TouchPlugin extends TaskPlugin {
    @Override
    protected void onRegister() {
        AbstractTask task = getTask();
        if (task instanceof Touchable) {
            task.getGame().getView().addTouchable((Touchable) task);
        }
    }

    @Override
    protected void onKilled() {
        AbstractTask task = getTask();
        if (task instanceof Touchable) {
            task.getGame().getView().removeTouchable((Touchable) task);
        }
    }

    @Override
    protected void onRemoved() {
        onKilled();
    }
}
