package cc.balloonbros.balanceball.lib;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import cc.balloonbros.balanceball.R;

/**
 * An activity for a game you create.
 * Your activity must be inherited from this activity
 * in order to create the game using OpenGL.
 */
public class GameActivity extends Activity {
    /** A surface view for OpenGL */
    private GameSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        // Turn off the window's title bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Make the screen the full screen mode.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set the layout for GameActivity.
        setContentView(R.layout.game_activity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSurfaceView != null) {
            mSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSurfaceView != null) {
            mSurfaceView.onResume();
        }
    }

    /**
     * Get the surface view.
     * @return Surface view.
     */
    public GameSurfaceView getView() {
        return mSurfaceView;
    }

    /**
     * Create a new surface view for OpenGL and add it to the layout
     * in GameActivity.
     * If the surface view already exists, this method will do nothing.
     * @param game The game passed to the renderer.
     */
    public void buildSurface(GameMain game) {
        if (mSurfaceView != null) {
            return;
        }

        mSurfaceView = new GameSurfaceView(game);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.game_container);
        layout.addView(mSurfaceView, RelativeLayout.LayoutParams.MATCH_PARENT);
    }
}
