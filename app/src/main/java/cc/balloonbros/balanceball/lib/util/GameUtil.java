package cc.balloonbros.balanceball.lib.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import cc.balloonbros.balanceball.lib.CurrentGame;

public class GameUtil {
    /**
     * 現在のFPSにおいてミリ秒からフレーム数に変換する。
     * 指定されたミリ秒の間に、返されたフレーム数だけループされる。
     * @param millieSecond フレーム数に変換したいミリ秒
     * @return フレーム数
     */
    public static int milliSecondToFrameCount(int millieSecond) {
        long fps = CurrentGame.get().getFps();
        return (int) (fps * millieSecond / 1000);
    }

    /**
     * Convert a drawable object to a bitmap object.
     * @param drawable Conversion target.
     * @return Bitmap object converted from drawable object.
     */
    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
    }
}
