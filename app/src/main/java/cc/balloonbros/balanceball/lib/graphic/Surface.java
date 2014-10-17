package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import cc.balloonbros.balanceball.lib.GameDisplay;
import cc.balloonbros.balanceball.lib.graphic.style.FontStyle;

/**
 * ゲームの描画面
 */
public class Surface {
    /** 描画先のキャンバス */
    private Canvas mCanvas;
    /** 描画矩形で利用する */
    private Rect mSourceRect = new Rect();

    /**
     * 描画先のキャンバスをセットする
     * @param canvas 描画先のキャンバス
     */
    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
    }

    /**
     * 描画先のキャンバスを取得する
     * @return 描画先のキャンバス
     */
    public Canvas getCanvas() {
        return mCanvas;
    }

    /**
     * 画面全体を指定した色で塗りつぶす
     * @param color
     */
    public void fill(int color) {
        mCanvas.drawColor(color);
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

    /**
     * スプライトを画面全体に伸縮して描画する
     * @param sprite 描画するスプライト
     */
    public void drawStrech(Sprite sprite) {
        sprite.setPosition(0, 0);
        mSourceRect.set(0, 0, sprite.getWidth(), sprite.getHeight());
        mCanvas.drawBitmap(sprite.getBitmap(), mSourceRect, GameDisplay.getInstance().getDisplayRect(), null);
    }

    /**
     * スプライトを指定された矩形に伸縮して描画する
     * @param sprite 描画するスプライト
     * @param destination 描画する先の矩形
     */
    public void drawStrech(Sprite sprite, Rect destination) {
        mSourceRect.set(0, 0, sprite.getWidth(), sprite.getHeight());
        mCanvas.drawBitmap(sprite.getBitmap(), mSourceRect, destination, null);
    }

    /**
     * スプライトの指定された矩形部分を描画先キャンバスの指定された矩形に伸縮して描画する
     * @param sprite 描画するスプライト
     * @param source 描画する元の矩形
     * @param destination 描画する先の矩形
     */
    public void drawStrech(Sprite sprite, Rect source, Rect destination) {
        mCanvas.drawBitmap(sprite.getBitmap(), source, destination, null);
    }
}