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
import cc.balloonbros.balanceball.lib.task.system.BaseTask;
import cc.balloonbros.balanceball.lib.task.timer.FrameTimer;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;
import cc.balloonbros.balanceball.lib.task.timer.SecondTimer;
import cc.balloonbros.balanceball.lib.task.timer.Timer;

/**
 * ゲーム内のタスクの基底クラス。
 * タスクはすべてこのクラスを継承する。
 */
abstract public class AbstractTask extends BaseTask implements Updateable {
    private AbstractScene mScene = null;

    /**
     * このタスクの子タスクと親タスク
     */
    private ArrayList<AbstractTask> mChildren = new ArrayList<AbstractTask>();
    private AbstractTask mParent = null;

    /**
     * フレームタイマーのキュー
     */
    private ArrayList<Timer> mTimerQueue = new ArrayList<Timer>();
    private ArrayList<Timer> mTimerReserveQueue = new ArrayList<Timer>();

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
    protected void execute(Canvas canvas) {
        this.onUpdate();

        if (this instanceof Drawable) {
            ((Drawable)this).onDraw(canvas);
        }

        for (int i = 0; i < mTimerReserveQueue.size(); i++) {
            mTimerQueue.add(mTimerReserveQueue.get(i));
        }
        mTimerReserveQueue.clear();
        for (int i = 0; i < mTimerQueue.size(); i++) {
            Timer timer = mTimerQueue.get(i);
            if (timer.ready()) {
                timer.invoke();
            }
            if (timer.isRemovable()) {
                mTimerQueue.remove(i);
            }
        }
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

    /**
     * 指定フレーム後にタイマーを起動する
     * @param frame ここに指定したフレーム数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    protected Timer setFrameTimer(int frame, TimerEventListener listener) {
        return setFrameTimerQueue().start(frame, listener);
    }

    /**
     * 指定フレーム後にタイマーの起動を繰り返す
     * @param frame ここに指定したフレーム数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    protected Timer setFrameInterval(int frame, TimerEventListener listener) {
        return setFrameTimerQueue().start(frame, listener, true);
    }

    /**
     * 指定秒数後にタイマーを起動する
     * @param time ここに指定した秒数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    protected Timer setTimer(int time, TimerEventListener listener) {
        return setSecondTimerQueue().start(time, listener);
    }

    /**
     * 指定秒数後にタイマーの起動を繰り返す
     * @param time ここに指定した秒数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    protected Timer setTimerInterval(int time, TimerEventListener listener) {
        return setSecondTimerQueue().start(time, listener);
    }

    private Timer setFrameTimerQueue() {
        FrameTimer timer = new FrameTimer(getGame());
        mTimerReserveQueue.add(timer);
        return timer;
    }

    private Timer setSecondTimerQueue() {
        SecondTimer timer = new SecondTimer();
        mTimerReserveQueue.add(timer);
        return timer;
    }

    protected void setTouchListener(View.OnTouchListener listener) {
        getGame().getView().setOnTouchListener(listener);
    }
}
