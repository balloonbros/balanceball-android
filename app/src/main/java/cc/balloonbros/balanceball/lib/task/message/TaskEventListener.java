package cc.balloonbros.balanceball.lib.task.message;

import java.util.EventListener;

import cc.balloonbros.balanceball.lib.task.AbstractTask;

public interface TaskEventListener extends EventListener {
    public void onMessage(AbstractTask sender, TaskMessage message);
}
