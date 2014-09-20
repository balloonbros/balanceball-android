package cc.balloonbros.balanceball.lib.task.timer;

public interface Timer {
    /**
     * タイマーを開始する
     * @param timerCount 待ち
     * @param listener コールバックリスナー
     */
    public Timer start(long timerCount, TimerEventListener listener);

    /**
     * タイマーを開始する
     * @param timerCount 待ち
     * @param listener コールバックリスナー
     * @param loop タイマーをループさせるかどうか
     */
    public Timer start(long timerCount, TimerEventListener listener, boolean loop);

    /**
     * タイマーコールバックを起動できるかどうかをチェックする
     * @return タイマーコールバックが起動できる場合はtrue
     */
    public boolean ready();

    /**
     * タイマーコールバックを起動させる
     */
    public void invoke();

    /**
     * フレーム毎に処理される関数
     */
    public void process();

    /**
     * タイマーを止める
     */
    public void stop();

    /**
     * このタイマーの実行完了後に削除していいかどうかを確認する
     * @return 削除可能であればtrue
     */
    public boolean isRemovable();
}
