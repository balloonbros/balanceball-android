package cc.balloonbros.balanceball.lib;

public class riGraphicTools {
    public static final String vs_SolidColor =
        "uniform mat4 uVPMatrix;" +
        "uniform mat4 uMMatrix;" +
        "attribute vec4 vPosition;" +
        "attribute vec4 aColor;" +
        "varying vec4 vColor;" +
        "void main() {" +
        "  gl_Position = uVPMatrix * uMMatrix * vPosition;" +
        "  vColor = aColor;" +
        "}";

    public static final String fs_SolidColor =
        "precision mediump float;" +
        "varying vec4 vColor;" +
        "void main() {" +
        "  gl_FragColor = vColor;" +
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
}
