package cc.balloonbros.balanceball.task;

import android.graphics.Canvas;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import cc.balloonbros.balanceball.GameMain;

public class TaskManager {
    private LinkedList<TaskBase> mTaskList = new LinkedList<TaskBase>();
    private GameMain mGame = null;
    private boolean mWhileExecute = false;
    private LinkedList<TaskBase> mReservedTask = new LinkedList<TaskBase>();

    public TaskManager(GameMain game) {
        mGame = game;
    }

    public void register(TaskBase... tasks) {
        for (int i = 0; i < tasks.length; i++) {
            TaskBase task = tasks[i];

            task.setGame(mGame);
            task.onRegistered();
            mReservedTask.offer(task);
        }

        confirmRegistration(mReservedTask);
    }

    public void execute(Canvas canvas) {
        Iterator<TaskBase> it = mTaskList.iterator();

        while (it.hasNext()) {
            mWhileExecute = true;
            it.next().execute(canvas);
        }

        mWhileExecute = false;
        confirmRegistration(mReservedTask);
    }

    private void confirmRegistration(LinkedList<TaskBase> reservedTask) {
        if (!mWhileExecute) {
            TaskBase task = reservedTask.poll();
            while (task != null) {
                mTaskList.add(task);
                task = reservedTask.poll();
            }
        }
    }
}
