package cc.balloonbros.balanceball.lib.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;

import java.util.ArrayList;

import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.task.message.TaskMessageListener;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;
import cc.balloonbros.balanceball.lib.task.system.TimerTask;

/**
 * ゲーム内のタスクの基底クラス。
 * タスクはすべてこのクラスを継承する。
 */
abstract public class AbstractTask extends TimerTask implements TaskFunction {
    /** タスクが属しているシーン */
    private AbstractScene mScene = null;

    /** タスク処理関数 */
    private TaskFunction mCurrentFunction = this;

    /** このタスクの子タスクと親タスク */
    private ArrayList<AbstractTask> mChildren = new ArrayList<AbstractTask>();
    private AbstractTask mParent = null;

    /** タスクが実行された時のフレームカウント */
    private long mFrameCountInExecution = 0;

    /** タスクが一時停止中かどうか */
    private boolean mStop = false;

    /** タスク検索に利用するタグ */
    private String mTag = "";

    public AbstractTask getParent() { return mParent; }
    private void setParent(AbstractTask parentTask) { mParent = parentTask; }
    public void belongsTo(AbstractScene scene) { mScene = scene; }
    public AbstractScene getScene() { return mScene; }
    public GameMain getGame() { return mScene.getGame(); }
    public TaskManager getTaskManager() { return getScene().getTaskManager(); }
    public long getFrameCount() { return getGame().getFrameCount(); }
    public long getFps() { return getGame().getFps(); }
    public String getTag() { return mTag; }

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
        // タスクの優先度が後ろに下がった場合、既に実行済みのタスクがもう一度実行される可能性があるため
        // タスクの実行が終わったらその時のフレームカウントを保存しておき
        // 同じフレーム内でのタスク実行はスキップする
        if (mFrameCountInExecution == getFrameCount()) {
            return;
        }

        if (!mStop) {
            if (mCurrentFunction != null) {
                mCurrentFunction.update();
            }
            executeTimer();
        }

        if (this instanceof Drawable) {
            ((Drawable)this).onDraw(canvas);
        }

        mFrameCountInExecution = getFrameCount();
    }

    /**
     * タスクにタグを設定する
     */
    public void setTag(String tag) {
        mTag = tag;
        if (getScene() != null) {
            getTaskManager().registerTagList(this);
        }
    }

    /**
     * タスクの実行を一時的に停止します
     * タスクは停止されても描画はし続けます
     */
    public void stop() {
        mStop = true;
    }

    /**
     * タスクの実行を再開します
     */
    public void resume() {
        mStop = false;
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
        resetTimers();
        mCurrentFunction = null;
        getScene().getTaskManager().remove(this);
        onKilled();
    }

    /**
     * このタスクから他タスクにメッセージを送信する
     * @param targetTask メッセージを送信する先のタスク
     * @param message メッセージオブジェクト。必要に応じて受取先でキャストする
     */
    public void sendMessage(TaskMessageListener targetTask, TaskMessage message) {
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
     * 指定されたタグがついているタスクを探して取得する
     * @param tag 検索するタグ
     * @return タスクのリスト
     */
    public ArrayList<AbstractTask> findByTag(String tag) {
        return getTaskManager().findByTag(tag);
    }

    /**
     * 新しくタスクをタスクマネージャーに登録する
     * @param registerTask 登録するタスク
     */
    public void registerTask(AbstractTask registerTask) {
        getTaskManager().register(registerTask);
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
    protected void onRegister() { }

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
