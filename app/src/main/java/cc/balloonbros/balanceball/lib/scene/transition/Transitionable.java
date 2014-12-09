package cc.balloonbros.balanceball.lib.scene.transition;

import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.graphic.opengl.FrameBuffer;

public interface Transitionable {
    public Surface transit(Surface currentSurface, Surface nextSurface);
    public FrameBuffer transit(FrameBuffer currentFrameBuffer, FrameBuffer nextFrameBuffer);
    public boolean isDrawingCurrentScene();
    public boolean isDrawingNextScene();
    public boolean completeTransition();
}
