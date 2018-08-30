package dev.thomaslienbacher.strategygame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.thomaslienbacher.strategygame.Game;
import dev.thomaslienbacher.strategygame.gameobjects.Map;

/**
 * @author Thomas Lienbacher
 */
public class CameraController {

    private static final float ZOOM_SENSITIVITY = 0.95f;
    private static final float MOVE_SENSITIVITY = 0.6f;

    private OrthographicCamera cam;
    private Map map;

    public CameraController(OrthographicCamera cam, Map map) {
        this.cam = cam;
        this.map = map;
    }

    public static Vector2 cameraUnproject(int screenX, int screenY) {
        Vector3 vec = new Vector3(screenX, screenY, Game.getGameCam().position.z);
        Game.getGameCam().unproject(vec);
        return new Vector2(vec.x, vec.y);
    }

    public static Vector2 toScreenCoords(int screenX, int screenY) {
        Vector2 vec = new Vector2();
        vec.x = (float)screenX / (float) Gdx.graphics.getWidth() * Game.WIDTH;
        vec.y = -((float)screenY / (float)Gdx.graphics.getHeight() * Game.HEIGHT) + Game.HEIGHT;
        return vec;
    }

    public void startupCamPosition() {
        Vector2 center = new Vector2();
        map.getBounds().getCenter(center);
        this.cam.position.x = center.x;
        this.cam.position.y = center.y;

        limitCamZoom();
        limitCamPosition();
    }

    private Vector2 prevPosition = new Vector2();

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 v = cameraUnproject(screenX, screenY);
        prevPosition = v.cpy();

        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 v = cameraUnproject(screenX, screenY);

        Vector3 pos = cam.position;
        pos.x += (v.x - prevPosition.x) * -MOVE_SENSITIVITY;
        pos.y += (v.y - prevPosition.y) * -MOVE_SENSITIVITY;

        prevPosition = v.cpy();

        limitCamPosition();

        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        cam.zoom *= (amount > 0 ? ZOOM_SENSITIVITY : 1 / ZOOM_SENSITIVITY);

        limitCamZoom();
        limitCamPosition();

        return false;
    }

    //limit zoom
    private void limitCamZoom() {
        float maxZoom = (map.getBounds().width < map.getBounds().height
                ? map.getBounds().width / Game.WIDTHF
                : map.getBounds().height / Game.HEIGHTF);

        if(cam.zoom > maxZoom) cam.zoom = maxZoom;
    }

    //limit to inside the bounds of the map
    private void limitCamPosition() {
        Vector3 pos = cam.position;
        Rectangle bounds = map.getBounds();
        float pad = map.getAbsolutePadding();
        float zoom = cam.zoom;

        if(pos.x < bounds.x - pad + Game.WIDTHF * zoom / 2) pos.x = bounds.x - pad + Game.WIDTHF * zoom / 2;
        else if(pos.x > bounds.x + bounds.width + pad - Game.WIDTHF * zoom / 2) pos.x = bounds.x + bounds.width + pad - Game.WIDTHF * zoom / 2;
        if(pos.y < bounds.y - pad + Game.HEIGHTF * zoom / 2) pos.y = bounds.y - pad + Game.HEIGHTF * zoom / 2;
        else if(pos.y > bounds.y + bounds.height + pad - Game.HEIGHTF * zoom / 2) pos.y = bounds.y + bounds.height + pad - Game.HEIGHTF * zoom / 2;
    }
}
