package cc.balloonbros.balanceball.lib;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

/**
 * ゲーム内で利用する素材を管理する。
 */
public class AssetManager {
    /**
     * リソース
     */
    private Resources mResources = null;

    /**
     * 読み込んだ画像を管理するハッシュテーブル
     */
    private SparseArray<Bitmap> mAssets = new SparseArray<Bitmap>();

    /**
     * コンストラクタ
     *
     * @param resources リソース
     */
    public AssetManager(Resources resources) { mResources = resources; }

    /**
     * 素材を読み込む
     *
     * @param assetIds 素材ID。複数指定可能
     */
    public void loadAssets(int... assetIds) {
        for (int i = 0; i < assetIds.length; i++) {
            int assetId = assetIds[i];
            mAssets.put(assetId, BitmapFactory.decodeResource(mResources, assetId));
        }
    }

    /**
     * あらかじめ読み込んだ画像を取得する
     *
     * @param assetId 画像ID
     * @return ビットマップオブジェクト
     */
    public Bitmap getImage(int assetId) { return mAssets.get(assetId); }

    /**
     * 読み込んだ素材を全て破棄する
     */
    public void dispose() {
        for (int i = 0; i < mAssets.size(); i++) {
            mAssets.get(mAssets.keyAt(i)).recycle();
        }
        mAssets.clear();
    }
}
