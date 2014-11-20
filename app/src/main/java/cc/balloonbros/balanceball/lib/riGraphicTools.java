package cc.balloonbros.balanceball.lib;

import android.opengl.GLES20;

public class riGraphicTools {
    public static int sp_SolidColor;
    public static int sp_Image;

    public static final String vs_SolidColor =
        "uniform mat4 uMVPMatrix;" +
        "attribute vec4 vPosition;" +
        "attribute vec4 vColor;" +
        "void main() {" +
        "  gl_Position = uMVPMatrix * vPosition;" +
        "  gl_FrontColor = vColor;" +
        "  gl_FrontColor.a = 1.0;" +
        "}";

    public static final String fs_SolidColor =
        "precision mediump float;" +
        "void main() {" +
        "  gl_FragColor = gl_Color;" +
        "}";

    public static final String vs_Image =
        "uniform mat4 uVPMatrix;" +
        "uniform mat4 uMMatrix;" +
        "attribute vec4 vPosition;" +
        "attribute vec2 a_texCoord;" +
        "varying vec2 v_texCoord;" +
        "void main() {" +
        "  gl_Position = uVPMatrix * uMMatrix * vPosition;" +
        "  v_texCoord = a_texCoord;" +
        "}";

    public static final String fs_Image =
        "precision mediump float;" +
        "varying vec2 v_texCoord;" +
        "uniform sampler2D s_texture;" +
        "void main() {" +
        "  gl_FragColor = texture2D(s_texture, v_texCoord);" +
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
