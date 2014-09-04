package cc.balloonbros.balanceball.lib;

import android.graphics.Canvas;
import java.util.LinkedList;

import cc.balloonbros.balanceball.lib.AbstractTask;
import cc.balloonbros.balanceball.lib.Drawable;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.Updateable;

/**
 * タスク管理クラス
 */
public class TaskManager {
    /**
     * タスク一覧
     */
    private LinkedList<AbstractTask> mTaskList = new LinkedList<AbstractTask>();

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
    private LinkedList<AbstractTask> mReservedRegisterTask = new LinkedList<AbstractTask>();

    /**
     * タスク削除予約リスト
     */
    private LinkedList<AbstractTask> mReservedRemoveTask = new LinkedList<AbstractTask>();

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
     * @see TaskManager#register
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
        for (AbstractTask task: mTaskList) {
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
     * 指定されたプライオリティを持つタスクを検索して取得する
     *
     * @param priority プライオリティ
     * @return 見つかったタスク。タスクが見つからなければnull
     */
    public AbstractTask find(int priority) {
        for (AbstractTask task: mTaskList) {
            if (task.getPriority() == priority) {
                return task;
            }
        }

        return null;
    }

    /**
     * 予約リストに入っているタスクの処理を確定させる
     */
    private void confirm() {
        // 登録予約タスクを登録する
        // 優先度を見て、優先度が高いタスクほどリストの最初に登録する
        AbstractTask registeredTask = mReservedRegisterTask.poll();
        while (registeredTask != null) {
            int index    = 0;
            int location = -1;

            // タスクリストの優先度と追加タスクの優先度を比較しながら挿入位置を探す
            for (AbstractTask task: mTaskList) {
                if (task.getPriority() > registeredTask.getPriority()) {
                    location = index;
                    break;
                }
                index++;
            }

            // 探した挿入位置にタスクを追加する
            if (location == -1) {
                mTaskList.add(registeredTask);
            } else {
                mTaskList.add(location, registeredTask);
            }
            registeredTask.onRegistered();
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
        for (AbstractTask task: mTaskList) {
            task.onEnterLoop();
        }
    }

    /**
     * ゲームループから抜けた際に全てのタスクに通知を送る
     */
    public void leaveLoop() {
        for (AbstractTask task: mTaskList) {
            task.onLeaveLoop();
        }
    }
}
