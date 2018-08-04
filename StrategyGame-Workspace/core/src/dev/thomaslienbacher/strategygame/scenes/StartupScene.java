package dev.thomaslienbacher.strategygame.scenes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.thomaslienbacher.strategygame.assets.Data;
import dev.thomaslienbacher.strategygame.utils.Utils;

/**
 * Scene that is shown when the game boots up
 *
 * @author Thomas Lienbacher
 */
public class StartupScene extends Scene {

    public static float LOGO_DISPLAY_TIME = 2.0f; //how long the logo will be shown on startup in seconds

    private Texture logo;
    private float logoTime = 0;

    public StartupScene(GameStates state) {
        super(state);
    }

    @Override
    public void loadAssets(AssetManager assetManager) {
        assetManager.load(Data.LOGO, Texture.class);
    }

    @Override
    public void create(AssetManager assetManager) {
        logo = assetManager.get(Data.LOGO);
        Utils.setLinearFilter(logo);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        logoTime += delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(logo, 0, 0);
    }

    @Override
    public void dispose() {
        super.dispose();

        logo.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public float getLogoTime() {
        return logoTime;
    }
}
