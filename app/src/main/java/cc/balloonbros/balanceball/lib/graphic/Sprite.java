package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Bitmap;
import android.graphics.Rect;

import cc.balloonbros.balanceball.lib.CurrentGame;

/**
 * スプライトクラス
 */
public class Sprite extends DrawObject {
    /** スプライトのビットマップ */
    private Bitmap mBitmap = null;
    /** キャンバスに転送する矩形 */
    private Rect mSource = new Rect();

    /**
     * ビットマップからスプライトを生成する
     * @param bitmap ビットマップ
     */
    public Sprite(Bitmap bitmap) {
        mBitmap = bitmap;
        setSource(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    /**
     * リソースIDからスプライトを生成する
     * @param resourceId リソースID
     */
    public Sprite(int resourceId) {
        this(CurrentGame.get().getCurrentScene().getImage(resourceId));
    }

    /**
     * キャンバスに転送する矩形を取得
     * @return 転送元矩形
     */
    public Rect getSource() {
        return mSource;
    }

    /**
     * キャンバスに転送する矩形を設定する
     * @param source 転送元矩形
     */
    public void setSource(Rect source) {
        setSource(source.left, source.top, source.right - source.left, source.bottom - source.top);
    }

    /**
     * キャンバスに転送する矩形を設定する
     * @param x 転送元x座標
     * @param y 転送元y座標
     * @param width 転送元矩形の幅
     * @param height 転送元矩形の高さ
     */
    public void setSource(int x, int y, int width, int height) {
        mSource.set(x, y, x + width, y + height);
        updateRect();
    }

    /**
     * ビットマップを取得する
     * @return ビットマップ
     */
    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * ビットマップを指定された幅、高さに分割する
     * @param width 幅
     * @param height 高さ
     */
    public SlicedSprite slice(int width, int height) {
        return new SlicedSprite(this, width, height);
    }

    @Override
    public int getWidth() {
        return mSource.right - mSource.left;
    }

    @Override
    public int getHeight() {
        return mSource.bottom - mSource.top;
    }
}
