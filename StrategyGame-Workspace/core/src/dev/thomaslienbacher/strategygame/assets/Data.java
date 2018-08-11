package dev.thomaslienbacher.strategygame.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * Holds all constants and is used to load i18n bundles
 *
 * @author Thomas Lienbacher
 */
public class Data {

    private static I18NBundle bundle;

    //i18n
    public static void loadI18N(AssetManager assetManager) {
        assetManager.load("i18n/Bundle", I18NBundle.class);
    }

    public static void createI18N(AssetManager assetManager) {
        bundle = assetManager.get("i18n/Bundle", I18NBundle.class);
    }

    public static String getI18N(String key) {
        return bundle.get(key);
    }

    public static String formatI18N(String key, Object... args) {
        return bundle.format(key, args);
    }

    //colors
    public static final Color TLS_BLUE = new Color((float)117 / 255, (float)183 / 255, 1.0f, 1.0f);

    //misc
    public static final String LOGO = "logo.png";

    //fonts
    public static final String FONT = "fonts/DroidSansMono.ttf";

    //map
    public static final String MAP_COLORCODE = "map/map_colorcode.png";
    public static final String MAPDATA_JSON = "map/map_data.json";

}
