package cc.balloonbros.balanceball.lib.graphic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StyleTemplate {
    private Map<String, FontStyle> mStyles = new HashMap<String, FontStyle>();

    public void add(String tag, FontStyle style) {
        mStyles.put(tag, style);
    }

    public FontStyle get(String tag) {
        return mStyles.get(tag);
    }

    public void dispose() {
        Collection<FontStyle> styles = mStyles.values();
        for (FontStyle style: styles) {
            style.dispose();
        }

        mStyles.clear();
    }
}
