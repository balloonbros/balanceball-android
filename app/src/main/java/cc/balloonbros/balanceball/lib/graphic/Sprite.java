package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.CurrentGame;
import cc.balloonbros.balanceball.lib.GameRenderer;
import cc.balloonbros.balanceball.lib.riGraphicTools;

public class Sprite {
    private FloatBuffer uvBuffer;
    private float[] mtrxModel = new float[16];

    private int mWidth;
    private int mHeight;

    public Sprite() {
        // Create our UV coordinates.
        float[] uvs = new float[] {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };

        // Create a texture buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        // Generate a texture
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);

        // Bind a texture to the texturename.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        // Set wrapping mode
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        // Retrieve our image from resources.
        Bitmap bmp = BitmapFactory.decodeResource(CurrentGame.get().getResources(), R.drawable.launch_logo8);
        mWidth  = bmp.getWidth();
        mHeight = bmp.getHeight();

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

        bmp.recycle();
    }

    public void draw() {
        int modelHandle = GLES20.glGetUniformLocation(riGraphicTools.sp_Image, "uMMatrix");
        Matrix.setIdentityM(mtrxModel, 0);
        Matrix.scaleM(mtrxModel, 0, (float)mWidth, (float)mHeight, 1f);
        //Matrix.translateM(mtrxModel, 0, (960f / 1920f) * ssu, 0f, 0f);
        GLES20.glUniformMatrix4fv(modelHandle, 1, false, mtrxModel, 0);

        int textureCoordinatesLocation = GLES20.glGetAttribLocation(riGraphicTools.sp_Image, "a_texCoord");
        GLES20.glEnableVertexAttribArray(textureCoordinatesLocation);
        GLES20.glVertexAttribPointer(textureCoordinatesLocation, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

        int mSamplerLoc = GLES20.glGetUniformLocation(riGraphicTools.sp_Image, "s_texture");
        GLES20.glUniform1i(mSamplerLoc, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, GameRenderer.indices.length, GLES20.GL_UNSIGNED_SHORT, GameRenderer.drawListBuffer);

        GLES20.glDisableVertexAttribArray(textureCoordinatesLocation);
    }
}
