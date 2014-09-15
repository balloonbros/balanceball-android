package cc.balloonbros.balanceball.lib.task.system;

public class BaseTask {
    private BaseTask mPrev = null;
    private BaseTask mNext = null;
    private int mPriority = 0xffff;

    void setPrev(BaseTask prev) {
        mPrev = prev;
    }
    void setNext(BaseTask next) {
        mNext = next;
    }
    void clearLink() {
        mPrev = mNext = null;
    }

    public BaseTask getPrev() {
        return mPrev;
    }
    public BaseTask getNext() {
        return mNext;
    }
    public boolean isFirst() {
        return mPrev == null;
    }
    public boolean isLast() {
        return mNext == null;
    }

    public int getPriority() {
        return mPriority;
    }
    public void setPriority(int priority) {
        mPriority = priority;
    }
}