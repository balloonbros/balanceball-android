package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceView;

import cc.balloonbros.balanceball.lib.GameDisplay;
import cc.balloonbros.balanceball.lib.graphic.style.Style;

/**
 * ゲームの描画サーフェイス。
 * android.graphics.Canvasの薄いラッパー。
 */
public class Surface {
    /**
     * ゲーム用キャンバスのビットマップ。
     * ゲーム画面サイズと端末画面サイズが違う場合、まずこっちのキャンバスに描画した後に
     * 適切なサイズに伸縮してから描画先のキャンバスに転送する。
     */
    private Bitmap mGameBitmap;

    /** SurfaceViewのキャンバス */
    private Canvas mSurfaceCanvas;
    /** 描画先のキャンバス */
    private Canvas mTargetCanvas;
    /** ゲームディスプレイ */
    private GameDisplay mGameDisplay;

    /**
     * コンストラクタ
     */
    public Surface() {
        mGameDisplay = GameDisplay.getInstance();

        if (mGameDisplay.isFit()) {
            Point size = mGameDisplay.getDisplaySize();
            mGameBitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
            mTargetCanvas = new Canvas(mGameBitmap);
        }
    }

    /**
     * ゲームキャンバスのビットマップを伸縮させてメインのキャンバスに転送する
     * @return メインキャンバス
     */
    public Canvas forwardBitmap() {
        if (!mGameDisplay.isFit()) {
            Rect source      = mGameDisplay.getDisplayRect();
            Rect destination = mGameDisplay.getScaledRect();
            mSurfaceCanvas.drawBitmap(mGameBitmap, source, destination, null);
        }

        return mSurfaceCanvas;
    }

    /**
     * 描画先のキャンバスをセットする
     * @param canvas 描画先のキャンバス
     */
    public void setCanvas(Canvas canvas) {
        mSurfaceCanvas = canvas;

        if (mTargetCanvas == null) {
            mTargetCanvas = mSurfaceCanvas;
        }
    }

    /**
     * 描画先のキャンバスを取得する
     * @return 描画先のキャンバス
     */
    public Canvas getCanvas() {
        return mTargetCanvas;
    }

    /**
     * 画面全体を指定した色で塗りつぶす
     * @param color
     */
    public void fill(int color) {
        mTargetCanvas.drawColor(color);
    }

    /**
     * 文字列を描画する
     * @param text 描画する文字列
     */
    public void draw(DrawString text) {
        Paint paint;
        Point position = text.getPosition();
        Style style = text.getStyle();

        if (style != null) {
            paint = style.generatePaint();
        } else {
            paint = Style.getDefault().generatePaint();
        }

        if (text.needsConcatenate()) {
            mTargetCanvas.drawText(text.getChars(), 0, text.getLength(), position.x, position.y, paint);
        } else {
            mTargetCanvas.drawText(text.getFirstString(), position.x, position.y, paint);
        }
    }

    /**
     * シェイプを描画する
     * @param shape 描画するシェイプ
     */
    public void draw(Shape shape) {
        Point position = shape.getPosition();
        mTargetCanvas.drawBitmap(shape.getBitmap(), position.x, position.y, null);
    }

    /**
     * スプライトを描画する
     * @param sprite 描画するスプライト
     */
    public void draw(Sprite sprite) {
        mTargetCanvas.drawBitmap(sprite.getBitmap(), sprite.getSource(), sprite.getRect(), null);
    }

    /**
     * スプライトを画面全体に伸縮して描画する
     * @param sprite 描画するスプライト
     */
    public void drawStretch(Sprite sprite) {
        drawStretch(sprite, GameDisplay.getInstance().getDisplayRect());
    }

    /**
     * スプライトを指定された矩形に伸縮して描画する
     * @param sprite 描画するスプライト
     * @param destination 描画する先の矩形
     */
    public void drawStretch(Sprite sprite, Rect destination) {
        mTargetCanvas.drawBitmap(sprite.getBitmap(), sprite.getSource(), destination, null);
    }

    /**
     * アニメーションを描画する
     * @param animation 描画するアニメーション
     */
    public void draw(Animation animation) {
        Sprite sprite = animation.getCurrentSprite();
        if (sprite != null) {
            draw(sprite);
        }
    }
}