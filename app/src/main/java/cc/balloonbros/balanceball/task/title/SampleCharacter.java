package cc.balloonbros.balanceball.task.title;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.graphic.Animation;
import cc.balloonbros.balanceball.lib.graphic.Sprite;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.task.Drawable;
import cc.balloonbros.balanceball.lib.task.basic.PositionableTask;

public class SampleCharacter extends PositionableTask implements Drawable {
    private Animation mAnimation;

    @Override
    public void onRegister() {
        mAnimation = Sprite.from(getImage(R.drawable.character)).slice(48, 48).toAnimation(this, 1000);
        mAnimation.useUnits(0, 4, 8, 12);
        mAnimation.moveToCenter();
    }

    @Override
    public void onDraw(Surface surface) {
        surface.draw(mAnimation);
    }
}
