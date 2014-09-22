package cc.balloonbros.balanceball.lib.task.system;

import java.util.ArrayList;

import cc.balloonbros.balanceball.lib.task.timer.BasicTimer;
import cc.balloonbros.balanceball.lib.task.timer.FrameTimer;
import cc.balloonbros.balanceball.lib.task.timer.Timer;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;

abstract public class TimerTask extends BaseTask {
    /**
     * フレームタイマーのキュー
     */
    private ArrayList<Timer> mTimerQueue = new ArrayList<Timer>();
    private ArrayList<Timer> mTimerReserveQueue = new ArrayList<Timer>();

    /**
     * フレームタイマーの起動処理を実行する。
     * 現在のキューに登録されているタイマーを全て捜査して
     * 起動状態になっていればコールバックを呼び出す
     */
    protected void executeTimer() {
        for (int i = 0; i < mTimerReserveQueue.size(); i++) {
            mTimerQueue.add(mTimerReserveQueue.get(i));
        }
        mTimerReserveQueue.clear();
        for (int i = 0; i < mTimerQueue.size(); i++) {
            Timer timer = mTimerQueue.get(i);
            timer.process();
            if (timer.ready()) {
                timer.invoke();
            }
            if (timer.isRemovable()) {
                mTimerQueue.remove(i);
            }
        }
    }

    /**
     * 登録されているタイマーを全てリセットする
     */
    public void resetTimers() {
        mTimerReserveQueue.clear();
        mTimerQueue.clear();
    }

    /**
     * 指定フレーム後にタイマーを起動する
     * @param frame ここに指定したフレーム数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    public Timer setFrameTimer(int frame, TimerEventListener listener) {
        return setFrameTimerQueue().start(frame, listener);
    }

    /**
     * 指定フレーム後にタイマーの起動を繰り返す
     * @param frame ここに指定したフレーム数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    public Timer setFrameInterval(int frame, TimerEventListener listener) {
        return setFrameTimerQueue().start(frame, listener, true);
    }

    /**
     * 指定秒数後にタイマーを起動する
     * @param time ここに指定した秒数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    public Timer setTimer(int time, TimerEventListener listener) {
        return setBasicTimerQueue().start(time, listener);
    }

    /**
     * 指定秒数後にタイマーの起動を繰り返す
     * @param time ここに指定した秒数経過後にコールバックを起動する
     * @param listener コールバックを受け取るリスナー
     */
    public Timer setTimerInterval(int time, TimerEventListener listener) {
        return setBasicTimerQueue().start(time, listener);
    }

    /**
     * フレームタイマーを生成してキューにセットしてから返す
     * @return フレームタイマー
     */
    private Timer setFrameTimerQueue() {
        FrameTimer timer = new FrameTimer();
        mTimerReserveQueue.add(timer);
        return timer;
    }

    /**
     * 通常のタイマーを生成してキューにセットしてから返す
     * @return タイマー
     */
    private Timer setBasicTimerQueue() {
        BasicTimer timer = new BasicTimer();
        mTimerReserveQueue.add(timer);
        return timer;
    }
}
