package cc.balloonbros.balanceball.lib.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;

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
abstract public class AbstractTask extends TimerTask implements TaskFunction {
    private AbstractScene mScene = null;
    private TaskFunction mCurrentFunction = this;

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
        if (mCurrentFunction != null) {
            mCurrentFunction.update();
        }

        executeTimer();

        if (this instanceof Drawable) {
            ((Drawable)this).onDraw(canvas);
        }
    }

    /**
     * タスク実行関数を遷移させる
     * @param function タスク実行関数
     */
    public void changeTask(TaskFunction function) { mCurrentFunction = function; }

    /**
     * ロードされたリソースから画像を取得する
     * @param assetId 画像ID
     * @return 画像
     */
    public Bitmap getImage(int assetId) { return getScene().getAssetManager().getImage(assetId); }

    /**
     * フォントを取得する
     * @param font フォント名
     * @return フォント
     */
    public Typeface getFont(String font) { return getScene().getAssetManager().getFont(font); }

    /**
     * デバイスのディスプレイサイズを取得する
     * @return ディスプレイサイズ
     */
    public Point getDisplaySize() { return getGame().getDisplaySize(); }

    /**
     * 自分自身をタスクリストから削除する
     */
    public void kill() {
        super.kill();
        mCurrentFunction = null;
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
     * タスク処理関数
     */
    public void update() { }

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
