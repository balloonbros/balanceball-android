package cc.balloonbros.balanceball.task.message;

import cc.balloonbros.balanceball.lib.task.message.AbstractTaskMessage;

public class OrientationMessage extends AbstractTaskMessage {
    private float[] mOrientation =  new float[3];

    public OrientationMessage() {
        super("orientation");
    }

    public float getAxisZ() {
        return mOrientation[0];
    }

    public float getAxisX() {
        return mOrientation[1];
    }

    public float getAxisY() {
        return mOrientation[2];
    }

    public float[] getOrientation() {
       return mOrientation;
    }
    public void setOrientation(float[] orientations) {
        mOrientation = orientations;
    }
}
