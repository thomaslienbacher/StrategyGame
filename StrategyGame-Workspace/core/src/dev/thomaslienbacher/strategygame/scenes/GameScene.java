package dev.thomaslienbacher.strategygame.scenes;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import dev.thomaslienbacher.strategygame.Game;
import dev.thomaslienbacher.strategygame.assets.Data;
import dev.thomaslienbacher.strategygame.gameobjects.Map;
import dev.thomaslienbacher.strategygame.gameobjects.Province;
import dev.thomaslienbacher.strategygame.ui.StateWindow;
import dev.thomaslienbacher.strategygame.utils.CameraController;
import dev.thomaslienbacher.strategygame.utils.Utils;

public class GameScene extends Scene {

    //gameobjects
    private Map map;
    private Texture background;

    //ui
    private StateWindow stateWindow;

    //misc
    private ShapeRenderer shapeRenderer;
    private CameraController cameraController;

    public GameScene(GameStates state) {
        super(state);
    }

    @Override
    public void loadAssets(AssetManager assetManager) {
        assetManager.load(Data.WATER, Texture.class);
    }

    @Override
    public void create(AssetManager assetManager) {
        map = new Map();
        background = assetManager.get(Data.WATER);
        background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        Utils.setLinearFilter(background);

        stateWindow = new StateWindow("State");
        uistage.addActor(stateWindow);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        cameraController = new CameraController(Game.getGameCam(), map);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(PolygonSpriteBatch batch) {
        float padding = map.getAbsolutePadding();
        batch.draw(background, map.getBounds().x - padding, map.getBounds().y - padding, map.getBounds().width + padding * 2, map.getBounds().height + padding * 2);

        map.render(batch);

        batch.end();

        shapeRenderer.setProjectionMatrix(Game.getGameCam().combined);
        shapeRenderer.begin();

        for(Province p : map.getProvinces()) {
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.polygon(p.getPolygon().getVertices());
        }

        shapeRenderer.end();

        batch.begin();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void switchTo() {
        super.switchTo();

        cameraController.startupCamPosition();
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

        try {
            stateWindow.getTitleLabel().setText(map.getState((int) v.x, (int) v.y).getName());
        }
        catch(Exception e){}

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
