package cc.balloonbros.balanceball;

public enum TaskPriority {
    MAXIMUM(0x0000),
    CENTER_CIRCLE(0x0001),
    BALL(0x0002),
    WIND_OUT_BREAKER(0x0020),
    WIND(0x0030),
    DEBUG(0xfffe),
    MINIMUM(0xffff);

    private final int mPriority;
    private TaskPriority(int priority) {
        mPriority = priority;
    }
    public int getPriority() {
        return mPriority;
    }
}
