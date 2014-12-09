package cc.balloonbros.balanceball.lib;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;

import cc.balloonbros.balanceball.lib.display.DisplaySize;
import cc.balloonbros.balanceball.lib.display.GameDisplay;
import cc.balloonbros.balanceball.lib.graphic.opengl.FrameBuffer;
import cc.balloonbros.balanceball.lib.graphic.opengl.Polygon;
import cc.balloonbros.balanceball.lib.graphic.opengl.Texture;

public class GameRenderer implements Renderer {
    private final float[] projectionAndViewMatrix = new float[16];
    GameMain mGame;
    long mLastTime;
    public static int sFrameCount = 0;

    public GameRenderer(GameMain game) {
        mGame = game;
        mLastTime = System.currentTimeMillis() + 100;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        long now = System.currentTimeMillis();
        if (mLastTime > now) return;
        long elapsed = now - mLastTime;
        sFrameCount++;

        // Update our example
        if (elapsed > 0) {
            Log.d("fps", String.valueOf(1000 / elapsed));
        } else {
            Log.d("fps", "Infinite");
        }

        if (mGame.canChangeScene()) {
            mGame.updateCurrentScene();
        }

        FrameBuffer frameBuffer;
        if (mGame.whileChangingScene()) {
            frameBuffer = mGame.getSceneChanger().execute();
        } else {
            frameBuffer = mGame.getCurrentScene().execute();
        }

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Redo the Viewport, fix the aspect ratio of the display.
        GameDisplay display = GameDisplay.getInstance();
        float scaledHeight = display.getScaledSize().getHeight();
        float padding = (display.getDeviceDisplaySize().getHeight() - scaledHeight) / 2f;
        GLES20.glViewport(0, (int) padding, display.getDeviceDisplaySize().getWidth(), (int) scaledHeight);

        frameBuffer.flip();

        mLastTime = now;
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the display size.
        GameDisplay display = GameDisplay.getInstance();
        display.updateDeviceDisplaySize(width, height);

        // Prepare arrays to use with calculation of the transformation matrix.
        final float[] projectionMatrix = new float[16];
        final float[] viewMatrix       = new float[16];

        // Calculate the projection and view transformation.
        DisplaySize displaySize = display.getDisplaySize();
        Matrix.orthoM(projectionMatrix, 0, 0f, displaySize.getWidth(), 0f, displaySize.getHeight(), 0f, 2f);
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f);
        Matrix.multiplyMM(projectionAndViewMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        Polygon.start(projectionAndViewMatrix);
        Texture.start(projectionAndViewMatrix);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the clear color to black
        GLES20.glClearColor(0f, 0f, 0f, 1);

        Polygon.initialize();
        Texture.initialize();

        mGame.getGameStartListener().onStart(mGame);
    }
}
