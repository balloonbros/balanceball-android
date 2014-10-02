package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Style {
    private boolean mModified = false;
    private Paint mPaint = null;
    private Typeface mFont = null;
    private float mSize = 0;
    private int mColor = Color.BLACK;
    private int mAlpha = 0xff;
    private boolean mAntiAlias = true;
    private Paint.Align mAlign = Paint.Align.LEFT;
    private boolean mIsDefault = false;

    private static Style sDefault = null;
    private static Paint sPaint = new Paint();
    private static Typeface sFont = null;
    private static float sSize = 0;
    private static int sColor = Color.BLACK;
    private static int sAlpha = 0xff;
    private static boolean sAntiAlias = true;
    private static Paint.Align sAlign = Paint.Align.LEFT;

    private static void resetDefault() {
        sDefault = null;
        sPaint   = null;
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

    public static Style getDefault() {
        if (sDefault != null) {
            return sDefault;
        }

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
        style.mIsDefault = true;

        sDefault = style;
        return style;
    }

    public static Style from(Style template) {
        Style style = new Style();

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
        return mColor;
    }

    public int alpha() {
        return mAlpha;
    }

    public boolean antiAlias() {
        return mAntiAlias;
    }

    public Paint.Align align() {
        return mAlign;
    }

    public Style font(Typeface font) {
        if (font != null && (mFont == null || mFont != font)) {
            mModified = true;
            mFont = font;
        }
        return cloneIfDefault();
    }

    public Style size(float size) {
        if (mSize != size) {
            mModified = true;
            mSize = size;
        }
        return cloneIfDefault();
    }

    public Style color(int color) {
        if (mColor != color) {
            mModified = true;
            mColor = color;
        }
        return cloneIfDefault();
    }

    public Style alpha(int alpha) {
        if (mAlpha != alpha) {
            mModified = true;
            mAlpha = alpha;
        }
        return cloneIfDefault();
    }

    public Style antiAlias(boolean antiAlias) {
        if (mAntiAlias != antiAlias) {
            mModified = true;
            mAntiAlias = antiAlias;
        }
        return cloneIfDefault();
    }

    public Style align(Paint.Align align) {
        if (mAlign != align) {
            mModified = true;
            mAlign = align;
        }
        return cloneIfDefault();
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

    private Style cloneIfDefault() {
        if (mIsDefault) {
            mIsDefault = false;
            return Style.from(this);
        }

        return this;
    }

    protected Paint generatePaint() {
        if (mIsDefault && sPaint != null) {
            return sPaint;
        }

        if (!mModified && mPaint != null) {
            return mPaint;
        }

        Paint paint;
        if (mIsDefault) {
            if (sPaint == null) {
                sPaint = new Paint();
            }
            paint = sPaint;
        } else {
            if (mPaint == null) {
                mPaint = new Paint();
            }
            paint = mPaint;
        }

        if (!mModified) {
            return paint;
        }

        if (mFont != null) {
            paint.setTypeface(mFont);
        }
        if (mSize > 0) {
            paint.setTextSize(mSize);
        }
        paint.setColor(mColor);
        paint.setAlpha(mAlpha);
        paint.setAntiAlias(mAntiAlias);
        paint.setTextAlign(mAlign);

        mModified = false;
        return paint;
    }
}
