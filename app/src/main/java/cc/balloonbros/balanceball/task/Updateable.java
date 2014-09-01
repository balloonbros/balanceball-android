package cc.balloonbros.balanceball.task;

public interface Updateable {
    /**
     * タスクメイン処理。
     * 1フレーム毎に1回ずつ実行される。
     */
    public void onUpdate();
}
