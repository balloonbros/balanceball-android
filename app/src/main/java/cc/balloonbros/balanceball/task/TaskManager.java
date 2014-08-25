package cc.balloonbros.balanceball.task;

import android.content.Context;
import android.graphics.Canvas;
import java.util.Iterator;
import java.util.LinkedList;

import cc.balloonbros.balanceball.GameSurfaceView;

public class TaskManager {
    private LinkedList<TaskBase> taskList = new LinkedList<TaskBase>();
    private Canvas mCanvas = null;
    private GameSurfaceView mView = null;

    public TaskManager(GameSurfaceView view) {
        mView = view;
    }

    public void register(TaskBase task) {
        task.setView(mView);
        task.onRegistered();
        taskList.add(task);
    }

    public void execute() {
        Iterator<TaskBase> it = taskList.iterator();

        while (it.hasNext()) {
            TaskBase task = it.next();

            task.execute(mCanvas);
        }
    }

    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
    }
}
