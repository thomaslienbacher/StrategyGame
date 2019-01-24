package dev.thomaslienbacher.strategygame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dev.thomaslienbacher.strategygame.Game;

/**
 * Main class when run on Desktop
 *
 * @author Thomas Lienbacher
 */
public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.height = Game.HEIGHT;
        config.width = Game.WIDTH;
        config.title = DesktopLauncher.class.getName();
        config.samples = 4;
        config.allowSoftwareMode = false;
        config.fullscreen = true;
        config.vSyncEnabled = true;
        config.foregroundFPS = 60;
        config.backgroundFPS = 20;

        config.forceExit = false;

        new LwjglApplication(new Game(), config);
    }
}
