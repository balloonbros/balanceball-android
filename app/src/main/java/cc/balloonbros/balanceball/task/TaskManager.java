package cc.balloonbros.balanceball.task;

import android.graphics.Canvas;
import java.util.LinkedList;

import cc.balloonbros.balanceball.GameMain;

/**
 * タスク管理クラス
 */
public class TaskManager {
    /**
     * タスク一覧
     */
    private LinkedList<TaskBase> mTaskList = new LinkedList<TaskBase>();

    /**
     * タスクを管理する対象のゲーム
     */
    private GameMain mGame = null;

    /**
     * タスクループ中かどうかのフラグ。ループ中であればtrue
     */
    private boolean mWhileExecute = false;

    /**
     * タスク登録予約リスト
     */
    private LinkedList<TaskBase> mReservedRegisterTask = new LinkedList<TaskBase>();

    /**
     * タスク削除予約リスト
     */
    private LinkedList<TaskBase> mReservedRemoveTask = new LinkedList<TaskBase>();

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
    public void reserve(TaskBase... tasks) {
        // 受け取ったタスクはまず予約リストに登録する
        for (TaskBase task: tasks) {
            task.setGame(mGame);
            mReservedRegisterTask.offer(task);
        }

        // タスクループ外であればタスクを登録する
        if (!mWhileExecute) {
            confirm();
        }
    }

    /**
     * タスクリストからタスクを削除する
     *
     * @param removeTask 削除するタスク
     */
    public void remove(TaskBase removeTask) {
        mReservedRemoveTask.offer(removeTask);
    }

    /**
     * 現在のタスク数を取得する
     *
     * @return 現在のタスク数
     */
    public int getTaskCount() {
        return mTaskList.size();
    }

    /**
     * タスクループを実行する
     *
     * @param canvas 描画対象のキャンバス
     */
    public void execute(Canvas canvas) {
        mWhileExecute = true;

        // タスクリストに登録されているタスクの更新処理と描画処理を全て呼び出してフレームを進める
        for (TaskBase task: mTaskList) {
            if (task instanceof Updateable) {
                ((Updateable)task).onUpdate();
            }

            if (task instanceof Drawable) {
                ((Drawable)task).onDraw(canvas);
            }
        }

        mWhileExecute = false;

        // タスクループが終わったので予約されていたタスクを登録する
        confirm();
    }

    /**
     * 予約リストに入っているタスクの処理を確定させる
     */
    private void confirm() {
        TaskBase task;

        // 登録予約タスクを登録する
        task = mReservedRegisterTask.poll();
        while (task != null) {
            mTaskList.add(task);
            task.onRegistered();
            task = mReservedRegisterTask.poll();
        }

        // 削除予約タスクを削除する
        task = mReservedRemoveTask.poll();
        while (task != null) {
            mTaskList.remove(task);
            task = mReservedRemoveTask.poll();
        }
    }
}
