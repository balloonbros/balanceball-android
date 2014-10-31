package cc.balloonbros.balanceball.lib.scene.transition;

import cc.balloonbros.balanceball.lib.graphic.Surface;

public interface Transitionable {
    public Surface transit(Surface currentSurface, Surface nextSurface);
    public boolean isDrawingCurrentScene();
    public boolean isDrawingNextScene();
    public boolean completeTransition();
}
