package cc.balloonbros.balanceball;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

public class AssetManager {
    private Resources mResources = null;
    private SparseArray<Bitmap> mAssets = new SparseArray<Bitmap>();

    public AssetManager(Resources resources) {
        mResources = resources;
    }

    public void loadAssets(int... loadAssets) {
        for (int i = 0; i < loadAssets.length; i++) {
            int assetId = loadAssets[i];
            mAssets.put(assetId, BitmapFactory.decodeResource(mResources, assetId));
        }
    }

    public Bitmap getImage(int assetId) {
        return mAssets.get(assetId);
    }
}
