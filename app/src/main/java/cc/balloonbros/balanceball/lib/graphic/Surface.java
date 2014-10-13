package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * ゲームの描画面
 */
public class Surface {
    /** 描画先のキャンバス */
    private Canvas mCanvas;

    /**
     * 描画先のキャンバスをセットする
     * @param canvas 描画先のキャンバス
     */
    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
    }

    /**
     * 文字列を描画する
     * @param text 描画する文字列
     */
    public void draw(DrawString text) {
        Paint paint;
        Point position = text.getPosition();
        FontStyle style = text.getStyle();

        if (style != null) {
            paint = style.generatePaint();
        } else {
            paint = FontStyle.getDefault().generatePaint();
        }

        if (text.needsConcatenate()) {
            mCanvas.drawText(text.getChars(), 0, text.getLength(), position.x, position.y, paint);
        } else {
            mCanvas.drawText(text.getFirstString(), position.x, position.y, paint);
        }
    }

    /**
     * シェイプを描画する
     * @param shape 描画するシェイプ
     */
    public void draw(Shape shape) {
        shape.drawToCanvas(mCanvas);
    }

    /**
     * スプライトを描画する
     * @param sprite 描画するスプライト
     */
    public void draw(Sprite sprite) {
        Point position = sprite.getPosition();
        mCanvas.drawBitmap(sprite.getBitmap(), position.x, position.y, null);
    }
}