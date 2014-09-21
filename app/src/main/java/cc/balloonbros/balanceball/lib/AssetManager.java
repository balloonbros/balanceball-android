package cc.balloonbros.balanceball.lib;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.SparseArray;

import java.util.HashMap;

/**
 * ゲーム内で利用する素材を管理する。
 */
public class AssetManager {
    /** リソース */
    private Resources mResources = null;

    /** 読み込んだ画像 */
    private SparseArray<Bitmap> mBitmaps = new SparseArray<Bitmap>();

    /** 読み込んだフォント */
    private HashMap<String, Typeface> mFonts = new HashMap<String, Typeface>();

    /**
     * コンストラクタ
     * @param resources リソース
     */
    public AssetManager(Resources resources) { mResources = resources; }

    /**
     * 画像を読み込む。読み込んだ画像はgetImageメソッドで取得する
     * @param assetIds 素材ID。複数指定可能
     * @see #getImage(int)
     */
    public void loadBitmaps(int... assetIds) {
        for (int assetId: assetIds) {
            mBitmaps.put(assetId, BitmapFactory.decodeResource(mResources, assetId));
        }
    }

    /**
     * フォントを読み込む。読み込んだ画像はgetFontメソッドで取得する
     * フォントはassets以下のfontsディレクトリから読み込まれる。
     * @param fonts フォント名。複数指定可能
     * @see #getFont(String)
     */
    public void loadFonts(String... fonts) {
        for (String font: fonts) {
            Typeface typeface = Typeface.createFromAsset(mResources.getAssets(), "fonts/" + font);
            mFonts.put(font, typeface);
        }
    }

    /**
     * loadBitmapsメソッドで読み込んだ画像を取得する
     * @param assetId 画像ID
     * @return ビットマップオブジェクト
     * @see #loadBitmaps(int...)
     */
    public Bitmap getImage(int assetId) { return mBitmaps.get(assetId); }

    /**
     * loadFontsメソッドで読み込んだフォントを取得する
     * @param font フォント名
     * @return フォント
     * @see #loadFonts(String...)
     */
    public Typeface getFont(String font) { return mFonts.get(font); }

    /**
     * 読み込んだ素材を全て破棄する
     */
    public void dispose() {
        for (int i = 0; i < mBitmaps.size(); i++) {
            mBitmaps.get(mBitmaps.keyAt(i)).recycle();
        }
        mBitmaps.clear();
    }
}
