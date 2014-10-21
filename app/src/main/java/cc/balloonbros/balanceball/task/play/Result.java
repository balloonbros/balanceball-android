package cc.balloonbros.balanceball.task.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.shape.Rectangle;
import cc.balloonbros.balanceball.lib.graphic.style.ShapeStyle;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

public class Result extends AbstractTask implements Drawable {
    private DrawString mResult = new DrawString("Result");
    private DrawString mShare = new DrawString("スコアをシェアする");
    private Rectangle mResultShape = new Rectangle(400, 100);
    private Rectangle mShadow = new Rectangle();
    private Bitmap mShape;

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

        GradientDrawable shape = (GradientDrawable) getGame().getResources().getDrawable(R.drawable.shape1);
        mShape = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mShape);
        shape.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        shape.draw(canvas);
    }

    @Override
    public void onDraw(Surface surface) {
        surface.draw(mShadow);

        surface.getCanvas().drawBitmap(mShape, 0, 0, null);
        /*
        surface.draw(mResult);
        surface.draw(mShare);
        surface.draw(mResultShape);
        */
    }
}
