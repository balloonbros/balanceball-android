package cc.balloonbros.balanceball;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // ゲーム開始
        BalanceBall game = new BalanceBall(this);
        game.start();
    }
}
