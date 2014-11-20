package cc.balloonbros.balanceball.lib;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import cc.balloonbros.balanceball.lib.task.extender.Touchable;

/**
 * A surface view for OpenGL.
 * The view uses OpenGL ES version 2.0.
 */
public class GameSurfaceView extends GLSurfaceView implements View.OnTouchListener {
    private static final int OPEN_GL_ES_VERSION = 2;
    private ArrayList<Touchable> mTouchListeners = new ArrayList<Touchable>();

    /**
     * Constructor.
     * @param game The game that renders to this surface view.
     */
    public GameSurfaceView(GameMain game) {
        super(game.getContext());

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(OPEN_GL_ES_VERSION);

        // Create a renderer for OpenGL ES and set it to this view.
        Renderer renderer = new GameRenderer(game);
        setRenderer(renderer);

        // Set the render mode to continuously.
        // This mode creates the game loop.
        setRenderMode(RENDERMODE_CONTINUOUSLY);

        setOnTouchListener(this);
    }

    /**
     * タッチイベントを処理するタスクを追加
     * @param touchable 追加するタスク
     */
    public void addTouchable(Touchable touchable) {
        mTouchListeners.add(touchable);
    }

    /**
     * 不要なタスクをタッチイベントのリストから削除する
     * @param touchable 削除するタスク
     */
    public void removeTouchable(Touchable touchable) {
        mTouchListeners.remove(touchable);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        for (int i = 0; i < mTouchListeners.size(); i++) {
            mTouchListeners.get(i).onTouch(motionEvent);
        }

        return false;
    }
}
