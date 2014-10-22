package cc.balloonbros.balanceball.task.play;

import android.graphics.Color;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.DrawString;
import cc.balloonbros.balanceball.lib.graphic.Shape;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.Drawable;

public class Result extends AbstractTask implements Drawable {
    private DrawString mResult = new DrawString("Result");
    private DrawString mShare = new DrawString("スコアをシェアする");
    private Shape mShape = new Shape(R.drawable.shape1);

    @Override
    public void onRegister() {
        super.onRegister();
        setPriority(_i(R.integer.priority_result));

        mResult.setStyle(getFontStyle("result")).setPosition(getDisplaySize().x / 2, 100);
        mShare .setStyle(getFontStyle("share")) .setPosition(400, 250);
    }

    @Override
    public void onDraw(Surface surface) {
        surface.fill(Color.BLACK & (0x88 << 24));

        surface.draw(mShape);
        surface.draw(mResult);
        surface.draw(mShare);
    }
}
