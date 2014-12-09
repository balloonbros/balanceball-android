package cc.balloonbros.balanceball.lib.graphic;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import cc.balloonbros.balanceball.lib.graphic.old.DrawObject;
import cc.balloonbros.balanceball.lib.graphic.old.Sprite;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.extender.TimerPlugin;
import cc.balloonbros.balanceball.lib.task.timer.TimerEventListener;

/**
 * アニメーション
 */
public class Animation extends DrawObject implements TimerEventListener {
    /**
     * どのスプライトのどの句形部分を描画するのかを
     * アニメーションクラスの内部に保持する
     */
    private class AnimationSprite {
        final public Sprite sprite;
        final public Rect source;

        public AnimationSprite(Sprite sp, Rect so) {
            sprite = sp;
            source = so;
        }
    }

    /** アニメーションリスト */
    private List<AnimationSprite> mAnimations = new ArrayList<AnimationSprite>();
    /** アニメーションに使うスプライトのリスト */
    private List<Sprite> mSprites = new ArrayList<Sprite>();
    /** 現在描画中のスプライトのインデックス */
    private int mUnitIndex = 0;
    /** アニメーションに使うスプライトのインデックスのリスト */
    private int[] mUnits;

    /**
     * コンストラクタ
     * @param task アニメーションを描画するタスク
     * @param interval アニメーションを更新する時間。ミリ秒
     * @param sprites スライス済みスプライト
     */
    public Animation(AbstractTask task, int interval, SlicedSprite sprites) {
        this(task, interval);
        addSprite(sprites);
    }

    /**
     * コンストラクタ
     * @param task アニメーションを描画するタスク
     * @param interval アニメーションを更新する時間。ミリ秒
     */
    public Animation(AbstractTask task, int interval) {
        if (!task.hasPlugin(TimerPlugin.class)) {
            task.with(new TimerPlugin());
        }
        task.plugin(TimerPlugin.class).setInterval(interval, this);
    }

    /**
     * アニメーションにスプライトを追加する
     * @param sprites 追加するスプライト
     */
    public void addSprite(SlicedSprite sprites) {
        mSprites.add(sprites.getSprite());
        for (int i = 0; i < sprites.getCount(); i++) {
            add(sprites.getSprite(), sprites.getSourceRectAt(i));
        }

        extendUnits(sprites.getCount());
    }

    /**
     * アニメーションにスプライトを追加する
     * @param sprites 追加するスプライトの配列
     */
    public void addSprites(Sprite... sprites) {
        for (Sprite sprite: sprites) {
            mSprites.add(sprite);
            add(sprite, sprite.getSource());
        }

        extendUnits(sprites.length);
    }

    /**
     * アニメーションにスプライトを追加する
     * @param sprite 追加するスプライト
     */
    public void addSprite(Sprite sprite) {
        mSprites.add(sprite);
        add(sprite, sprite.getSource());

        extendUnits(1);
    }

    /**
     * アニメーションするスプライトのインデックスのリストを設定する。
     * ここで指定したインデックスのスプライトがアニメーションされる。
     * @param units インデックスのリスト
     */
    public void useUnits(int... units) {
        mUnits = units;
    }

    /**
     * ここで指定したインデックスのスプライトからアニメーションが始まる。
     * @param index インデックス
     */
    public void setAnimationIndex(int index) {
        mUnitIndex = index;
    }

    /**
     * 現在のフレームで描画するスプライトを取得する
     * @return スプライト
     */
    public Sprite getCurrentSprite() {
        AnimationSprite animationSprite = getCurrentAnimation();
        if (animationSprite == null) {
            return null;
        }

        Sprite sprite = animationSprite.sprite;
        sprite.setSource(animationSprite.source);

        return sprite;
    }

    /**
     * スプライトとその描画矩形をアニメーションに登録する
     * @param sprite スプライト
     * @param source 矩形
     */
    private void add(cc.balloonbros.balanceball.lib.graphic.old.Sprite sprite, Rect source) {
        mAnimations.add(new AnimationSprite(sprite, source));
    }

    /**
     * アニメーションユニット配列を拡張する
     * @param extendCount 拡張数
     */
    private void extendUnits(int extendCount) {
        if (mUnits == null) {
            mUnits = new int[extendCount];
            for (int i = 0; i < extendCount; i++) {
                mUnits[i] = i;
            }
        } else {
            int[] units = new int[mUnits.length + extendCount];
            System.arraycopy(mUnits, 0, units, 0, mUnits.length);
            for (int i = 0; i < extendCount; i++) {
                units[mUnits.length] = mUnits.length + i;
            }
            mUnits = units;
        }
    }

    private AnimationSprite getCurrentAnimation() {
        if (mUnits == null || mUnitIndex > mUnits.length - 1 || mUnitIndex < 0) {
            return null;
        }

        int index = mUnits[mUnitIndex];
        if (index > mAnimations.size() - 1) {
            return null;
        }
        return mAnimations.get(index);
    }

    /**
     * アニメーションの描画位置が移動したら
     * アニメーションに内包しているスプライトの位置も同期させておく。
     * @param position 移動後の位置
     */
    @Override
    public void onMove(Point position) {
        super.onMove(position);
        for (int i = 0; i < mSprites.size(); i++) {
            mSprites.get(i).setPosition(position);
        }
    }

    @Override
    public int getWidth() {
        AnimationSprite animation = getCurrentAnimation();
        if (animation == null) {
            return 0;
        } else {
            return animation.sprite.getWidth();
        }
    }

    @Override
    public int getHeight() {
        AnimationSprite animation = getCurrentAnimation();
        if (animation == null) {
            return 0;
        } else {
            return animation.sprite.getHeight();
        }
    }

    @Override
    public void onTimer() {
        mUnitIndex++;
        if (mUnitIndex >= mUnits.length) {
            mUnitIndex = 0;
        }
    }
}
