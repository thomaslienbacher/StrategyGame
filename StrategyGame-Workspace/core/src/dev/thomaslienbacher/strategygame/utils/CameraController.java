package dev.thomaslienbacher.strategygame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import dev.thomaslienbacher.strategygame.Game;

/**
 * @author Thomas Lienbacher
 */
public class CameraController {

    private static final float ZOOM_SENSTIVITY = 5.0f;
    private static final float MOVE_SENSTIVITY = 1.0f;

    private OrthographicCamera cam;

    public CameraController(OrthographicCamera cam) {
        this.cam = cam;
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

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        Game.getGameCam().zoom += (float) amount / 10;

        return false;
    }
}
