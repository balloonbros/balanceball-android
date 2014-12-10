package cc.balloonbros.balanceball.lib;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import cc.balloonbros.balanceball.lib.task.extender.Touchable;

/**
 * A surface view for OpenGL.
 * The view uses OpenGL ES version 2.0.
 */
@SuppressLint("ViewConstructor")
public class GameSurfaceView extends GLSurfaceView implements View.OnTouchListener {
    /** We use OpenGL ES Version 2 */
    private static final int OPENGL_ES_VERSION = 2;
    /** Renderer */
    private GameRenderer mRenderer;

    /** Touch listener list */
    private ArrayList<Touchable> mTouchListeners = new ArrayList<Touchable>();
    /**
     * We must bubble a touch event on the OpenGL thread because if
     * a task that received the touch event can't draw any object using
     * OpenGL command.
     * So, to avoid it, we define a runnable interface to bubble the touch
     * event to tha task with queueEvent method.
     */
    private MotionEvent mMotionEvent;
    private Runnable mTouchEventBubbleRunner = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < mTouchListeners.size(); i++) {
                mTouchListeners.get(i).onTouch(mMotionEvent);
            }
        }
    };

    /**
     * Constructor.
     * @param game The game that renders to this surface view.
     */
    public GameSurfaceView(GameMain game) {
        super(game.getContext());

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(OPENGL_ES_VERSION);

        // Create a renderer for OpenGL ES and set it to this view.
        mRenderer = new GameRenderer(game);
        setRenderer(mRenderer);

        // Set the render mode to continuously.
        // This mode creates the game loop.
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRenderer.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRenderer.onResume();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnTouchListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setOnTouchListener(null);
    }

    /**
     * Add a touchable task to the listener list.
     * @param touchable Target task.
     */
    public void addTouchable(Touchable touchable) {
        mTouchListeners.add(touchable);
    }

    /**
     * Remove a touchable task from the listener list.
     * @param touchable Removed task from the list.
     */
    public void removeTouchable(Touchable touchable) {
        mTouchListeners.remove(touchable);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mMotionEvent = motionEvent;
        queueEvent(mTouchEventBubbleRunner);

        return false;
    }
}
