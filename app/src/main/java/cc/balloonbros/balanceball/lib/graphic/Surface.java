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

    /** 描画先のデフォルト位置 */
    private Point mDefaultPosition = null;

    /**
     * 描画先のキャンバスをセットする
     * @param canvas 描画先のキャンバス
     */
    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
    }

    public void draw(DrawString text, float x, float y) {
        Style style = text.getStyle();
        Paint paint;
        if (style != null) {
            paint = style.generatePaint();
        } else {
            paint = Style.getDefault().generatePaint();
        }

        if (text.needsConcatenate()) {
            mCanvas.drawText(text.getChars(), 0, text.getLength(), x, y, paint);
        } else {
            mCanvas.drawText(text.getFirstString(), x, y, paint);
        }
    }

    public void draw(DrawString text) {
        int x = 0;
        int y = 0;

        Point p = text.getPosition();
        if (p != null) {
            x = p.x;
            y = p.y;
        }

        draw(text, x, y);
    }
}