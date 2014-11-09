package cc.balloonbros.balanceball.lib;

import android.opengl.GLES20;

public class riGraphicTools {
    public static int sp_SolidColor;

    public static final String vs_SolidColor =
        "uniform mat4 uMVPMatrix;" +
        "attribute vec4 vPosition;" +
        "void main() {" +
        "  gl_Position = uMVPMatrix * vPosition;" +
        "}";

    public static final String fs_SolidColor =
        "precision mediump float;" +
        "void main() {" +
        "  gl_FragColor = vec4(0.5, 0, 0, 1);" +
        "}";

    public static int loadShader(int type, String shaderCode) {
        // Create a shader type.
        // Vertex shader (GLES20.GL_VERTEX_SHADER) or Fragment shader (GLES20.GL_FRAGMENT_SHADER).
        int shader = GLES20.glCreateShader(type);

        // Add the source code to the shader and compile it.
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
