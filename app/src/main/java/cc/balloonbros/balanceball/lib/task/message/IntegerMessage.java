package cc.balloonbros.balanceball.lib.task.message;

public class IntegerMessage extends AbstractTaskMessage {
    private int mMessage;

    public IntegerMessage(String label, int message) {
        super(label);
        mMessage = message;
    }

    public void setMessage(int message) {
        mMessage = message;
    }
    public int getMessage() {
        return mMessage;
    }
}
