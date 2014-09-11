package cc.balloonbros.balanceball.lib.task.message;

abstract public class AbstractTaskMessage implements TaskMessage {
    protected String mLabel = "";

    public AbstractTaskMessage(String label) {
        mLabel = label;
    }

    @Override
    public String getLabel() {
        return mLabel;
    }
}
