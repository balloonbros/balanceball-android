package cc.balloonbros.balanceball.lib;

import android.content.res.Resources;

public class _ {
    private static Resources mResources;

    static void set(Resources resources) {
        mResources = resources;
    }

    public static int i(int id) { return mResources.getInteger(id); }
    public static String s(int id) { return mResources.getString(id); }
}
