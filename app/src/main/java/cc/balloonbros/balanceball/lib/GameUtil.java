package cc.balloonbros.balanceball.lib;

public class GameUtil {
    /**
     * 現在のFPSにおいてミリ秒からフレーム数に変換する。
     * 指定されたミリ秒の間に、返されたフレーム数だけループされる。
     * @param millieSecond フレーム数に変換したいミリ秒
     * @return フレーム数
     */
    public static int milliSecondToFrameCount(int millieSecond) {
        long fps = CurrentGame.get().getFps();
        return (int) (fps * millieSecond / 1000);
    }
}
