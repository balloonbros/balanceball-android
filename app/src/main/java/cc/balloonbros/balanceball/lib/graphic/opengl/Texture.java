package cc.balloonbros.balanceball.lib.graphic.opengl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import cc.balloonbros.balanceball.lib.CurrentGame;
import cc.balloonbros.balanceball.lib.graphic.Shader;
import cc.balloonbros.balanceball.lib.graphic.Sprite;
import cc.balloonbros.balanceball.lib.riGraphicTools;
import cc.balloonbros.balanceball.lib.util.GameUtil;
import cc.balloonbros.balanceball.lib.util.SharedBuffer;

/**
 * Representation of the texture object.
 */
public class Texture {
    private static Shader sShader;
    private static float[] sViewProjectionMatrix;

    private int mTextureId;
    private boolean mIsLoad = false;
    private int mWidth;
    private int mHeight;

    public Texture() { }

    public Texture(int resourceId) {
        load(GLES20.GL_TEXTURE0, resourceId);
    }

    public Texture(int textureUnit, int resourceId) {
        load(textureUnit, resourceId);
    }

    public static void initialize() {
        sShader = Shader.create(riGraphicTools.vs_Image, riGraphicTools.fs_Image);
    }

    public Sprite toSprite() {
        return new Sprite(this);
    }

    public void load(int resourceId) {
        load(GLES20.GL_TEXTURE0, resourceId);
    }

    public void load(int textureUnit, int resourceId) {
        // Generate a new texture.
        int[] textureId = new int[1];
        GLES20.glGenTextures(textureId.length, textureId, 0);
        mTextureId = textureId[0];

        // Bind the texture to the texture unit 0.
        GLES20.glActiveTexture(textureUnit);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        /*
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        */

        // Retrieve an image from resources.
        Resources res = CurrentGame.get().getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, resourceId);
        if (bmp == null) {
            bmp = GameUtil.convertDrawableToBitmap(res.getDrawable(resourceId));
        }

        // Stored width and height from bitmap resource to this class in order to be speedup.
        mWidth  = bmp.getWidth();
        mHeight = bmp.getHeight();

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

        bmp.recycle();

        mIsLoad = true;
    }

    public static Texture from(int textureId, int width, int height) {
        Texture texture = new Texture();
        texture.mTextureId = textureId;
        texture.mWidth = width;
        texture.mHeight = height;
        texture.mIsLoad = true;

        return texture;
    }

    public static void start(float[] viewProjectionMatrix) {
        sViewProjectionMatrix = viewProjectionMatrix;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void draw(float x, float y, float z) {
        draw(x, y, z, mWidth, mHeight);
    }

    public void draw(float x, float y, float z, float width, float height) {
        if (!mIsLoad) {
            return;
        }

        sShader.use();

        int positionLocation    = sShader.getAttributeLocationAndEnable("vPosition");
        int uvLocation          = sShader.getAttributeLocationAndEnable("a_texCoord");
        int viewProjectLocation = sShader.getUniformLocation("uVPMatrix");
        int modelLocation       = sShader.getUniformLocation("uMMatrix");
        int textureLocation     = sShader.getUniformLocation("s_texture");

        float[] modelMatrix = SharedBuffer.identityMatrix();
        Matrix.scaleM(modelMatrix, 0, width, height, 1f);
        Matrix.translateM(modelMatrix, 0, x / width, y / height, z);

        GLES20.glVertexAttribPointer(positionLocation, 3, GLES20.GL_FLOAT, false, 0, SharedBuffer.identitySquare());
        GLES20.glVertexAttribPointer(uvLocation, 2, GLES20.GL_FLOAT, false, 0, SharedBuffer.unitUv());
        GLES20.glUniformMatrix4fv(viewProjectLocation, 1, false, sViewProjectionMatrix, 0);
        GLES20.glUniform1i(textureLocation, 0);
        GLES20.glUniformMatrix4fv(modelLocation, 1, false, modelMatrix, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glDrawElements(
            GLES20.GL_TRIANGLE_STRIP,
            SharedBuffer.squareIndices().capacity(),
            GLES20.GL_UNSIGNED_SHORT,
            SharedBuffer.squareIndices()
        );

        sShader.disableAttribute("vPosition");
        sShader.disableAttribute("a_texCoord");
    }
}
