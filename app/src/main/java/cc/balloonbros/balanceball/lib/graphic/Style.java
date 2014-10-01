package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Style {
    private String mHash = null;
    private Typeface mFont = null;
    private float mSize = 0;
    private int mColor = Color.BLACK;
    private int mAlpha = 0xff;
    private boolean mAntiAlias = true;
    private Paint.Align mAlign = Paint.Align.LEFT;

    private static Typeface sFont = null;
    private static float sSize = 0;
    private static int sColor = Color.BLACK;
    private static int sAlpha = 0xff;
    private static boolean sAntiAlias = true;
    private static Paint.Align sAlign = Paint.Align.LEFT;

    public static void setDefaultFont(Typeface font) {
        sFont = font;
    }

    public static void setDefaultSize(float size) {
        sSize = size;
    }

    public static void setDefaultColor(int color) {
        sColor = color;
    }

    public static void setDefaultAlpha(int alpha) {
        sAlpha = alpha;
    }

    public static void setDefaultAntiAlias(boolean antiAlias) {
        sAntiAlias = antiAlias;
    }

    public static void setDefaultAlign(Paint.Align align) {
        sAlign = align;
    }

    public static Style getDefault() {
        Style style = new Style();

        if (sFont != null) {
            style.font(sFont);
        }
        if (sSize != 0) {
            style.size(sSize);
        }
        style.color(sColor);
        style.alpha(sAlpha);
        style.antiAlias(sAntiAlias);
        style.align(sAlign);

        return style;
    }

    public Style font(Typeface font) {
        if (mFont == null || !mFont.equals(font)) {
            mHash = null;
            mFont = font;
        }
        return this;
    }

    public Style size(float size) {
        if (mSize != size) {
            mHash = null;
            mSize = size;
        }
        return this;
    }

    public Style color(int color) {
        if (mColor != color) {
            mHash = null;
            mColor = color;
        }
        return this;
    }

    public Style alpha(int alpha) {
        if (mAlpha != alpha) {
            mHash = null;
            mAlpha = alpha;
        }
        return this;
    }

    public Style antiAlias(boolean antiAlias) {
        if (mAntiAlias != antiAlias) {
            mHash = null;
            mAntiAlias = antiAlias;
        }
        return this;
    }

    public Style align(Paint.Align align) {
        if (mAlign != align) {
            mHash = null;
            mAlign = align;
        }
        return this;
    }

    public Style center() {
        return align(Paint.Align.CENTER);
    }

    public Style right() {
        return align(Paint.Align.RIGHT);
    }

    public Style left() {
        return align(Paint.Align.RIGHT);
    }

    protected Paint generatePaint() {
        Paint p = new Paint();
        if (mFont != null) {
            p.setTypeface(mFont);
        }
        if (mSize > 0) {
            p.setTextSize(mSize);
        }
        p.setColor(mColor);
        p.setAlpha(mAlpha);
        p.setAntiAlias(mAntiAlias);
        p.setTextAlign(mAlign);

        return p;
    }

    protected String hash() {
        if (mHash != null) {
            return mHash;
        }

        String hash = String.valueOf(mFont.hashCode()) + String.valueOf(mSize) + String.valueOf(mColor) + String.valueOf(mAlpha);

        if (mAntiAlias) {
            hash += "true";
        } else {
            hash += "false";
        }

        switch (mAlign) {
            case LEFT:   hash += "left"; break;
            case RIGHT:  hash += "right"; break;
            case CENTER: hash += "center"; break;
        }

        mHash = hash;
        return hash;
    }
}
