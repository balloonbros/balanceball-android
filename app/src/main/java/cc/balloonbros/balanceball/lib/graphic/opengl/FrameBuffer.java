package cc.balloonbros.balanceball.lib.graphic.opengl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import cc.balloonbros.balanceball.R;
import cc.balloonbros.balanceball.lib.CurrentGame;
import cc.balloonbros.balanceball.lib.display.GameDisplay;

public class FrameBuffer {
    /** Frame buffer id. */
    private int mFrameBufferId = 0;
    /** Render buffer id. */
    private int mRenderBufferId = 0;
    /** Texture id. */
    private int mTextureId = 0;

    /** The texture of the frame buffer. */
    private Texture mTexture;

    /** Frame buffer width. */
    private int mWidth;
    /** Frame buffer height. */
    private int mHeight;

    /**
     * Create a new frame buffer by specified size.
     * @param width The frame buffer width.
     * @param height The frame buffer height.
     */
    public void setup(int width, int height) {
        // At first, release buffers.
        release();

        mWidth  = width;
        mHeight = height;

        // Create a temporary buffer.
        int[] buffer = new int[1];

        GLES20.glGenFramebuffers(buffer.length, buffer, 0);
        mFrameBufferId = buffer[0];
        GLES20.glGenRenderbuffers(buffer.length, buffer, 0);
        mRenderBufferId = buffer[0];
        GLES20.glGenTextures(buffer.length, buffer, 0);
        mTextureId = buffer[0];

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBufferId);

        // Create the off screen position framebuffer texture target.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, mWidth, mHeight, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, mTextureId, 0);

        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, mRenderBufferId);
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, mWidth, mHeight);
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, mRenderBufferId);

        // Create and bind a new frame buffer.

        // Create and bind a new render buffer.

        // Attache the texture as the frame buffer's attachment.

        // ToDo: Check the status whether if it is GLES20.GL_FRAMEBUFFER_COMPLETE.
        final int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
        //if (status != GLES20.GL_FRAMEBUFFER_COMPLETE) {

        mTexture = Texture.from(mTextureId, mWidth, mHeight);
    }

    /**
     * Release buffers.
     */
    public void release() {
        if (mTextureId != 0) {
            GLES20.glDeleteTextures(1, new int[] { mTextureId }, 0);
            mTextureId = 0;
        }

        if (mRenderBufferId != 0) {
            GLES20.glDeleteRenderbuffers(1, new int[] { mRenderBufferId }, 0);
            mRenderBufferId = 0;
        }

        if (mFrameBufferId != 0) {
            GLES20.glDeleteFramebuffers(1, new int[] { mFrameBufferId }, 0);
            mFrameBufferId = 0;
        }
    }

    /**
     * Get the width of the frame buffer.
     * @return width.
     */
    public int getWidth() {
        return mWidth;
    }

    /**
     * Get the height of the frame buffer.
     * @return height.
     */
    public int getHeight() {
        return mHeight;
    }

    /**
     * Enable this frame buffer.
     */
    public void begin() {
        bind();

        GLES20.glViewport(0, 0, mWidth, mHeight);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }

    public void bind() {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBufferId);
    }

    /**
     * Disable this frame buffer.
     */
    public void end() {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

    public void flip() {
        mTexture.draw(0, mHeight, 0, mWidth, -mHeight);
    }
}
