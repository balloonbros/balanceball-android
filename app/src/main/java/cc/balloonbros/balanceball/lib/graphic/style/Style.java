package cc.balloonbros.balanceball.lib.graphic.style;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Define styles for drawing text.
 * This class is a wrapper of android.graphic.Paint.
 * You can use the method chain to set each styles and
 * generate paint from an instance of this class.
 */
public class Style {
    /**
     * Style modified flag. Set true when styles has changed, and
     * set false when generate or rebuilt the paint instance.
     * @see Style#generatePaint
     */
    private boolean mModified = false;
    private Paint mPaint = null;
    private Typeface mFont = null;
    private float mSize = 0;
    private Integer mColor = null;
    private int mAlpha = -1;
    private Boolean mAntiAlias = null;
    private Paint.Align mAlign = null;

    private static Style sDefault = null;
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

    public Style font(Typeface font) {
        if (font != null && (mFont == null || mFont != font)) {
            mModified = true;
            mFont = font;
        }
        return this;
    }

    public Style size(float size) {
        if (mSize != size) {
            mModified = true;
            mSize = size;
        }
        return this;
    }

    public Style color(int color) {
        if (mColor == null || mColor != color) {
            mModified = true;
            mColor = color;
        }
        return this;
    }

    public Style alpha(int alpha) {
        if (mAlpha != alpha) {
            mModified = true;
            mAlpha = alpha;
        }
        return this;
    }

    public Style antiAlias(boolean antiAlias) {
        if (mAntiAlias == null || mAntiAlias != antiAlias) {
            mModified = true;
            mAntiAlias = antiAlias;
        }
        return this;
    }

    public Style align(Paint.Align align) {
        if (mAlign != align) {
            mModified = true;
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

    public void dispose() {
        mPaint = null;
    }

    /**
     * Generate a Paint instance and return it.
     *
     * When an instance of this class generates a Paint instance by invoking this method,
     * it is cached to mPaint (the field variable of this class). So you can invoke
     * this method any times without thinking about creating a new instance
     * because the instance of this class return immediately the paint instance if there is
     * its cache.
     * But, if you set or change new styles to the instance and invoke generatePaint method again,
     * the cache instance of Paint will be rebuilt and return it.
     *
     * @see Style#mModified
     * @return Generated Paint class instance.
     */
    public Paint generatePaint() {
        // Return immediately the cache instance if there is already the cache and there is no change.
        if (!mModified && mPaint != null) {
            return mPaint;
        }

        // Create a new instance if the paint instance doesn't exist.
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
