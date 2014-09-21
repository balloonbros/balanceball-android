package cc.balloonbros.balanceball.lib.task.system;

import android.util.SparseArray;

public class TaskList {
    private BaseTask mFirst = null;
    private BaseTask mLast = null;
    private int mTaskCount = 0;
    private SparseArray<BaseTask> mCache = new SparseArray<BaseTask>();

    public BaseTask getFirst() { return mFirst; }
    public BaseTask getLast()  { return mLast; }
    public int      getCount() { return mTaskCount; }

    public void clear() {
        mCache.clear();
        mCache = null;

        BaseTask task = mFirst;
        while (task != null) {
            BaseTask nextTask = task.getNext();
            task.clearLink();
            task = nextTask;
        }
    }

    public BaseTask find(int priority) {
        if (mFirst.getPriority() == priority) {
            return mFirst;
        } else if (mLast.getPriority() == priority) {
            return mLast;
        } else {
            return mCache.get(priority);
        }
    }

    public void register(BaseTask task) {
        if (mFirst == null) {
            mFirst = mLast = task;
        } else {
            insert(task);
        }

        mTaskCount++;
        mCache.put(task.getPriority(), task);
    }

    public void remove(BaseTask removeTask) {
        if (mFirst == removeTask) {
            BaseTask nextFirst = mFirst.getNext();
            mFirst.clearLink();
            mFirst = nextFirst;
            nextFirst.setPrev(null);
        } else if (mLast == removeTask) {
            BaseTask nextLast = mLast.getPrev();
            mLast.clearLink();
            mLast = nextLast;
            nextLast.setNext(null);
        } else {
            removeTask.getPrev().setNext(removeTask.getNext());
            removeTask.getNext().setPrev(removeTask.getPrev());
            removeTask.clearLink();
        }

        mTaskCount--;
        mCache.remove(removeTask.getPriority());
    }

    private void insert(BaseTask task) {
        if (mFirst.getPriority() > task.getPriority()) {
            mFirst.setPrev(task);
            task.setNext(mFirst);
            mFirst = task;
        } else if (mLast.getPriority() <= task.getPriority()) {
            mLast.setNext(task);
            task.setPrev(mLast);
            mLast = task;
        } else {
            BaseTask base = mFirst;
            while (base != null) {
                if (base.getPriority() > task.getPriority()) {
                    base.getPrev().setNext(task);
                    task.setPrev(base.getPrev());
                    base.setPrev(task);
                    task.setNext(base);
                    break;
                }
                base = base.getNext();
            }
        }
    }
}
