package cc.balloonbros.balanceball.lib.graphic;

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
}
