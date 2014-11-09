package cc.balloonbros.balanceball.lib;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cc.balloonbros.balanceball.lib.task.extender.Touchable;

/**
 * ゲーム描画サーフェイス
 */
public class GameSurfaceView extends GLSurfaceView implements View.OnTouchListener {
    private static final int OPENGL_ES_VERSION = 2;
    private ArrayList<Touchable> mTouchListeners = new ArrayList<Touchable>();

    /** Renderer */
    private Renderer mRenderer;

    /**
     * コンストラクタ
     * @param context このサーフェイスが属するコンテキスト
     */
    public GameSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(OPENGL_ES_VERSION);

        Renderer renderer = new GameRenderer(context);
        setRenderer(renderer);

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
