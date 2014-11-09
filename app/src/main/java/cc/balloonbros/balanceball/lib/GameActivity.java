package cc.balloonbros.balanceball.lib;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cc.balloonbros.balanceball.R;

public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        // 画面の明るさをキープしたまま暗くならないようにする
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // アプリのタイトルとステータスバーを非表示にする
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_activity);
    }
}
