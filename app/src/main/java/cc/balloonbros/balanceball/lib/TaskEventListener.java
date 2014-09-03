package cc.balloonbros.balanceball.lib;

import java.util.EventListener;

public interface TaskEventListener extends EventListener {
    public void onMessage(AbstractTask sender, TaskMessage message);
}
