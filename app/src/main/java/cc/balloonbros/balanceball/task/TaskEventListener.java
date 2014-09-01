package cc.balloonbros.balanceball.task;

import java.util.EventListener;

public interface TaskEventListener extends EventListener {
    public void onMessage(Object message);
}
