package cc.balloonbros.balanceball.lib.graphic.opengl;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import cc.balloonbros.balanceball.lib.graphic.Shader;
import cc.balloonbros.balanceball.lib.riGraphicTools;
import cc.balloonbros.balanceball.lib.util.BufferUtil;
import cc.balloonbros.balanceball.lib.util.SharedBuffer;

/**
 * This class is representation of a polygon.
 */
public class Polygon {
    private static Shader sShader;
    private static float[] sViewProjectionMatrix;

    private FloatBuffer mVertexBuffer;
    private ShortBuffer mIndexBuffer;

    public static void initialize() {
        sShader = Shader.create(riGraphicTools.vs_SolidColor, riGraphicTools.fs_SolidColor);
    }

    public static void start(float[] viewProjectionMatrix) {
        sViewProjectionMatrix = viewProjectionMatrix;
    }

    public Polygon(float[] vertices, short[] indices) {
        // Convert to the buffer.
        mVertexBuffer = BufferUtil.convert(vertices);
        mIndexBuffer  = BufferUtil.convert(indices);
    }

    public Polygon(FloatBuffer vertices, ShortBuffer indices) {
        mVertexBuffer = vertices;
        mIndexBuffer  = indices;
    }

    public void draw(float x, float y, float z, FloatBuffer color) {
        draw(x, y, z, color, 1, 1);
    }

    public void draw(float x, float y, float z, FloatBuffer color, float scaleX, float scaleY) {
        sShader.use();

        int positionLocation    = sShader.getAttributeLocationAndEnable("vPosition");
        int colorLocation       = sShader.getAttributeLocationAndEnable("aColor");
        int viewProjectLocation = sShader.getUniformLocation("uVPMatrix");
        int modelLocation       = sShader.getUniformLocation("uMMatrix");

        float[] modelMatrix = SharedBuffer.identityMatrix();
        Matrix.scaleM(modelMatrix, 0, scaleX, scaleY, 1f);
        Matrix.translateM(modelMatrix, 0, x / scaleX, y / scaleY, z);

        GLES20.glVertexAttribPointer(positionLocation, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        GLES20.glVertexAttribPointer(colorLocation, 4, GLES20.GL_FLOAT, false, 0, color);
        GLES20.glUniformMatrix4fv(viewProjectLocation, 1, false, sViewProjectionMatrix, 0);
        GLES20.glUniformMatrix4fv(modelLocation, 1, false, modelMatrix, 0);
        //GLES20.glUniform4fv(colorLocation, 1, color, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, mIndexBuffer.capacity(), GLES20.GL_UNSIGNED_SHORT, mIndexBuffer);

        sShader.disableAttribute("vPosition");
        sShader.disableAttribute("aColor");
    }
}
