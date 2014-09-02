package cc.balloonbros.balanceball;

import android.content.Context;

import java.io.ObjectInput;

import cc.balloonbros.balanceball.task.Ball;
import cc.balloonbros.balanceball.task.DebugOutput;
import cc.balloonbros.balanceball.task.Orientation;
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
    public void onInitialize() {
        getAssetManager().loadAssets(R.drawable.ic_launcher);

        Ball           initialTask1 = new Ball();
        Orientation    initialTask2 = new Orientation();
        WindOutBreaker initialTask3 = new WindOutBreaker();
        DebugOutput    initialTask4 = new DebugOutput();

        initialTask1.setPriority(TaskPriority.BALL);
        initialTask4.setPriority(TaskPriority.DEBUG);

        getTaskManager().reserve(initialTask1, initialTask2, initialTask3, initialTask4);
    }
}