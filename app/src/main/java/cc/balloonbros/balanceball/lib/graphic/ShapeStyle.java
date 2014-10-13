package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Color;
import android.graphics.Paint;

public class ShapeStyle {
    private boolean mModified = false;
    private Paint mPaint = null;
    private Integer mColor = null;
    private Boolean mAntiAlias = null;
    private Paint.Style mStyle = null;
    private Integer mAlpha = null;

    private static ShapeStyle sDefault = null;
    private static int sColor = Color.BLACK;
    private static boolean sAntiAlias = true;
    private static Paint.Style sStyle = Paint.Style.FILL;
    private static int sAlpha = 0xff;

    public static void setDefaultColor(int color) {
        sColor = color;
        sDefault = null;
    }

    public static void setDefaultAntiAlias(boolean antiAlias) {
        sAntiAlias = antiAlias;
        sDefault = null;
    }

    public static void setDefaultStyle(Paint.Style style) {
        sStyle = style;
        sDefault = null;
    }

    public static void setDefaultAlpha(int alpha) {
        sAlpha = alpha;
        sDefault = null;
    }

    public static ShapeStyle getDefault() {
        if (sDefault != null) {
            return sDefault;
        }

        ShapeStyle style = new ShapeStyle();

        style.antiAlias(sAntiAlias);
        style.color(sColor);
        if (sStyle != null) {
            style.style(sStyle);
        }

        sDefault = style;
        return style;
    }

    public ShapeStyle color(int color) {
        if (mColor == null || mColor != color) {
            mModified = true;
            mColor = color;
        }
        return this;
    }

    public ShapeStyle antiAlias(boolean antiAlias) {
        if (mAntiAlias == null || mAntiAlias != antiAlias) {
            mModified = true;
            mAntiAlias = antiAlias;
        }
        return this;
    }

    public ShapeStyle alpha(int alpha) {
        if (mAlpha == null || mAlpha != alpha) {
            mModified = true;
            mAlpha = alpha;
        }
        return this;
    }

    public ShapeStyle style(Paint.Style style) {
        if (mStyle == null || mStyle != style) {
            mModified = true;
            mStyle = style;
        }
        return this;
    }

    public ShapeStyle stroke() {
        return style(Paint.Style.STROKE);
    }

    public ShapeStyle fill() {
        return style(Paint.Style.FILL);
    }

    public ShapeStyle fillAndStroke() {
        return style(Paint.Style.FILL_AND_STROKE);
    }

    protected Paint generatePaint() {
        if (!mModified && mPaint != null) {
            return mPaint;
        }

        if (mPaint == null) {
            mPaint = new Paint();
        }

        if (mColor != null) {
            mPaint.setColor(mColor);
        } else {
            mPaint.setColor(sColor);
        }

        if (mAntiAlias != null) {
            mPaint.setAntiAlias(mAntiAlias);
        } else {
            mPaint.setAntiAlias(sAntiAlias);
        }

        if (mStyle != null) {
            mPaint.setStyle(mStyle);
        } else {
            mPaint.setStyle(sStyle);
        }

        if (mAlpha != null) {
            mPaint.setAlpha(mAlpha);
        } else {
            mPaint.setAlpha(sAlpha);
        }

        mModified = false;
        return mPaint;
    }
}
