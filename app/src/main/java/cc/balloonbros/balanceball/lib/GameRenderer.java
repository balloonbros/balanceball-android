package cc.balloonbros.balanceball.lib;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameRenderer implements GLSurfaceView.Renderer{
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    float mScreenWidth = 1280;
    float mScreenHeight = 768;

    private Context mContext;
    private long mLastTime;

    public static float vertices[];
    public static short indices[];
    public FloatBuffer vertexBuffer;
    public ShortBuffer drawListBuffer;

    /**
     * Constructor.
     * @param context Context.
     */
    public GameRenderer(Context context) {
        mContext = context;
        mLastTime = System.currentTimeMillis() + 100;
    }

    public void onPause() {

    }

    public void onResume() {
        mLastTime = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig) {
        // Create a triangle vertex.
        SetupTriangle();

        // Set the background color to black.
        GLES20.glClearColor(0f, 0f, 0f, 0f);

        // Create the shader.
        int vertexShader   = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER,   riGraphicTools.vs_SolidColor);
        int fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_SolidColor);

        // Create the empty OpenGL ES program.
        riGraphicTools.sp_SolidColor = GLES20.glCreateProgram();

        // Add the vertex shader and fragment shader to the program.
        GLES20.glAttachShader(riGraphicTools.sp_SolidColor, vertexShader);
        GLES20.glAttachShader(riGraphicTools.sp_SolidColor, fragmentShader);

        // Make the program executable.
        GLES20.glLinkProgram(riGraphicTools.sp_SolidColor);

        // Set the our shader program.
        GLES20.glUseProgram(riGraphicTools.sp_SolidColor);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // We need to know the current display size.
        mScreenWidth  = width;
        mScreenHeight = height;

        // Redo the Viewport, making it fullscreen.
        GLES20.glViewport(0, 0, (int) mScreenHeight, (int) mScreenHeight);

        // Clear matrices.
        for (int i = 0; i < 16; i++) {
            mtrxProjection[i] = 0f;
            mtrxView[i] = 0f;
            mtrxProjectionAndView[i] = 0f;
        }

        // Setup screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, mScreenWidth, 0f, mScreenHeight, 0f, 50);

        // Set the camera position (View matrix).
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f);

        // Calculate the projection and view transformation.
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        long now = System.currentTimeMillis();
        if (mLastTime > now) {
            return;
        }

        // Get the amount of time per frame.
        long elapsed = now - mLastTime;

        Render(mtrxProjectionAndView);

        // Save the current time to calculate the amount of time per frame.
        mLastTime = now;
    }

    public void Render(float m[]) {
        // clear Screen and Depth Buffer, we have set the clear color as black.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Get the handle to vertex shader's vPosition member.
        int mPositionHandle = GLES20.glGetAttribLocation(riGraphicTools.sp_SolidColor, "vPosition");

        // Enable generic vertex attribute array.
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data.
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        // Get handle to shape's transformation matrix.
        int mtrxhandle = GLES20.glGetUniformLocation(riGraphicTools.sp_SolidColor, "uMVPMatrix");

        // Apply the projection and view transformation.
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

        // Draw the triangle.
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable the vertex array.
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public void SetupTriangle() {
        vertices = new float[] {
            10f,  200f, 0f,
            10f,  100f, 0f,
            100f, 100f, 0f,
        };

        indices = new short[] { 0, 1, 2 };

        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // Initialize byte buffer for the draw list.
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = bb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
    }
}
