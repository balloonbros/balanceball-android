package cc.balloonbros.balanceball.lib.task;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

import cc.balloonbros.balanceball.lib.display.GameDisplay;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.GameRenderer;
import cc.balloonbros.balanceball.lib.graphic.opengl.Texture;
import cc.balloonbros.balanceball.lib.graphic.style.Style;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.scene.AbstractScene;
import cc.balloonbros.balanceball.lib.scene.SceneChanger;
import cc.balloonbros.balanceball.lib.task.message.TaskMessageListener;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;
import cc.balloonbros.balanceball.lib.task.system.BaseTask;

/**
 * ゲーム内のタスクの基底クラス。
 * タスクはすべてこのクラスを継承する。
 */
abstract public class AbstractTask extends BaseTask implements TaskFunction {
    /** タスク処理関数 */
    private TaskFunction mCurrentFunction = this;
    /** タスクが属しているシーン */
    private AbstractScene mScene = null;

    /** このタスクの子タスクと親タスク */
    private ArrayList<AbstractTask> mChildren = new ArrayList<AbstractTask>();
    private AbstractTask mParent = null;

    /** タスクが実行された時のフレームカウント */
    private long mFrameCountInExecution = 0;
    /** タスクが一時停止中かどうか */
    private boolean mStop = false;
    /** タスク検索に利用するタグ */
    private String mTag = "";
    /** この時間まで一時停止する */
    private long mWaitDuration = 0;
    /** このフレームまで一時停止する */
    private long mWaitFrame = 0;

    /** タスクに登録されているプラグイン一覧 */
    private List<TaskPlugin> mPlugins = new ArrayList<TaskPlugin>();

    public AbstractTask getParent() { return mParent; }
    private void setParent(AbstractTask parentTask) { mParent = parentTask; }
    public TaskManager getTaskManager() { return getScene().getTaskManager(); }
    public long getFrameCount() {
        return GameRenderer.sFrameCount;
        //return getGame().getFrameCount();
    }
    public long getFps() { return getGame().getFps(); }
    public String getTag() { return mTag; }
    public GameDisplay getGameDisplay() { return getGame().getGameDisplay(); }

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
     * タスクが属するシーンをセットする
     * @param scene タスクが属するシーン
     */
    public void belongsTo(AbstractScene scene) {
        mScene = scene;
    }

    /**
     * タスクが属するシーンを取得する
     * @return タスクが属するシーン
     */
    public AbstractScene getScene() {
        return mScene;
    }

    /**
     * タスクが属するゲームを取得する
     * @return タスクが属するゲーム
     */
    public GameMain getGame() {
        return mScene.getGame();
    }

    /**
     * タスクのゲームループを実行する
     * @param surface キャンバス
     */
    public void execute(Surface surface) {
        long frameCount = getFrameCount();

        // タスクの優先度が後ろに下がった場合、既に実行済みのタスクがもう一度実行される可能性があるため
        // タスクの実行が終わったらその時のフレームカウントを保存しておき
        // 同じフレーム内でのタスク実行はスキップする
        if (mFrameCountInExecution == frameCount) {
            return;
        }

        if (mStop) {
            // タスクがwait関数で停止しているかどうかを調べて停止時間を過ぎていたら再開する
            if ((mWaitDuration > 0 && System.currentTimeMillis() > mWaitDuration) ||
                (mWaitFrame    > 0 && frameCount                 > mWaitFrame)) {
                resume();
            }
        } else {
            if (mCurrentFunction != null) {
                mCurrentFunction.update();
            }
            for (int i = 0; i < mPlugins.size(); i++) {
                mPlugins.get(i).onExecute();
            }
        }

        if (this instanceof Drawable) {
            ((Drawable) this).onDraw(surface);
        }

        mFrameCountInExecution = frameCount;
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
        mWaitDuration = 0;
        mWaitFrame = 0;
    }

    /**
     * タスクの実行が一時的に停止しているかどうかを確認する
     * @return タスクの実行が一時停止していればtrue
     */
    public boolean isStop() {
        return mStop;
    }

    /**
     * 指定ミリ秒の間処理を実行せずに待つ
     * @param duration 待つ時間(ミリ秒)
     */
    public void wait(int duration) {
        if (duration > 0) {
            mWaitDuration = System.currentTimeMillis() + duration;
            stop();
        }
    }

    /**
     * 指定フレーム数の間処理を実行せずに待つ
     * @param frameCount 待つ時間(ミリ秒)
     */
    public void waitFrame(int frameCount) {
        if (frameCount > 0) {
            mWaitFrame = getFrameCount() + frameCount;
            stop();
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
    public Bitmap getImage(int assetId) { return getScene().getImage(assetId); }

    /**
     * フォントを取得する
     * @param font フォント名
     * @return フォント
     */
    public Typeface getFont(String font) { return getScene().getFont(font); }

    /**
     * Delegate to getTexture method of the current scene instance.
     * @see AbstractScene#getTexture(int)
     * @param resourceId The resource id.
     * @return A texture.
     */
    public Texture getTexture(int resourceId) {
        return getScene().getTexture(resourceId);
    }

    /**
     * フォントスタイルを取得する
     * @param id スタイルID
     * @return スタイル
     */
    public Style getStyle(String id) { return getScene().getStyle(id); }

    /**
     * デバイスのディスプレイサイズを取得する
     * @return ディスプレイサイズ
     */
    public Point getDisplaySize() { return getGame().getDisplaySize(); }

    /**
     * 自分自身をタスクリストから削除する
     */
    public void kill() {
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
    public SceneChanger changeScene(AbstractScene scene) {
        return getGame().changeScene(scene);
    }

    /**
     * タスクで利用するプラグインを登録する
     * @param plugin プラグイン
     */
    public AbstractTask with(TaskPlugin plugin) {
        plugin.setTask(this);
        plugin.onInitialize();
        mPlugins.add(plugin);
        return this;
    }

    /**
     * 組み込まれているプラグインを取得する
     * @param clazz 取得するプラグイン
     * @param <T> TaskPlugin
     * @return プラグイン
     */
    public <T extends TaskPlugin> T plugin(Class<T> clazz) {
        for (int i = 0; i < mPlugins.size(); i++) {
            TaskPlugin plugin = mPlugins.get(i);
            if (clazz.isAssignableFrom(plugin.getClass())) {
                return clazz.cast(plugin);
            }
        }
        return null;
    }

    /**
     * このタスクが指定されたプラグインを組み込んでいるかどうかを取得する
     * @param clazz プラグイン
     * @param <T> TaskPlugin
     * @return プラグインが組み込まれていればtrue
     */
    public <T extends TaskPlugin> boolean hasPlugin(Class<T> clazz) {
        return plugin(clazz) != null;
    }

    /**
     * プラグインを削除する
     * @param clazz 削除するプラグイン
     */
    public void removePlugin(Class<? extends TaskPlugin> clazz) {
        TaskPlugin plugin = plugin(clazz);
        plugin.onRemoved();
        mPlugins.remove(plugin);
    }

    /* ==============================================
     *             オーバー可能なメソッド
     * ============================================== */

    /**
     * タスク処理関数
     */
    public void update() { }

     /**
     * タスクがタスクマネージャーに登録された時に呼ばれる
     */
    protected void onRegister() {
        for (TaskPlugin plugin: mPlugins) {
            plugin.onRegister();
        }
    }

    /**
     * ゲームループに入る時に呼ばれる
     */
    protected void onEnterLoop() {
        for (TaskPlugin plugin: mPlugins) {
            plugin.onEnterLoop();
        }
    }

    /**
     * ゲームループから抜けた時に呼ばれる
     */
    protected void onLeaveLoop() {
        for (TaskPlugin plugin: mPlugins) {
            plugin.onLeaveLoop();
        }
    }

    /**
     * タスクが削除される時に呼ばれる
     */
    protected void onKilled() {
        for (TaskPlugin plugin: mPlugins) {
            plugin.onRemoved();
            plugin.onKilled();
        }

        mPlugins.clear();
    }
}
