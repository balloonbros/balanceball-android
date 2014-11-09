package cc.balloonbros.balanceball;

import android.os.Bundle;

import cc.balloonbros.balanceball.lib.GameActivity;
import cc.balloonbros.balanceball.scene.LaunchScene;

public class MainActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long fps = getResources().getInteger(R.integer.fps);

        // ゲーム開始
        BalanceBall game = new BalanceBall(this);
        game.start(new LaunchScene(), fps);
    }
}
