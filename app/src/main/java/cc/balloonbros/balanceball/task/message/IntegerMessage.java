package cc.balloonbros.balanceball.task.message;

import cc.balloonbros.balanceball.lib.AbstractTaskMessage;

public class IntegerMessage extends AbstractTaskMessage {
    private int mMessage;

    public IntegerMessage(String label, int message) {
        super(label);
        mMessage = message;
    }

    public int getMessage() {
        return mMessage;
    }
}
