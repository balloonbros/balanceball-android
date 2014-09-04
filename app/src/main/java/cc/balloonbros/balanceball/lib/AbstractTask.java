package cc.balloonbros.balanceball.lib;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

import cc.balloonbros.balanceball.lib.TaskManager;
import cc.balloonbros.balanceball.TaskPriority;

/**
 * ゲーム内のタスクの基底クラス。
 * タスクはすべてこのクラスを継承する。
 */
abstract public class AbstractTask {
    /**
     * このタスクが属しているゲーム
     */
    private GameMain mGame = null;

    /**
     * このタスクが属しているタスクマネージャー
     */
    private TaskManager mTaskManager = null;

    /**
     * このタスクの子タスク
     */
    private ArrayList<AbstractTask> mChildren = new ArrayList<AbstractTask>();

    /**
     * registerChildメソッドによって自分自身を追加してくれた親タスク
     */
    private AbstractTask mParent = null;

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
     * 親タスクを取得する。
     *
     * @return 親タスク。親がいない場合はnull
     */
    public AbstractTask getParent() {
        return mParent;
    }

    /**
     * 親タスクをセットする
     *
     * @param parentTask 親タスク
     */
    private void setParent(AbstractTask parentTask) {
        mParent = parentTask;
    }

    /**
     * タスクがタスクマネージャーに登録された時に呼ばれる
     */
    public void onRegistered() {
    }

    /**
     * ゲームループに入る時に呼ばれる
     */
    public void onEnterLoop() {
    }

    /**
     * ゲームループから抜けた時に呼ばれる
     */
    public void onLeaveLoop() {
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
    protected void sendMessage(TaskEventListener targetTask, TaskMessage message) {
        targetTask.onMessage(this, message);
    }

    /**
     * 指定されたプライオリティのタスクを探して取得する
     *
     * @param priority タスクのプライオリティ
     * @return 見つかったタスクを返す。タスクが見つからなければnull
     */
    protected AbstractTask find(TaskPriority priority) {
        return mTaskManager.find(priority.getPriority());
    }

    /**
     * このタスクの子タスクとしてタスクマネージャーにタスクを登録する
     *
     * @param childTask 登録するタスク
     */
    protected void registerChild(AbstractTask childTask) {
        childTask.setParent(this);
        mChildren.add(childTask);
        mTaskManager.register(childTask);
    }

    protected void setFrameTimer(int frame, FrameTimerEventListener listener) {

    }
}
