package cc.balloonbros.balanceball.lib.scene;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cc.balloonbros.balanceball.lib.AssetManager;
import cc.balloonbros.balanceball.lib.GameMain;
import cc.balloonbros.balanceball.lib.ResourceBase;
import cc.balloonbros.balanceball.lib.display.DisplaySize;
import cc.balloonbros.balanceball.lib.display.GameDisplay;
import cc.balloonbros.balanceball.lib.graphic.Surface;
import cc.balloonbros.balanceball.lib.graphic.opengl.FrameBuffer;
import cc.balloonbros.balanceball.lib.graphic.opengl.Texture;
import cc.balloonbros.balanceball.lib.graphic.style.Style;
import cc.balloonbros.balanceball.lib.graphic.style.StyleTemplate;
import cc.balloonbros.balanceball.lib.task.AbstractTask;
import cc.balloonbros.balanceball.lib.task.TaskManager;

/**
 * シーンの基底クラス。
 */
abstract public class AbstractScene extends ResourceBase {
    /** シーンが属するゲーム */
    private GameMain mGame;
    /** シーンで利用する素材 */
    private AssetManager mAssetManager;
    /** シーンで利用するスタイル */
    private StyleTemplate mStyleTemplate = new StyleTemplate();
    /** シーンのタスクマネージャー */
    private TaskManager mTaskManager;
    private Map<Integer, Texture> mTextures = new HashMap<Integer, Texture>();

    /** The frame buffer that belongs to this scene. */
    private FrameBuffer mFrameBuffer = new FrameBuffer();

    public TaskManager getTaskManager() { return mTaskManager; }
    public AssetManager getAssetManager() { return mAssetManager; }
    public GameMain getGame() { return mGame; }

    /**
     * コンストラクタ
     */
    public AbstractScene() {
        mTaskManager = new TaskManager(this);

        DisplaySize displaySize = GameDisplay.getInstance().getDisplaySize();
        mFrameBuffer.setup(displaySize.getWidth(), displaySize.getHeight());
    }

    /**
     * シーンが属するゲームをセットする
     * @param game シーンが属するゲーム
     */
    public void belongsTo(GameMain game) {
        mAssetManager = new AssetManager(game.getContext().getResources());
        mGame = game;

        onInitialize();
    }

    /**
     * FPSを変更する
     * @param fps 変更後のFPS
     */
    public void changeFps(long fps) { mGame.getGameLoop().changeFps(fps); }

    /**
     * タスクを新しく登録する
     * @param tasks 登録するタスク。複数指定可能
     */
    public void registerTasks(AbstractTask... tasks) { getTaskManager().register(tasks); }

    /**
     * 画像を読み込む
     * @param assetId 素材のID
     */
    public void loadBitmaps(int... assetId) { mAssetManager.loadBitmaps(assetId); }

    /**
     * Load a texture from the resource id.
     * @param resourceId The resource id that loads to a texture.
     */
    public void loadTexture(final int resourceId) {
        final Texture texture = new Texture(resourceId);
        mTextures.put(resourceId, texture);

        /*
        mGame.getView().queueEvent(new Runnable() {
            @Override
            public void run() {
                texture.load(resourceId);
            }
        });
        */
    }

    /**
     * Get a loaded texture.
     * Note that, return null if there isn't the texture related by
     * resource id.
     * @param resourceId The resource id.
     * @return A texture.
     */
    public Texture getTexture(int resourceId) {
        return mTextures.get(resourceId);
    }

    /**
     * フォントを読み込む
     * @param fonts フォント名。複数指定可能
     */
    public void loadFonts(String... fonts) { mAssetManager.loadFonts(fonts); }

    /**
     * スタイルを読み込む
     * @param xmlId スタイルが定義されたXMLのID
     */
    public void loadStyle(int... xmlId) {
        Resources r = mGame.getContext().getResources();
        for (int id: xmlId) {
            XmlResourceParser parser = r.getXml(id);
            try {
                Style style = new Style();
                Style defaultStyle = null;
                String styleId = null;
                boolean isDefaultTag = false;

                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tag = parser.getName();

                    switch (eventType) {
                        case XmlPullParser.START_TAG: {
                            if (tag.equals("style")) {
                                styleId = parser.getAttributeValue(null, "id");
                            } else if (tag.equals("item")) {
                                String attribute = parser.getAttributeValue(null, "name");
                                if (attribute.equals("font")) {
                                    if (isDefaultTag && defaultStyle != null) {
                                        defaultStyle.font(getFont(parser.nextText()));
                                    } else {
                                        style.font(getFont(parser.nextText()));
                                    }
                                } else if (attribute.equals("color")) {
                                    if (isDefaultTag && defaultStyle != null) {
                                        defaultStyle.color(Color.parseColor(parser.nextText()));
                                    } else {
                                        style.color(Color.parseColor(parser.nextText()));
                                    }
                                } else if (attribute.equals("antialias")) {
                                    if (isDefaultTag && defaultStyle != null) {
                                        defaultStyle.antiAlias(parser.nextText().equals("true"));
                                    } else {
                                        style.antiAlias(parser.nextText().equals("true"));
                                    }
                                } else if (attribute.equals("align")) {
                                    if (isDefaultTag && defaultStyle != null) {
                                        defaultStyle.align(Paint.Align.valueOf(parser.nextText().toUpperCase()));
                                    } else {
                                        style.align(Paint.Align.valueOf(parser.nextText().toUpperCase()));
                                    }
                                } else if (attribute.equals("size")) {
                                    if (isDefaultTag && defaultStyle != null) {
                                        defaultStyle.size(Integer.parseInt(parser.nextText()));
                                    } else {
                                        style.size(Integer.parseInt(parser.nextText()));
                                    }
                                }
                            } else if (tag.equals("default")) {
                                defaultStyle = new Style();
                                isDefaultTag = true;
                            }
                            break;
                        }
                        case XmlPullParser.END_TAG: {
                            if (tag.equals("style")) {
                                mStyleTemplate.add(styleId, style);
                            } else if (tag.equals("default")) {
                                isDefaultTag = false;
                            }

                            if (tag.equals("style") || tag.equals("default")) {
                                if (defaultStyle == null) {
                                    style = new Style();
                                } else {
                                    style = Style.from(defaultStyle);
                                }
                            }
                            break;
                        }
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ロードされたリソースから画像を取得する
     * @param assetId 画像ID
     * @return 画像
     */
    public Bitmap getImage(int assetId) { return getAssetManager().getImage(assetId); }

    /**
     * フォントを取得する
     * @param font フォント名
     * @return フォント
     */
    public Typeface getFont(String font) { return getAssetManager().getFont(font); }

    /**
     * 登録されたスタイルテンプレートを取得する
     * @param tag タグ名
     * @return タグ名に紐づくスタイル
     */
    public Style getStyle(String tag) {
        return mStyleTemplate.get(tag);
    }

    /**
     */
    public FrameBuffer execute() {
        mFrameBuffer.begin();
        // 全てのタスクを実行する
        mTaskManager.execute(null);
        mFrameBuffer.end();
        return mFrameBuffer;
    }
    public FrameBuffer getFrameBuffer() {
        return mFrameBuffer;
    }

    /**
     * シーンに登録されている全てのタスクを実行して描画する
     * @param surface 描画先のサーフェイス
     */
    public Surface execute(Surface surface) {
        // 全てのタスクを実行する
        mTaskManager.execute(surface);
        return surface;
    }

    /**
     * シーンを破棄する
     */
    public void dispose() {
        mTaskManager.dispose();
        mAssetManager.dispose();
        mStyleTemplate.dispose();
    }

    /* ==============================================
     *           オーバーライド専用メソッド
     * ============================================== */

    /**
     * シーンが切り替わる際に最初に実行される
     */
    protected void onInitialize() { }
}
