package cc.balloonbros.balanceball.lib.graphic;

import android.opengl.GLES20;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * This class compiles shader source code.
 * Also can send data to the shader program.
 */
public class Shader {
    /** The shader program id */
    private int mProgramId;

    private Map<String, Integer> mLocations = new HashMap<String, Integer>();

    /**
     * Create a new shader from a source code as a string.
     * @param vertexShaderSource A source code of the vertex shader.
     * @param fragmentShaderSource A source code of the fragment shader.
     * @return A new shader.
     */
    public static Shader create(String vertexShaderSource, String fragmentShaderSource) {
        Shader shader = new Shader();

        // Create the vertex shader and fragment shader.
        int vertexShader   = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        // Add the source code to the shader and compile it.
        GLES20.glShaderSource(vertexShader, vertexShaderSource);
        GLES20.glShaderSource(fragmentShader, fragmentShaderSource);
        GLES20.glCompileShader(vertexShader);
        GLES20.glCompileShader(fragmentShader);

        // Create the empty program and store the program id in this class.
        shader.mProgramId = GLES20.glCreateProgram();

        // Add the shaders to the program.
        GLES20.glAttachShader(shader.mProgramId, vertexShader);
        GLES20.glAttachShader(shader.mProgramId, fragmentShader);

        // Creates OpenGL ES program executable.
        GLES20.glLinkProgram(shader.mProgramId);

        return shader;
    }

    public void use() {
        GLES20.glUseProgram(mProgramId);
    }

    public int getAttributeLocationAndEnable(String name) {
        int location = getAttributeLocation(name);
        GLES20.glEnableVertexAttribArray(location);
        return location;
    }

    public int getAttributeLocation(String name) {
        if (mLocations.containsKey(name)) {
            return mLocations.get(name);
        }

        int location = GLES20.glGetAttribLocation(mProgramId, name);
        mLocations.put(name, location);
        return location;
    }

    public int getUniformLocation(String name) {
        if (mLocations.containsKey(name)) {
            return mLocations.get(name);
        }

        int location = GLES20.glGetUniformLocation(mProgramId, name);
        mLocations.put(name, location);
        return location;
    }

    public void disableAttribute(String name) {
        if (mLocations.containsKey(name)) {
            GLES20.glDisableVertexAttribArray(mLocations.get(name));
        }
    }
}
