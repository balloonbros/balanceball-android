package cc.balloonbros.balanceball.lib.scene;

import cc.balloonbros.balanceball.lib.CurrentGame;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.graphic.opengl.FrameBuffer;
import cc.balloonbros.balanceball.lib.scene.transition.Transitionable;

/**
 * シーン切り替えクラス。
 *
 * 現在のシーンと切り替え先のシーンを登録してシーンの切り替えを実行する。
 * シーン切り替えの際にトランジションエフェクトが登録されていれば
 * エフェクトを実行しながらシーンが切り替わっていく。
 */
public class SceneChanger {
    /** 現在のシーン */
    private AbstractScene mCurrentScene;
    /** 切り替え先のシーン */
    private AbstractScene mNextScene;
    /** 切り替え先のシーンの描画先 */
    private Surface mNextSurface;
    /** シーン切り替え時のトランジションエフェクト */
    private Transitionable mTransition;
    /** シーン切り替えが完了したかどうか */
    private boolean mFinishedSceneChange = false;

    /**
     * コンストラクタ
     * @param currentScene 現在のシーン
     * @param nextScene 切り替え先のシーン
     */
    public SceneChanger(AbstractScene currentScene, AbstractScene nextScene) {
        mCurrentScene = currentScene;
        mNextScene    = nextScene;
    }

    /**
     * シーン切り替えに使うトランジションエフェクトを登録する
     * @param transition トランジションエフェクト
     */
    public void with(Transitionable transition) {
        mTransition = transition;

        // ToDo: シーン切り替えが完了したらサーフェイス内のビットマップを解放するのを忘れずに
        if (mNextSurface == null) {
            //mNextSurface = new Surface();
        }
    }

    /**
     * シーン切り替えを実行する
     */
    public FrameBuffer execute() {
        FrameBuffer frameBuffer;

        if (mCurrentScene == null || mTransition == null) {
            // トランジションエフェクトがない場合はすぐに次のシーンへ切り替える
            frameBuffer = mNextScene.execute();
            done();
        } else {
            // 全てのタスクを実行する
            FrameBuffer currentSceneFrameBuffer = null;
            FrameBuffer nextSceneFrameBuffer = null;

            if (mTransition.isDrawingCurrentScene()) {
                currentSceneFrameBuffer = mCurrentScene.execute();
            }
            if (mTransition.isDrawingNextScene()) {
                nextSceneFrameBuffer = mNextScene.execute();
            }
            frameBuffer = mTransition.transit(currentSceneFrameBuffer, nextSceneFrameBuffer);

            if (mTransition.completeTransition()) {
                done();
            }
        }

        return frameBuffer;
    }

    /**
     * シーン切り替えを実行する
     */
    public Surface execute(Surface surface) {
        if (mCurrentScene == null || mTransition == null) {
            // トランジションエフェクトがない場合はすぐに次のシーンへ切り替える
            mNextScene.getTaskManager().execute(surface);
            done();
        } else {
            // 全てのタスクを実行する
            if (mTransition.isDrawingCurrentScene()) {
                mCurrentScene.getTaskManager().execute(surface);
            }
            if (mTransition.isDrawingNextScene()) {
                mNextScene.getTaskManager().execute(mNextSurface);
            }
            surface = mTransition.transit(surface, mNextSurface);

            if (mTransition.completeTransition()) {
                done();
            }
        }

        return surface;
    }

    /**
     * シーン切り替えが完了したかどうかを確認する
     * @return シーン切り替えが完了していればtrue
     */
    public boolean finishedSceneChange() {
        return mFinishedSceneChange;
    }

    /**
     * シーン切り替えが完了した時にコールする
     */
    private void done() {
        // 終了フラグをたてる
        mFinishedSceneChange = true;

        // 現在のシーンを破棄する
        if (mCurrentScene != null) {
            mCurrentScene.dispose();
        }
    }
}
