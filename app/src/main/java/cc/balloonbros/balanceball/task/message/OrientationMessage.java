package cc.balloonbros.balanceball.task.message;

import cc.balloonbros.balanceball.lib.AbstractTaskMessage;

public class OrientationMessage extends AbstractTaskMessage {
    private float[] mOrientatin =  new float[3];

    public OrientationMessage(float[] orientations) {
        super("orientation");
        mOrientatin = orientations;
    }

    public float getAxisZ() {
        return mOrientatin[0];
    }

    public float getAxisX() {
        return mOrientatin[1];
    }

    public float getAxisY() {
        return mOrientatin[2];
    }

    public float[] getOrientation() {
       return mOrientatin;
    }
}
