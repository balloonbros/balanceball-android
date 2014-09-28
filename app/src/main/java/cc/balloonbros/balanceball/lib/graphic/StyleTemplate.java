package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class StyleTemplate {
    private String mHash = null;
    private Typeface mFont = null;
    private float mSize = 0;
    private int mColor = Color.BLACK;
    private int mAlpha = 0xff;
    private boolean mAntiAlias = true;
    private Paint.Align mAlign = Paint.Align.LEFT;

    public StyleTemplate font(Typeface font) {
        if (mFont == null || !mFont.equals(font)) {
            mHash = null;
            mFont = font;
        }
        return this;
    }

    public StyleTemplate size(float size) {
        if (mSize != size) {
            mHash = null;
            mSize = size;
        }
        return this;
    }

    public StyleTemplate color(int color) {
        if (mColor != color) {
            mHash = null;
            mColor = color;
        }
        return this;
    }

    public StyleTemplate alpha(int alpha) {
        if (mAlpha != alpha) {
            mHash = null;
            mAlpha = alpha;
        }
        return this;
    }

    public StyleTemplate antiAlias(boolean antiAlias) {
        if (mAntiAlias != antiAlias) {
            mHash = null;
            mAntiAlias = antiAlias;
        }
        return this;
    }

    public StyleTemplate align(Paint.Align align) {
        if (mAlign != align) {
            mHash = null;
            mAlign = align;
        }
        return this;
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
