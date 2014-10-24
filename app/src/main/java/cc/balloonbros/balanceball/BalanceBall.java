package cc.balloonbros.balanceball;

import android.content.Context;
import android.hardware.SensorManager;

import cc.balloonbros.balanceball.lib.GameMain;

/**
 * BalanceBallゲーム本体。
 */
public class BalanceBall extends GameMain {
    /**
     * コンストラクタ
     * @param context ゲームを配置するActivity
     */
    public BalanceBall(Context context) {
        super(context, 1920, 1080);
    }
}