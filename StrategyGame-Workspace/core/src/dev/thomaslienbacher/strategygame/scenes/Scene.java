package dev.thomaslienbacher.strategygame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dev.thomaslienbacher.strategygame.Game;

/**
 * Used to manage everything what happens in a scene
 *
 * @author Thomas Lienbacher
 */
public abstract class Scene implements InputProcessor {

    private GameStates state;
    protected Stage uistage;

    public Scene(GameStates state) {
        this.state = state;
        this.uistage = new Stage(Game.getGuiViewport(), Game.getBatch());
    }

    public abstract void loadAssets(AssetManager assetManager);

    public abstract void create(AssetManager assetManager);

    public void update(float delta) {
        uistage.act(delta);
    }

    public abstract void render(PolygonSpriteBatch batch);

    public final void renderGUI() {
        uistage.getBatch().end();
        uistage.draw();
    }

    public void dispose() {
        uistage.dispose();
    }

    public void switchTo() {
        Game.setGameState(this.state);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(0, uistage);
        inputMultiplexer.addProcessor(1, this);

        Gdx.input.setInputProcessor(inputMultiplexer);

        if(Game.DEBUG) {
            Gdx.graphics.setTitle(getClass().getName());
        }
    }

    public final GameStates getState() {
        return state;
    }

    public final Stage getUistage() {
        return uistage;
    }
}
