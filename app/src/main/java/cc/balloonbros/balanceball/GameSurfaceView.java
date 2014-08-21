package cc.balloonbros.balanceball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by kengo on 2014/08/21.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder holder = null;

    public GameSurfaceView(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.BLUE);

        Resources res = getResources();
        Bitmap ic = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);

        Canvas canvas = holder.lockCanvas();

        canvas.drawRect(0, 0, 100, 100, p);
        canvas.drawBitmap(ic, 100, 100, p);

        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
