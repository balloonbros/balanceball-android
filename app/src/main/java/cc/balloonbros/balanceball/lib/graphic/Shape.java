package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import cc.balloonbros.balanceball.lib.graphic.style.ShapeStyle;

abstract public class Shape extends DrawObject {
    private ShapeStyle mStyle = null;

    public Shape setStyle(ShapeStyle style) {
        mStyle = style;
        return this;
    }

    public ShapeStyle getStyle() {
        return mStyle;
    }

    protected void drawToCanvas(Canvas canvas) {
        Paint paint;
        Point position = getPosition();
        ShapeStyle style = getStyle();

        if (style != null) {
            paint = style.generatePaint();
        } else {
            paint = ShapeStyle.getDefault().generatePaint();
        }

        draw(position.x, position.y, paint, canvas);
    }

    abstract protected void draw(int x, int y, Paint paint, Canvas canvas);
}
