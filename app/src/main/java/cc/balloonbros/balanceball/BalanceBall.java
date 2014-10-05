package cc.balloonbros.balanceball;

import android.content.Context;
import android.hardware.SensorManager;

import cc.balloonbros.balanceball.lib.GameMain;

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
    }
}