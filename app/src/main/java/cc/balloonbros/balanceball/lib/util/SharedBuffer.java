package cc.balloonbros.balanceball.lib.util;

import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class SharedBuffer {
    private static final float[] sIdentitySquareVertices = VertexUtils.generateRectangle(0, 0, 0, 1, 1);
    private static final FloatBuffer sIdentitySquareBuffer = BufferUtil.convert(sIdentitySquareVertices);

    private static final float[] sUnitUv = new float[] { 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f };
    private static final FloatBuffer sUnitUvBuffer = BufferUtil.convert(sUnitUv);

    private static final short[] sSquareIndices = new short[] { 1, 2, 0, 3 };
    private static final ShortBuffer sSquareIndicesBuffer = BufferUtil.convert(sSquareIndices);

    private static final float[] IDENTITY_MATRIX = new float[16];

    public static FloatBuffer identitySquare() {
        return sIdentitySquareBuffer;
    }

    public static FloatBuffer unitUv() {
        return sUnitUvBuffer;
    }

    public static ShortBuffer squareIndices() {
        return sSquareIndicesBuffer;
    }

    public static float[] identityMatrix() {
        Matrix.setIdentityM(IDENTITY_MATRIX, 0);
        return IDENTITY_MATRIX;
    }
}
