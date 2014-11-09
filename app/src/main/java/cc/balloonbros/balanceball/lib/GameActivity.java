package cc.balloonbros.balanceball.lib;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import cc.balloonbros.balanceball.R;

public class GameActivity extends Activity {
    private GameSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        // Turn off the window's title bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Make the screen the full screen mode.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game_activity);

        mSurfaceView = new GameSurfaceView(this);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.game_container);
        layout.addView(mSurfaceView, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResume();
    }

    /**
     * Get the surface view.
     * @return Surface view.
     */
    public GameSurfaceView getView() {
        return mSurfaceView;
    }
}
