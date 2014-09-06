package cc.balloonbros.balanceball.lib.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import cc.balloonbros.balanceball.lib.Drawable;
import cc.balloonbros.balanceball.lib.task.timer.FrameTimerEventListener;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.TaskEventListener;
import cc.balloonbros.balanceball.TaskPriority;
import cc.balloonbros.balanceball.lib.TaskMessage;
import cc.balloonbros.balanceball.lib.Updateable;
import cc.balloonbros.balanceball.lib.task.timer.FrameTimer;

/**
 * ゲーム内のタスクの基底クラス。
 * タスクはすべてこのクラスを継承する。
 */
abstract public class AbstractTask {
    private GameMain mGame = null;
    private TaskManager mTaskManager = null;
    private TaskPriority mPriority = TaskPriority.MINIMUM;

    /**
     * このタスクの子タスクと親タスク
     */
    private ArrayList<AbstractTask> mChildren = new ArrayList<AbstractTask>();
    private AbstractTask mParent = null;

    /**
     * フレームタイマーのキュー
     */
    private LinkedList<FrameTimer> mFrameTimerQueue = new LinkedList<FrameTimer>();
    private LinkedList<FrameTimer> mFrameTimerReserveQueue = new LinkedList<FrameTimer>();

    public int getPriority() {
        return mPriority.getPriority();
    }
    public void setPriority(TaskPriority priority) {
        mPriority = priority;
    }
    public AbstractTask getParent() {
        return mParent;
    }
    private void setParent(AbstractTask parentTask) {
        mParent = parentTask;
    }
    public void setGame(GameMain game) {
        mGame = game;
        mTaskManager = mGame.getTaskManager();
    }
    protected GameMain getGame() {
        return mGame;
    }
    protected TaskManager getTaskManager() {
        return mTaskManager;
    }

    /**
     * タスクのゲームループを実行する
     * @param canvas キャンバス
     */
    protected void execute(Canvas canvas) {
        if (this instanceof Updateable) {
            ((Updateable)this).onUpdate();
        }

        if (this instanceof Drawable) {
            ((Drawable)this).onDraw(canvas);
        }

        for (FrameTimer timer: mFrameTimerReserveQueue) {
            mFrameTimerQueue.offer(timer);
        }
        mFrameTimerReserveQueue.clear();
        Iterator<FrameTimer> it = mFrameTimerQueue.iterator();
        while (it.hasNext()) {
            FrameTimer timer = it.next();
            if (timer.ready()) {
                timer.invoke();
            }
            if (timer.isRemovable()) {
                it.remove();
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
    protected Bitmap getImage(int assetId) {
        return mGame.getAssetManager().getImage(assetId);
    }
    protected int getInteger(int id) {
        return mGame.getResources().getInteger(id);
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

    /**
     * 指定フレーム後にタイマーを起動する
     * @param frame ここに指定したフレーム数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    protected FrameTimer setFrameTimer(int frame, FrameTimerEventListener listener) {
        return setFrameTimerQueue().start(frame, listener);
    }

    /**
     * 指定フレーム後にタイマーを起動を起動するのを繰り返す
     * @param frame ここに指定したフレーム数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    protected FrameTimer setFrameInterval(int frame, FrameTimerEventListener listener) {
        return setFrameTimerQueue().start(frame, listener, true);
    }

    private FrameTimer setFrameTimerQueue() {
        FrameTimer timer = new FrameTimer(getGame());
        mFrameTimerReserveQueue.offer(timer);
        return timer;
    }
}
