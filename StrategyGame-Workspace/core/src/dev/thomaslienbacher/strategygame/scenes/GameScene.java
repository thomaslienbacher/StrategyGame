package dev.thomaslienbacher.strategygame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.thomaslienbacher.strategygame.Game;
import dev.thomaslienbacher.strategygame.assets.Data;
import dev.thomaslienbacher.strategygame.assets.FontManager;
import dev.thomaslienbacher.strategygame.gameobjects.Map;
import dev.thomaslienbacher.strategygame.utils.CameraController;

public class GameScene extends Scene {

    private Map map;

    public GameScene(GameStates state) {
        super(state);
    }

    @Override
    public void loadAssets(AssetManager assetManager) {
        assetManager.load(Data.MAP_COLORCODE, Texture.class);
    }

    @Override
    public void create(AssetManager assetManager) {
        map = new Map((Texture) assetManager.get(Data.MAP_COLORCODE));
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        int w =  10;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) Game.getGameCam().position.y += w;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) Game.getGameCam().position.y -= w;
        if(Gdx.input.isKeyPressed(Input.Keys.A)) Game.getGameCam().position.x -= w;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) Game.getGameCam().position.x += w;
    }

    @Override
    public void render(SpriteBatch batch) {
        map.render(batch);

        FontManager.get(40).renderCentered(batch, GameScene.class.getName(), Game.WIDTHF / 2, Game.HEIGHTF / 2 + 200, Color.BLACK);
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 v = CameraController.cameraUnproject(screenX, screenY);

        Gdx.app.log("P", map.getProvince((int)v.x, (int)v.y).toString());
        Gdx.app.log("S", map.getState((int)v.x, (int)v.y).toString());

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
        Game.getGameCam().zoom += (float) amount / 10;

        return false;
    }
}
