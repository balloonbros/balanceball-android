package cc.balloonbros.balanceball;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.SensorManager;

import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.task.Ball;
import cc.balloonbros.balanceball.task.CenterCircle;
import cc.balloonbros.balanceball.task.DebugOutput;
import cc.balloonbros.balanceball.task.Orientation;
import cc.balloonbros.balanceball.task.WindOutBreaker;

/**
 * BalanceBallゲーム本体。
 */
public class BalanceBall extends GameMain {
    public SensorManager mSensorManager = null;

    public SensorManager getSensorManager() {
        return mSensorManager;
    }

    /**
     * コンストラクタ
     *
     * @param context ゲームを配置するActivity
     */
    public BalanceBall(Context context) {
        super(context);
    }

    /**
     * ゲーム開始前処理。
     * 必要なリソースを読み込んでおく。
     */
    @Override
    public void onInitialize() {
        super.onInitialize();

        mSensorManager = (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);

        getAssetManager().loadAssets(R.drawable.ic_launcher, R.drawable.circle, R.drawable.ball3, R.drawable.area3, R.drawable.wind3);

        Ball           initialTask1 = new Ball();
        Orientation    initialTask2 = new Orientation();
        WindOutBreaker initialTask3 = new WindOutBreaker();
        DebugOutput    initialTask4 = new DebugOutput();
        CenterCircle   initialTask5 = new CenterCircle();

        Resources res = getResources();
        initialTask1.setPriority(res.getInteger(R.integer.priority_ball));
        initialTask3.setPriority(res.getInteger(R.integer.priority_wind_out_breaker));
        initialTask4.setPriority(res.getInteger(R.integer.priority_debug));
        initialTask5.setPriority(res.getInteger(R.integer.priority_center_circle));

        getTaskManager().register(initialTask1, initialTask2, initialTask3, initialTask4, initialTask5);
    }
}