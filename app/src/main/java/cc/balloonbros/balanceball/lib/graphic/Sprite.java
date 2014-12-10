package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Point;

import java.nio.FloatBuffer;

import cc.balloonbros.balanceball.lib.display.DisplaySize;
import cc.balloonbros.balanceball.lib.display.GameDisplay;
import cc.balloonbros.balanceball.lib.graphic.opengl.Texture;

public class Sprite extends DrawObject {
    private FloatBuffer uvBuffer;

    private Texture mTexture;

    public Sprite(Texture texture) {
        mTexture = texture;
    }

    public void draw() {
        Point p = getPosition();
        mTexture.draw(p.x, p.y, getWorldDepth(), mTexture.getWidth(), mTexture.getHeight());
    }

    public void drawStretch() {
        DisplaySize displaySize = GameDisplay.getInstance().getDisplaySize();
        drawStretch(displaySize.getWidth(), displaySize.getHeight());
    }

    public void drawStretch(int width, int height) {
        Point p = getPosition();
        mTexture.draw(p.x, p.y, getWorldDepth(), width, height);
    }

    @Override
    public int getWidth() {
        return mTexture.getWidth();
    }

    @Override
    public int getHeight() {
        return mTexture.getHeight();
    }
}
