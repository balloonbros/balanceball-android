package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class FontStyle {
    private boolean mModified = false;
    private Paint mPaint = null;
    private Typeface mFont = null;
    private float mSize = 0;
    private Integer mColor = null;
    private int mAlpha = -1;
    private Boolean mAntiAlias = null;
    private Paint.Align mAlign = null;

    private static FontStyle sDefault = null;
    private static Typeface sFont = null;
    private static float sSize = 16;
    private static int sColor = Color.BLACK;
    private static int sAlpha = 0xff;
    private static boolean sAntiAlias = true;
    private static Paint.Align sAlign = Paint.Align.LEFT;

    private static void resetDefault() {
        sDefault = null;
    }

    public static void setDefaultFont(Typeface font) {
        sFont = font;
        resetDefault();
    }

    public static void setDefaultSize(float size) {
        sSize = size;
        resetDefault();
    }

    public static void setDefaultColor(int color) {
        sColor = color;
        resetDefault();
    }

    public static void setDefaultAlpha(int alpha) {
        sAlpha = alpha;
        resetDefault();
    }

    public static void setDefaultAntiAlias(boolean antiAlias) {
        sAntiAlias = antiAlias;
        resetDefault();
    }

    public static void setDefaultAlign(Paint.Align align) {
        sAlign = align;
        resetDefault();
    }

    public static FontStyle getDefault() {
        if (sDefault != null) {
            return sDefault;
        }

        FontStyle style = new FontStyle();

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

        sDefault = style;
        return style;
    }

    public static FontStyle from(FontStyle template) {
        FontStyle style = new FontStyle();

        style.font(template.font());
        style.size(template.size());
        style.color(template.color());
        style.alpha(template.alpha());
        style.antiAlias(template.antiAlias());
        style.align(template.align());

        return style;
    }

    public Typeface font() {
        return mFont;
    }

    public float size() {
        return mSize;
    }

    public int color() {
        if (mColor == null) {
            return sColor;
        } else {
            return mColor;
        }
    }

    public int alpha() {
        return mAlpha;
    }

    public boolean antiAlias() {
        if (mAntiAlias == null) {
            return sAntiAlias;
        } else {
            return mAntiAlias;
        }
    }

    public Paint.Align align() {
        return mAlign;
    }

    public FontStyle font(Typeface font) {
        if (font != null && (mFont == null || mFont != font)) {
            mModified = true;
            mFont = font;
        }
        return this;
    }

    public FontStyle size(float size) {
        if (mSize != size) {
            mModified = true;
            mSize = size;
        }
        return this;
    }

    public FontStyle color(int color) {
        if (mColor == null || mColor != color) {
            mModified = true;
            mColor = color;
        }
        return this;
    }

    public FontStyle alpha(int alpha) {
        if (mAlpha != alpha) {
            mModified = true;
            mAlpha = alpha;
        }
        return this;
    }

    public FontStyle antiAlias(boolean antiAlias) {
        if (mAntiAlias == null || mAntiAlias != antiAlias) {
            mModified = true;
            mAntiAlias = antiAlias;
        }
        return this;
    }

    public FontStyle align(Paint.Align align) {
        if (mAlign != align) {
            mModified = true;
            mAlign = align;
        }
        return this;
    }

    public FontStyle center() {
        return align(Paint.Align.CENTER);
    }

    public FontStyle right() {
        return align(Paint.Align.RIGHT);
    }

    public FontStyle left() {
        return align(Paint.Align.RIGHT);
    }

    public void dispose() {
        mPaint = null;
    }

    protected Paint generatePaint() {
        if (!mModified && mPaint != null) {
            return mPaint;
        }

        if (mPaint == null) {
            mPaint = new Paint();
        }

        if (mFont != null) {
            mPaint.setTypeface(mFont);
        } else if (sFont != null) {
            mPaint.setTypeface(sFont);
        }

        if (mSize > 0) {
            mPaint.setTextSize(mSize);
        } else {
            mPaint.setTextSize(sSize);
        }

        if (mColor != null) {
            mPaint.setColor(mColor);
        } else {
            mPaint.setColor(sColor);
        }

        if (mAlpha > -1) {
            mPaint.setAlpha(mAlpha);
        } else {
            mPaint.setAlpha(sAlpha);
        }

        if (mAntiAlias != null) {
            mPaint.setAntiAlias(mAntiAlias);
        } else {
            mPaint.setAntiAlias(sAntiAlias);
        }

        if (mAlign != null) {
            mPaint.setTextAlign(mAlign);
        } else {
            mPaint.setTextAlign(sAlign);
        }

        mModified = false;
        return mPaint;
    }
}
