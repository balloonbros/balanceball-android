package cc.balloonbros.balanceball.task.play;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Rectangle;
import cc.balloonbros.balanceball.lib.graphic.ShapeStyle;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

public class Result extends AbstractTask implements Drawable {
    private DrawString mResult = new DrawString("Result");
    private DrawString mShare = new DrawString("スコアをシェアする");
    private Rectangle mResultShape = new Rectangle(400, 100);
    private Rectangle mShadow = new Rectangle();

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_result));

        mResult.setStyle(getFontStyle("result")).setPosition(getDisplaySize().x / 2, 100);
        mShare .setStyle(getFontStyle("share")) .setPosition(400, 250);

        ShapeStyle white = new ShapeStyle().color(Color.WHITE).stroke();
        ShapeStyle black = new ShapeStyle().color(Color.BLACK).alpha(0xaa);
        mResultShape.setStyle(white);
        mShadow.setStyle(black);

        mShadow.changeSize(getDisplaySize().x, getDisplaySize().y);
    }

    @Override
    public void onDraw(Canvas canvas, Surface surface) {
        surface.draw(mShadow);
        surface.draw(mResult);
        surface.draw(mShare);
        surface.draw(mResultShape);
    }
}
