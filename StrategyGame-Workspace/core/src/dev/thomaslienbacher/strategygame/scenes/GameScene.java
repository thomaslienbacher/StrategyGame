package dev.thomaslienbacher.strategygame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.thomaslienbacher.strategygame.Game;
import dev.thomaslienbacher.strategygame.assets.Data;
import dev.thomaslienbacher.strategygame.assets.FontManager;
import dev.thomaslienbacher.strategygame.gameobjects.Map;
import dev.thomaslienbacher.strategygame.ui.StateWindow;
import dev.thomaslienbacher.strategygame.utils.CameraController;

public class GameScene extends Scene {

    //gameobjects
    private Map map;

    //ui
    private StateWindow stateWindow;

    //misc
    private CameraController cameraController;

    public GameScene(GameStates state) {
        super(state);
    }

    @Override
    public void loadAssets(AssetManager assetManager) {

    }

    @Override
    public void create(AssetManager assetManager) {
        map = new Map();

        stateWindow = new StateWindow("State");
        uistage.addActor(stateWindow);

        cameraController = new CameraController(Game.getGameCam());
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        int w =  10;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) Game.getGameCam().position.y += w;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) Game.getGameCam().position.y -= w;
        if(Gdx.input.isKeyPressed(Input.Keys.A)) Game.getGameCam().position.x -= w;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) Game.getGameCam().position.x += w;

        //Gdx.app.log("M", ""+Game.getGameCam().zoom);
    }

    @Override
    public void render(PolygonSpriteBatch batch) {
        map.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
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
        cameraController.touchDown(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        cameraController.touchUp(screenX, screenY, pointer, button);
        Vector2 v = CameraController.cameraUnproject(screenX, screenY);

        stateWindow.getTitleLabel().setText(map.getState((int)v.x, (int)v.y).getName());

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        cameraController.touchDragged(screenX, screenY, pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        cameraController.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        cameraController.scrolled(amount);
        return false;
    }
}
