package cc.balloonbros.balanceball.lib;

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
