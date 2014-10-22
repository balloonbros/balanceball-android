package cc.balloonbros.balanceball.lib;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class _ {
    private static Resources mResources;

    static void set(Resources resources) {
        mResources = resources;
    }

    public static int i(int id) { return mResources.getInteger(id); }
    public static String s(int id) { return mResources.getString(id); }
    public static int c(int id) { return mResources.getColor(id); }
    public static Drawable d(int id) { return mResources.getDrawable(id); }
}
