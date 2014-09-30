package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Paint;

import java.util.HashMap;

public class PaintFactory {
    private HashMap<String, Paint> mCache = new HashMap<String, Paint>();

    public Paint createFromTemplate(Style template) {
        if (mCache.containsKey(template.hash())) {
            return mCache.get(template.hash());
        }

        Paint p = template.generatePaint();
        mCache.put(template.hash(), p);
        return p;
    }
}
