package cc.balloonbros.balanceball.lib.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.task.message.TaskEventListener;
import cc.balloonbros.balanceball.lib.task.message.TaskMessage;
import cc.balloonbros.balanceball.lib.task.timer.FrameTimer;
import cc.balloonbros.balanceball.lib.task.timer.FrameTimerEventListener;

/**
 * ゲーム内のタスクの基底クラス。
 * タスクはすべてこのクラスを継承する。
 */
abstract public class AbstractTask implements Updateable {
    private GameMain mGame = null;
    private TaskManager mTaskManager = null;
    private int mPriority = 0xffff;

    /**
     * このタスクの子タスクと親タスク
     */
    private ArrayList<AbstractTask> mChildren = new ArrayList<AbstractTask>();
    private AbstractTask mParent = null;

    /**
     * フレームタイマーのキュー
     */
    private ArrayList<FrameTimer> mFrameTimerQueue = new ArrayList<FrameTimer>();
    private ArrayList<FrameTimer> mFrameTimerReserveQueue = new ArrayList<FrameTimer>();

    public int getPriority() {
        return mPriority;
    }
    public void setPriority(int priority) {
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

    public AbstractTask() {
        setPriority(0xffff);
    }

    public AbstractTask(int priority) {
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

        for (int i = 0; i < mFrameTimerReserveQueue.size(); i++) {
            mFrameTimerQueue.add(mFrameTimerReserveQueue.get(i));
        }
        mFrameTimerReserveQueue.clear();
        for (int i = 0; i < mFrameTimerQueue.size(); i++) {
            FrameTimer timer = mFrameTimerQueue.get(i);
            if (timer.ready()) {
                timer.invoke();
            }
            if (timer.isRemovable()) {
                mFrameTimerQueue.remove(i);
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
    protected AbstractTask find(int priority) {
        return mTaskManager.find(priority);
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
        mFrameTimerReserveQueue.add(timer);
        return timer;
    }

    protected void setTouchListener(View.OnTouchListener listener) {
        getGame().getView().setOnTouchListener(listener);
    }

    protected long getFrameCount() {
        return getGame().getFrameCount();
    }
}
