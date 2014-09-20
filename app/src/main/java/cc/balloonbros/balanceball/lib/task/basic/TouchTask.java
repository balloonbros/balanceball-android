package cc.balloonbros.balanceball.lib.task.basic;

import android.view.MotionEvent;
import android.view.View;

import cc.balloonbros.balanceball.lib.task.AbstractTask;

/**
 * タッチイベントを検知するためのタスク
 */
abstract public class TouchTask extends AbstractTask implements View.OnTouchListener {
    /**
     * タッチイベントのリスナーを登録する
     * @param listener リスナー
     */
    protected void setTouchListener(View.OnTouchListener listener) {
        getGame().getView().setOnTouchListener(listener);
    }

    @Override
    public void onRegistered() {
        setTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return onTouch(event);
    }

    /* ==============================================
     *           オーバーライド専用メソッド
     * ============================================== */

    /**
     * タッチが検出された時に呼び出される
     * @param event モーションイベント
     * @return trueを返すとイベントを検知し続ける
     */
    public boolean onTouch(MotionEvent event) { return false; }
}
