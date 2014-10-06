package cc.balloonbros.balanceball.lib.graphic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StyleTemplate {
    private Map<String, Style> mStyles = new HashMap<String, Style>();

    public void add(String tag, Style style) {
        mStyles.put(tag, style);
    }

    public Style get(String tag) {
        return mStyles.get(tag);
    }

    public void dispose() {
        Collection<Style> styles = mStyles.values();
        for (Style style: styles) {
            style.dispose();
        }

        mStyles.clear();
    }
}
