package cc.balloonbros.balanceball.lib.task;

import android.graphics.Canvas;

import cc.balloonbros.balanceball.lib.graphic.Surface;

public interface Drawable {
    /**
     * タスク描画処理。
     * 1フレーム毎に1回ずつ実行される。
     */
    public void onDraw(Canvas canvas, Surface surface);
}
