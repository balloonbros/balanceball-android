package cc.balloonbros.balanceball;

import android.content.Context;
import cc.balloonbros.balanceball.task.Ball;
import cc.balloonbros.balanceball.task.WindOutBreaker;

/**
 * BalanceBallゲーム本体。
 */
public class BalanceBall extends GameMain {
    /**
     * コンストラクタ。
     *
     * @param context ゲームを配置するコンテキスト(Activity)。
     */
    public BalanceBall(Context context) {
        super(context);
    }

    /**
     * ゲーム開始前処理。
     * 必要なリソースを読み込んでおく。
     */
    @Override
    public void onBeforeStart() {
        getAssetManager().loadAssets(R.drawable.ic_launcher);

        Ball           initialTask1 = new Ball();
        WindOutBreaker initialTask2 = new WindOutBreaker();

        getTaskManager().register(initialTask1, initialTask2);
    }
}