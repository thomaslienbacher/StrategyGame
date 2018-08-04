package dev.thomaslienbacher.strategygame.scenes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.thomaslienbacher.strategygame.Game;
import dev.thomaslienbacher.strategygame.assets.Data;
import dev.thomaslienbacher.strategygame.assets.FontManager;
import dev.thomaslienbacher.strategygame.utils.Utils;

/**
 * Main menu right after the StartupScene
 *
 * @author Thomas Lienbacher
 */
public class MainMenuScene extends Scene {

    public MainMenuScene(GameStates state) {
        super(state);
    }

    @Override
    public void loadAssets(AssetManager assetManager) {

    }

    @Override
    public void create(AssetManager assetManager) {

    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        FontManager.get(200).renderCentered(batch, "Hallo", Game.WIDTHF / 2, Game.HEIGHTF / 2, Color.BLACK);
    }

    @Override
    public void dispose() {
        super.dispose();
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
}
