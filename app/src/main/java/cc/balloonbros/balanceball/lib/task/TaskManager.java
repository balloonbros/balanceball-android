package cc.balloonbros.balanceball.lib.task;

import android.graphics.Canvas;
import android.util.SparseArray;

import java.util.LinkedList;

import cc.balloonbros.balanceball.lib.GameMain;

/**
 * タスク管理クラス
 */
public class TaskManager {
    private GameMain mGame = null;

    /**
     * タスク一覧
     */
    private TaskList mTaskList = new TaskList();
    private LinkedList<AbstractTask> mReservedRegisterTask = new LinkedList<AbstractTask>();
    private LinkedList<AbstractTask> mReservedRemoveTask = new LinkedList<AbstractTask>();
    private SparseArray<AbstractTask> mTaskCache = new SparseArray<AbstractTask>();

    /**
     * タスクループ中かどうかのフラグ。ループ中であればtrue
     */
    private boolean mWhileExecute = false;

    /**
     * コンストラクタ
     *
     * @param game タスクを管理する対象のゲーム
     */
    public TaskManager(GameMain game) {
        mGame = game;
    }

    /**
     * タスクを予約リストに追加する。
     * 予約リストに追加されたタスクはタスクループが実行されていない時にタスクリストに追加される。
     *
     * タスクループ中にタスクが追加されると例外が発生するため
     *   1. 予約リストにタスクを登録する
     *   2. タスクループの実行が完了するまで待つ
     *   3. 完了したらこの予約リストからタスクを取り出してタスクリストに登録する
     * という手順でタスクを登録する。
     */
    public void register(AbstractTask... tasks) {
        // 受け取ったタスクはまず予約リストに登録する
        for (AbstractTask task: tasks) {
            task.setGame(mGame);
            mReservedRegisterTask.offer(task);
        }

        // タスクループ外であればタスク登録を実行する
        if (!mWhileExecute) {
            confirm();
        }
    }

    /**
     * タスクリストからタスクを削除する。
     * registerと同じ理由でまずは予約リストに追加される。
     *
     * @see cc.balloonbros.balanceball.lib.task.TaskManager#register
     * @param removeTask 削除するタスク
     */
    public void remove(AbstractTask removeTask) {
        mReservedRemoveTask.offer(removeTask);

        // タスクループ外であればタスク削除を実行する
        if (!mWhileExecute) {
            confirm();
        }
    }

    /**
     * 現在のタスク数を取得する
     *
     * @return 現在のタスク数
     */
    public int getTaskCount() {
        return mTaskList.getCount();
    }

    /**
     * タスクループを実行する
     *
     * @param canvas 描画対象のキャンバス
     */
    public void execute(Canvas canvas) {
        mWhileExecute = true;

        // タスクリストに登録されているタスクの更新処理と描画処理を全て呼び出してフレームを進める
        AbstractTask task = (AbstractTask)mTaskList.getFirst();
        while (task != null) {
            task.execute(canvas);
            task = (AbstractTask)task.getNext();
        }

        mWhileExecute = false;

        // タスクループが終わったので予約されていたタスクを登録する
        confirm();
    }

    /**
     * 指定されたプライオリティを持つタスクを検索して取得する
     *
     * @param priority プライオリティ
     * @return 見つかったタスク。タスクが見つからなければnull
     */
    public AbstractTask find(int priority) {
        return (AbstractTask)mTaskList.find(priority);
    }

    /**
     * 予約リストに入っているタスクの処理を確定させる
     */
    private void confirm() {
        // 登録予約タスクを登録する
        // 優先度を見て、優先度が高いタスクほどリストの最初に登録する
        AbstractTask registeredTask = mReservedRegisterTask.poll();
        while (registeredTask != null) {
            registeredTask.onRegistered();
            mTaskList.register(registeredTask);

            // 次のタスクへ
            registeredTask = mReservedRegisterTask.poll();
        }

        // 削除予約タスクを削除する
        AbstractTask removedTask = mReservedRemoveTask.poll();
        while (removedTask != null) {
            mTaskList.remove(removedTask);
            removedTask = mReservedRemoveTask.poll();
        }
    }

    /**
     * ゲームループに入るときに全てのタスクに通知を送る
     */
    public void enterLoop() {
        AbstractTask task = (AbstractTask)mTaskList.getFirst();
        while (task != null) {
            task.onEnterLoop();
            task = (AbstractTask)task.getNext();
        }
    }

    /**
     * ゲームループから抜けた際に全てのタスクに通知を送る
     */
    public void leaveLoop() {
        AbstractTask task = (AbstractTask)mTaskList.getFirst();
        while (task != null) {
            task.onLeaveLoop();
            task = (AbstractTask)task.getNext();
        }
    }
}
