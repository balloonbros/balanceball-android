package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Style;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib._;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

public class Result extends AbstractTask implements Drawable {
    private DrawString mResult = new DrawString("Result");
    private DrawString mShare = new DrawString("スコアをシェアする");
    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_result));

        Style resultStyle = Style.getDefault().color(_c(R.color.result_color)).size(50F);
        mResult.setStyle(resultStyle);
        Style shareStyle = Style.from(resultStyle).size(30F);
        mShare.setStyle(shareStyle);

        mPaint.setColor(Color.BLACK & 0xaa << 24);
        mPaint2.setColor(Color.WHITE);
        mPaint2.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDraw(Canvas canvas, Surface surface) {
        canvas.drawRect(0, 0, getDisplaySize().x, getDisplaySize().y, mPaint);
        canvas.drawRect(200, 200, 600, 300, mPaint2);
        canvas.drawRect(200, 300, 400, 400, mPaint2);
        canvas.drawRect(400, 300, 600, 400, mPaint2);
        //canvas.drawColor(Color.BLACK & 0xaa << 24);
        surface.draw(mResult, getDisplaySize().x / 2, 100);
        surface.draw(mShare, 400, 250);
    }
}
