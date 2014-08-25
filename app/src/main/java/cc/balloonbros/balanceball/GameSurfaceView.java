package cc.balloonbros.balanceball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by kengo on 2014/08/21.
 */
public class GameSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private SurfaceHolder mHolder = null;
    private Thread mGameLoop = null;
    private Context mContext = null;

    private static final long FPS = 40;
    private static final long FRAME_TIME = 1000 / FPS;

    public GameSurfaceView(Context context) {
        super(context);
        mContext = context;

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mGameLoop = new Thread(this);
        mGameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mGameLoop = null;
    }

    @Override
    public void run() {
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.BLUE);

        Resources res = getResources();
        Bitmap ic = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);

        Point displaySize = new Point();
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(displaySize);

        int direction = 1;
        int x = 0;

        while (mGameLoop != null) {
            long startTime = System.currentTimeMillis();

            Canvas canvas = mHolder.lockCanvas();

            if (x + ic.getWidth() >= displaySize.x) {
                direction = -1;
            } else if (x <= 0){
                direction = 1;
            }

            x += (direction * 4);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawRect(0, 0, 100, 100, p);
            canvas.drawBitmap(ic, x, 100, p);

            mHolder.unlockCanvasAndPost(canvas);

            try {
                long waitTime = System.currentTimeMillis() - startTime;
                if (waitTime < FRAME_TIME) {
                    Thread.sleep(FRAME_TIME - waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
