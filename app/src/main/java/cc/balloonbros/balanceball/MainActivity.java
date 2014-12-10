package cc.balloonbros.balanceball;

import android.os.Bundle;

import cc.balloonbros.balanceball.lib.GameActivity;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.GameStartListener;
import cc.balloonbros.balanceball.scene.LaunchScene;
import cc.balloonbros.balanceball.scene.PlayScene;

public class MainActivity extends GameActivity implements GameStartListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long fps = getResources().getInteger(R.integer.fps);

        BalanceBall game = new BalanceBall(this);
        game.start(this);
    }

    @Override
    public void onStart(GameMain game) {
        //game.changeScene(new LaunchScene());
        game.changeScene(new PlayScene());
    }
}
