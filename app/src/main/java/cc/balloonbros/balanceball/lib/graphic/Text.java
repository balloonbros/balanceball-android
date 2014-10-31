package cc.balloonbros.balanceball.lib.graphic;

import cc.balloonbros.balanceball.lib.CurrentGame;
import cc.balloonbros.balanceball.lib.graphic.style.Style;

/**
 * 描画専用の文字列。
 * ゲームループ中で文字列連結するとStringオブジェクトがnewされてしまい
 * すぐにGCが起動してパフォーマンスが悪くなってしまうので
 * なるべく新しい文字列オブジェクトをnewさせないようにしたクラス。
 * Stringの操作は新しいStringを作ることを意味するので絶対にしない。
 *
 * 内部的に文字列をcharの配列で管理しておりsetやappendで文字列を追加可能。
 * charの配列のサイズが足りなくなれば自動的に拡張される。
 * なるべく配列の拡張が起こらないように初期サイズを計算して
 * DrawStringのコンストラクタで指定する。
 *
 * またテキストのスタイルも指定できる。
 */
public class Text extends DrawObject {
    /** 文字列初期サイズ */
    private final static int INITIAL_CHAR_SIZE = 32;

    /** 文字列の実態 */
    private char[] mChars = null;
    /** 文字列の長さ */
    private int mLength = 0;

    /**
     * appendされる前の最初の文字列。
     * "+"演算子を使って文字列を拡張していくと新しいStringオブジェクトが生成されるため
     * appendではStringをchar[]に分解してメンバ変数に追加していくが
     * 一番最初の文字列だけは分解の必要がないためそのままStringで保持しておく。
     */
    private String mFirstString = null;

    /** 文字の描画時のスタイル */
    private Style mStyle = null;

    /**
     * 初期サイズで描画専用文字列オブジェクトを生成する
     */
    public Text() {
        this(INITIAL_CHAR_SIZE);
    }

    /**
     * 文字列長を指定して描画専用文字列オブジェクトを生成する
     */
    public Text(int size) {
        this(size, null);
    }

    /**
     * 文字列から描画専用文字列オブジェクトを生成する
     */
    public Text(String value) {
        this(value, null);
    }

    /**
     * 文字列長とスタイルを指定して描画専用文字列オブジェクトを生成する
     */
    public Text(int size, Style template) {
        mChars = new char[size];
        setStyle(template);
    }

    /**
     * 文字列とスタイルを指定して描画専用文字列オブジェクトを生成する
     */
    public Text(String value, Style template) {
        append(value);
        setStyle(template);
    }

    /**
     * スタイルをセットする
     * @param style スタイル
     */
    public Text setStyle(Style style) {
        mStyle = style;
        return this;
    }

    /**
     * スタイルをセットする
     * @param id スタイルID
     */
    public Text setStyle(String id) {
        mStyle = CurrentGame.get().getCurrentScene().getStyle(id);
        return this;
    }

    /**
     * 設定されているスタイルを取得する
     * @return スタイル
     */
    public Style getStyle() {
        return mStyle;
    }

    /**
     * 文字列を空にする
     * @return this
     */
    public Text clear() {
        mLength = 0;
        mFirstString = null;
        return this;
    }

    /**
     * 指定されたフォーマットに整形された文字列をセットする
     * @param format フォーマット。%s / %d / %f を利用可能
     * @param params パラメータの配列
     * @return this
     */
    public Text format(String format, Object... params) {
        clear();

        boolean placeholder = false;
        int index = 0;
        int formatLength = format.length();

        for (int i = 0; i < formatLength; i++) {
            char c = format.charAt(i);
            if (placeholder) {
                switch (c) {
                    case 's': {
                        append((String)params[index++]);
                        break;
                    }
                    case 'd': {
                        append((Integer)params[index++]);
                        break;
                    }
                    case 'f': {
                        append((Float)params[index++]);
                        break;
                    }
                }
                placeholder = false;
                continue;
            }

            if (c == '%') {
                boolean zeroPadding = false;
                placeholder = true;
                c = format.charAt(++i);
                switch (c) {
                    case '0': {
                        zeroPadding = true;
                        c = format.charAt(++i);
                    }
                    case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': {
                        int tmp = c - '0';
                        int paddingCount = tmp;
                        while (i + 1 < formatLength) {
                            tmp = format.charAt(++i) - '0';
                            if (0 > tmp || tmp > 9) {
                                break;
                            }
                            paddingCount = (paddingCount * 10) + tmp;
                        }

                        int paramLength = String.valueOf(params[index]).length();
                        for (int loop = 0; loop < paddingCount - paramLength; loop++) {
                            if (zeroPadding) {
                                append('0');
                            } else {
                                append(' ');
                            }
                        }
                    }
                    default: {
                        i--;
                        break;
                    }
                }
            } else {
                append(c);
            }
        }

        return this;
    }

    /**
     * 描画する文字列をセットする
     * @param value 文字列
     * @return this
     */
    public Text set(String value) {
        clear();
        return append(value);
    }

    /**
     * int型の数値を文字列に変換してそれを描画文字列として設定する
     * @param value 数値
     * @return this
     */
    public Text set(int value) {
        clear();
        return append(value);
    }

    /**
     * float型の数値を文字列に変換してそれを描画文字列として設定する
     * @param value 数値
     * @return this
     */
    public Text set(float value) {
        clear();
        return append(value);
    }

    /**
     * 文字を文字列に変換してそれを描画文字列として設定する
     * @param value 文字
     * @return this
     */
    public Text set(char value) {
        clear();
        return append(value);
    }

    /**
     * 文字列を後ろに追加する
     * @param value 追加対象の文字列
     * @return this
     */
    public Text append(String value) {
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

    /**
     * int型の数値を文字列に変換してそれを後ろに追加する
     * @param value 追加対象の数値
     * @return this
     */
    public Text append(int value) {
        return append(String.valueOf(value));
    }

    /**
     * floatの数値を文字列に変換してそれを後ろに追加する
     * @param value 追加対象の数値
     * @return this
     */
    public Text append(float value) {
        return append(String.valueOf(value));
    }

    /**
     * 文字を後ろに追加する
     * @param value 追加対象の文字
     * @return this
     */
    public Text append(char value) {
        return addChar(value);
    }

    /**
     * 現在の文字の配列を取得する
     * @return 文字の配列
     */
    public char[] getChars() {
        return mChars;
    }

    /**
     * 現在の文字列の長さを取得する
     * @return 文字列の長さ
     */
    public int getLength() {
        return mLength;
    }

    /**
     * 最初の文字列が指定してあればそれもchar[]に分解した上で
     * 指定された文字を配列に追加していく
     * @param value 配列に追加する文字
     * @return this
     */
    private Text addChar(char value) {
        if (mFirstString != null) {
            for (int i = 0; i < mFirstString.length(); i++) {
                _addChar(mFirstString.charAt(i));
            }
            mFirstString = null;
        }
        _addChar(value);

        return this;
    }

    /**
     * 指定された文字を配列に追加する。
     * 配列のサイズが足りなければ自動的に現在の2倍のサイズに拡張する。
     * @param value 配列に追加する文字
     * @return this
     */
    private Text _addChar(char value) {
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

    /**
     * 複数回のappendが呼ばれて文字列がchar[]に分割された状態(連結する必要があるかどうか)
     * @return 文字列を連結する必要があればtrue
     */
    boolean needsConcatenate() {
        return mFirstString == null;
    }

    /**
     * 最初の文字列を取得する
     * @return 最初の文字列
     */
    String getFirstString() {
        return mFirstString;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
