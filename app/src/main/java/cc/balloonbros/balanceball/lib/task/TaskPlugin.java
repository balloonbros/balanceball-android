package cc.balloonbros.balanceball.lib.task;

abstract public class TaskPlugin {
    private AbstractTask mTask;

    protected void setTask(AbstractTask task) {
        mTask = task;
    }

    public AbstractTask getTask() {
        return mTask;
    }

    /**
     * タスク関数が実行される時に呼ばれる
     */
    protected void onExecute() { }

    /**
     * タスクがタスクマネージャーに登録される時に呼ばれる
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
