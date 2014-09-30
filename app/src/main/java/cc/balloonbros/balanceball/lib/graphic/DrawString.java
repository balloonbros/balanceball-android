package cc.balloonbros.balanceball.lib.graphic;

public class DrawString {
    private final static int INITIAL_CHAR_SIZE = 32;
    private char[] mChars = null;
    private int mLength = 0;
    private String mFirstString = null;
    private Style mTemplate = null;

    public DrawString() {
        this(INITIAL_CHAR_SIZE);
    }

    public DrawString(int size) {
        this(size, null);
    }

    public DrawString(String value) {
        this(value, null);
    }

    public DrawString(Style template) {
        this(INITIAL_CHAR_SIZE, template);
    }

    public DrawString(int size, Style template) {
        mChars = new char[size];
        setTemplate(template);
    }

    public DrawString(String value, Style template) {
        append(value);
        setTemplate(template);
    }

    public void setTemplate(Style template) {
        mTemplate = template;
    }

    public Style getTemplate() {
        return mTemplate;
    }

    public DrawString clear() {
        mLength = 0;
        mFirstString = null;
        return this;
    }

    public DrawString set(String value) {
        clear();
        return append(value);
    }

    public DrawString set(int value) {
        clear();
        return append(value);
    }

    public DrawString set(float value) {
        clear();
        return append(value);
    }

    public DrawString set(char value) {
        clear();
        return append(value);
    }

    public DrawString append(String value) {
        if (mLength == 0) {
            mFirstString = value;
            mLength = value.length();
        } else {
            for (int i = 0; i < value.length(); i++) {
                addChar(value.charAt(i));
            }
        }

        return this;
    }

    public DrawString append(int value) {
        return append(String.valueOf(value));
    }

    public DrawString append(float value) {
        return append(String.valueOf(value));
    }

    public DrawString append(char value) {
        return addChar(value);
    }

    public char[] getChars() {
        return mChars;
    }

    public int getLength() {
        return mLength;
    }

    private DrawString addChar(char value) {
        if (mFirstString != null) {
            for (int i = 0; i < mFirstString.length(); i++) {
                _addChar(mFirstString.charAt(i));
            }
            mFirstString = null;
        }
        _addChar(value);

        return this;
    }

    private DrawString _addChar(char value) {
        if (mChars == null) {
            mChars = new char[INITIAL_CHAR_SIZE];
        }

        if (mLength == mChars.length) {
            char[] newChars = new char[mChars.length * 2];
            System.arraycopy(mChars, 0, newChars, 0, mChars.length);
            mChars = newChars;
        }

        mChars[mLength] = value;
        mLength++;

        return this;
    }

    boolean needsConcatenate() {
        return mFirstString == null;
    }

    String getFirstString() {
        return mFirstString;
    }
}
