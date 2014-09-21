package cc.balloonbros.balanceball.lib.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;

import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.task.message.TaskEventListener;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;
import cc.balloonbros.balanceball.lib.task.system.TimerTask;

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

    /**
     * コンストラクタ。
     * 優先度最低のタスクを生成する
     */
    public AbstractTask() {
        super();
        setPriority(0xffff);
    }

    /**
     * コンストラクタ
     * @param priority タスクの優先度
     */
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
     * ロードされたリソースから画像を取得する
     * @param assetId 画像ID
     * @return 画像
     */
    public Bitmap getImage(int assetId) { return getScene().getAssetManager().getImage(assetId); }

    /**
     * 数値リソースを取得する
     * @param id リソースID
     * @return 数値
     */
    public int getInteger(int id) { return getGame().getResources().getInteger(id); }

    /**
     * 文字列リソースを取得する
     * @param id リソースID
     * @return 文字列
     */
    public String getString(int id) { return getGame().getResources().getString(id); }

    /**
     * デバイスのディスプレイサイズを取得する
     * @return ディスプレイサイズ
     */
    public Point getDisplaySize() { return getGame().getDisplaySize(); }

    /**
     * 自分自身をタスクリストから削除する
     */
    public void kill() {
        getScene().getTaskManager().remove(this);
        onKilled();
    }

    /**
     * このタスクから他タスクにメッセージを送信する
     * @param targetTask メッセージを送信する先のタスク
     * @param message メッセージオブジェクト。必要に応じて受取先でキャストする
     */
    public void sendMessage(TaskEventListener targetTask, TaskMessage message) {
        targetTask.onMessage(this, message);
    }

    /**
     * 指定されたプライオリティのタスクを探して取得する
     * @param priority タスクのプライオリティ
     * @return 見つかったタスクを返す。タスクが見つからなければnull
     */
    public AbstractTask find(int priority) {
        return getTaskManager().find(priority);
    }

    /**
     * このタスクの子タスクとしてタスクマネージャーにタスクを登録する
     * @param childTask 登録するタスク
     */
    public void registerChild(AbstractTask childTask) {
        childTask.setParent(this);
        mChildren.add(childTask);
        getTaskManager().register(childTask);
    }

    /**
     * シーンを切り替える
     * @param scene 切り替え先のシーン
     */
    public void changeScene(AbstractScene scene) {
        getGame().changeScene(scene);
    }

    /* ==============================================
     *           オーバーライド専用メソッド
     * ============================================== */

     /**
     * タスクがタスクマネージャーに登録された時に呼ばれる
     */
    protected void onRegistered() { }

    /**
     * ゲームループに入る時に呼ばれる
     */
    protected void onEnterLoop() { }

    /**
     * ゲームループから抜けた時に呼ばれる
     */
    protected void onLeaveLoop() { }

    /**
     * タスクが削除される時に呼ばれる
     */
    protected void onKilled() { }
}
