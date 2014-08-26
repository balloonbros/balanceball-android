package cc.balloonbros.balanceball;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BalanceBall game = new BalanceBall(this);
        game.start();
    }
}
