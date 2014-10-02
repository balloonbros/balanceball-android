package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Surface {
    private Canvas mCanvas;
    private Point mDefaultPosition = null;

    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
    }

    public void setDefaultDrawingPosition(Point defaultPosition) {
        mDefaultPosition = defaultPosition;
    }

    public void draw(DrawString text, float x, float y) {
        Paint paint = text.getStyle().generatePaint();

        if (text.needsConcatenate()) {
            mCanvas.drawText(text.getChars(), 0, text.getLength(), x, y, paint);
        } else {
            mCanvas.drawText(text.getFirstString(), x, y, paint);
        }
    }

    public void draw(DrawString text) {
        int x = 0;
        int y = 0;
        if (mDefaultPosition != null) {
            x = mDefaultPosition.x;
            y = mDefaultPosition.y;
        }

        draw(text, x, y);
    }
}