package cc.balloonbros.balanceball.lib.task.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;

import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.TaskManager;
import cc.balloonbros.balanceball.lib.task.Updateable;
import cc.balloonbros.balanceball.lib.task.message.TaskEventListener;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;
import cc.balloonbros.balanceball.lib.task.timer.FrameTimer;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.lib.task.timer.SecondTimer;
import cc.balloonbros.balanceball.lib.task.timer.Timer;

/**
 * ゲーム内のタスクの基底クラス。
 * タスクはすべてこのクラスを継承する。
 */
abstract public class AbstractTask extends TimerTask implements Updateable {
    private AbstractScene mScene = null;

    /**
     * このタスクの子タスクと親タスク
     */
    private ArrayList<AbstractTask> mChildren = new ArrayList<AbstractTask>();
    private AbstractTask mParent = null;

    public AbstractTask getParent() { return mParent; }
    private void setParent(AbstractTask parentTask) { mParent = parentTask; }
    public void setScene(AbstractScene scene) { mScene = scene; }
    public AbstractScene getScene() { return mScene; }
    protected GameMain getGame() { return mScene.getGame(); }
    protected TaskManager getTaskManager() { return getScene().getTaskManager(); }
    protected long getFrameCount() { return getGame().getFrameCount(); }

    public AbstractTask() {
        super();
        setPriority(0xffff);
    }

    public AbstractTask(int priority) {
        super();
        setPriority(priority);
    }

    /**
     * タスクのゲームループを実行する
     * @param canvas キャンバス
     */
    public void execute(Canvas canvas) {
        this.onUpdate();

        if (this instanceof Drawable) {
            ((Drawable)this).onDraw(canvas);
        }

        executeTimer();
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
     * ロードされたリソースから画像を取得する
     * @param assetId 画像ID
     * @return 画像
     */
    protected Bitmap getImage(int assetId) { return getScene().getAssetManager().getImage(assetId); }
    protected int getInteger(int id) { return getGame().getResources().getInteger(id); }

    /**
     * デバイスのディスプレイサイズを取得する
     * @return ディスプレイサイズ
     */
    protected Point getDisplaySize() { return getGame().getDisplaySize(); }

    /**
     * 自分自身をタスクリストから削除する
     */
    protected void kill() { getScene().getTaskManager().remove(this); }

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
    protected AbstractTask find(int priority) {
        return getTaskManager().find(priority);
    }

    /**
     * このタスクの子タスクとしてタスクマネージャーにタスクを登録する
     *
     * @param childTask 登録するタスク
     */
    protected void registerChild(AbstractTask childTask) {
        childTask.setParent(this);
        mChildren.add(childTask);
        getTaskManager().register(childTask);
    }

    /**
     * シーンを切り替える
     * @param scene 切り替え先のシーン
     */
    protected void changeScene(AbstractScene scene) {
        getGame().changeScene(scene);
    }

    protected void setTouchListener(View.OnTouchListener listener) {
        getGame().getView().setOnTouchListener(listener);
    }
}
