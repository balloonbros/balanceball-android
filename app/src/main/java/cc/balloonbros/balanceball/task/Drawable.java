package cc.balloonbros.balanceball.task;

import android.graphics.Canvas;

public interface Drawable {
    /**
     * タスク描画処理。
     * 1フレーム毎に1回ずつ実行される。
     */
    public void onDraw(Canvas canvas);
}
