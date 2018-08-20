package dev.thomaslienbacher.strategygame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTextButton;
import dev.thomaslienbacher.strategygame.Game;
import dev.thomaslienbacher.strategygame.assets.Data;
import dev.thomaslienbacher.strategygame.assets.FontManager;

import java.util.Locale;

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
        VisTextButton playButton = new VisTextButton(Data.getI18N("play_button"));
        playButton.setBounds(20, 20, 200, 100);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.getGameScene().switchTo();
            }
        });
        uistage.addActor(playButton);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(PolygonSpriteBatch batch) {
        FontManager.get(200).renderCentered(batch, Data.getI18N("main_menu_title"), Game.WIDTHF / 2, Game.HEIGHTF / 2 + 200, Color.BLACK);
        FontManager.get(100).renderCentered(batch, Locale.getDefault().toString(), Game.WIDTHF / 2, Game.HEIGHTF / 2, Color.BLACK);
        FontManager.get(70).renderCentered(batch, Data.getI18N("author"), Game.WIDTHF / 2, Game.HEIGHTF / 2 - 100, new Color(0.1f, 0.4f, 0.5f, 1));
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
