package cc.balloonbros.balanceball.lib.task.system;

import android.util.SparseArray;

/**
 * タスクリスト。
 *
 * ゲームループ中で効率的にタスクを優先度順に実行させるため
 * タスクは優先度順に連結リストで管理される。
 *
 * また検索の効率化のため
 *   キー: タスクの優先度
 *   値  : タスク自身
 * といったハッシュを内部的に管理しており、連結リストを辿ること無く
 * 優先度からタスクを検索できるようになっている。
 */
public class TaskList {
    /** 連結リストの一番最初のタスク */
    private BaseTask mFirst = null;

    /** 連結リストの一番最後のタスク */
    private BaseTask mLast = null;

    /** 連結リストに含まれるタスクの数 */
    private int mTaskCount = 0;

    /** 優先度をキーとしたタスクの一覧。検索用 */
    private SparseArray<BaseTask> mCache = new SparseArray<BaseTask>();

    public BaseTask getFirst() { return mFirst; }
    public BaseTask getLast()  { return mLast; }
    public int      getCount() { return mTaskCount; }

    /**
     * リストに含まれるタスクを全て削除する
     */
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

    /**
     * タスクの優先度からタスクを検索する
     * @param priority タスクの優先度
     * @return 引数で指定された優先度を持つタスク
     */
    public BaseTask find(int priority) {
        return mCache.get(priority);
    }

    /**
     * タスクをリストに登録する
     * @param registerTask 登録するタスク
     */
    public void register(BaseTask registerTask) {
        if (mFirst == null) {
            mFirst = mLast = registerTask;
        } else {
            insert(registerTask);
        }

        mTaskCount++;
        mCache.put(registerTask.getPriority(), registerTask);
    }

    /**
     * タスクをリストから削除する
     * @param removeTask 削除するタスク
     */
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

    /**
     * タスクの優先度を見て連結リストの適切な位置にタスクを挿入する
     * @param insertTask 連結リストに挿入するタスク
     */
    private void insert(BaseTask insertTask) {
        if (mFirst.getPriority() > insertTask.getPriority()) {
            mFirst.setPrev(insertTask);
            insertTask.setNext(mFirst);
            mFirst = insertTask;
        } else if (mLast.getPriority() <= insertTask.getPriority()) {
            mLast.setNext(insertTask);
            insertTask.setPrev(mLast);
            mLast = insertTask;
        } else {
            BaseTask base = mFirst;
            while (base != null) {
                if (base.getPriority() > insertTask.getPriority()) {
                    base.getPrev().setNext(insertTask);
                    insertTask.setPrev(base.getPrev());
                    base.setPrev(insertTask);
                    insertTask.setNext(base);
                    break;
                }
                base = base.getNext();
            }
        }
    }
}
