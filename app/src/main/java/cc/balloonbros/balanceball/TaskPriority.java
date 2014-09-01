package cc.balloonbros.balanceball;

public enum TaskPriority {
    MAXIMUM(0x0000),
    BALL(0x0001),
    WIND(0x0002),
    MINIMUM(0xffff);

    private final int mPriority;
    private TaskPriority(int priority) {
        mPriority = priority;
    }
    public int getPriority() {
        return mPriority;
    }
}
