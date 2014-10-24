package cc.balloonbros.balanceball.lib;

public class CurrentGame {
    private static GameMain sGame;

    private CurrentGame() { }

    static void set(GameMain game) {
        sGame = game;
    }

    public static GameMain get() {
        return sGame;
    }
}
