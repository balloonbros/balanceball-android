package cc.balloonbros.balanceball.lib;

import android.graphics.Bitmap;
import android.graphics.Point;

import cc.balloonbros.balanceball.GameMain;
import cc.balloonbros.balanceball.TaskManager;
import cc.balloonbros.balanceball.TaskPriority;

/**
 * ゲーム内のタスクの基底クラス。
 * タスクはすべてこのクラスを継承する。
 */
abstract public class TaskBase {
    /**
     * このタスクが属しているゲーム
     */
    private GameMain mGame = null;

    /**
     * このタスクが属しているタスクマネージャー
     */
    private TaskManager mTaskManager = null;

    /**
     * タスクのプライオリティ
     */
    private TaskPriority mPriority = TaskPriority.MINIMUM;

    /**
     * プライオリティを取得する
     *
     * @return プライオリティ
     */
    public int getPriority() {
        return mPriority.getPriority();
    }

    /**
     * プライオリティをセットする
     *
     * @param priority プライオリティ
     */
    public void setPriority(TaskPriority priority) {
        mPriority = priority;
    }

    /**
     * タスクがタスクマネージャーに登録された時に呼ばれる
     */
    public void onRegistered() {
    }

    /**
     * このタスクが属すゲームをセットする
     *
     * @param game ゲーム
     */
    public void setGame(GameMain game) {
        mGame = game;
        mTaskManager = mGame.getTaskManager();
    }

    /**
     * ロードされたリソースから画像を取得する
     *
     * @param assetId 画像ID
     * @return 画像
     */
    protected Bitmap getImage(int assetId) {
        return mGame.getAssetManager().getImage(assetId);
    }

    /**
     * ゲームを取得する
     *
     * @return ゲーム
     */
    protected GameMain getGame() {
        return mGame;
    }

    /**
     * このタスクが属するタスクマネージャーを取得する
     *
     * @return タスクマネージャー
     */
    protected TaskManager getTaskManager() {
        return mTaskManager;
    }

    /**
     * デバイスのディスプレイサイズを取得する
     *
     * @return ディスプレイサイズ
     */
    protected Point getDisplaySize() {
        Point displaySize = new Point();
        mGame.getWindowManager().getDefaultDisplay().getSize(displaySize);
        return displaySize;
    }

    /**
     * 自分自身をタスクリストから削除する
     */
    protected void kill() {
        mGame.getTaskManager().remove(this);
    }

    /**
     * このタスクから他タスクにメッセージを送信する
     *
     * @param targetTask メッセージを送信する先のタスク
     * @param message メッセージオブジェクト。必要に応じて受取先でキャストする
     */
    protected void sendMessage(TaskEventListener targetTask, Object message) {
        targetTask.onMessage(message);
    }

    /**
     * 指定されたプライオリティのタスクを探して取得する
     *
     * @param priority タスクのプライオリティ
     * @return 見つかったタスクを返す。タスクが見つからなければnull
     */
    protected TaskBase find(TaskPriority priority) {
        return mTaskManager.find(priority.getPriority());
    }
}
