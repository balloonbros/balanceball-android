package cc.balloonbros.balanceball.lib.task;

import cc.balloonbros.balanceball.lib.graphic.Surface;

public interface Drawable {
    /**
     * タスク描画処理。
     * 1フレーム毎に1回ずつ実行される。
     */
    public void onDraw(Surface surface);
}
