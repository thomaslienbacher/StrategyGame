package dev.thomaslienbacher.strategygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import dev.thomaslienbacher.strategygame.assets.FontManager;
import dev.thomaslienbacher.strategygame.scenes.GameStates;
import dev.thomaslienbacher.strategygame.scenes.MainMenuScene;
import dev.thomaslienbacher.strategygame.scenes.Scene;
import dev.thomaslienbacher.strategygame.scenes.StartupScene;


/**
 * 
 * @author Thomas Lienbacher
 */
public class Game extends ApplicationAdapter {
	
	//constants
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080; //WIDTH / 9 * 16;
	public static final float WIDTHF = 1920;
	public static final float HEIGHTF = 1080; //WIDTH / 9 * 16;
	public static final float ASPECT_RATIO = (float)WIDTH / (float)HEIGHT;
	public static final String APP_NAME = "Strategy Game";
	public static final String PREFERENCES = "strategygame-prefs";

	private static SpriteBatch batch;
	private static StretchViewport gameViewport;
	private static StretchViewport guiViewport;
	private static OrthographicCamera gameCam;
	private static OrthographicCamera guiCam;
	private static GameStates gameState;
	private static AssetManager assetManager;
	private static boolean firstFrame;
	private static Preferences preferences;

	//Scenes
	private static StartupScene startupScene;
	private static MainMenuScene mainMenuScene;

	//debug
	public final static boolean DEBUG = false;

	@Override
	public void create () {
	    gameState = GameStates.STARTUP;
		firstFrame = true;

		assetManager = new AssetManager();
		Texture.setAssetManager(assetManager);

		//gameCam and gameViewport
		gameCam = new OrthographicCamera();
		gameCam.setToOrtho(false, WIDTH, HEIGHT);
		gameViewport = new StretchViewport(WIDTH, HEIGHT, gameCam);

		guiCam = new OrthographicCamera();
		guiCam.setToOrtho(false, WIDTH, HEIGHT);
		guiViewport = new StretchViewport(WIDTH, HEIGHT, guiCam);

		//resize
        if(Game.DEBUG) {
			float d = 0.6f;
			Gdx.graphics.setWindowedMode((int) (Gdx.graphics.getDisplayMode().height * d * ASPECT_RATIO), (int) (Gdx.graphics.getDisplayMode().height * d));
		}

		//batch
		batch = new SpriteBatch();

		//setup loadingscene
		startupScene = new StartupScene(GameStates.STARTUP);
		startupScene.loadAssets(assetManager);
		assetManager.finishLoading(); //this should never be called but this is an exception
		startupScene.create(assetManager);
		startupScene.switchTo();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameCam.update();
		batch.begin();

		if(gameState == GameStates.STARTUP){
			batch.setProjectionMatrix(gameCam.combined);
			startupScene.render(batch);
			startupScene.renderGUI();
		}

		if(gameState == GameStates.MAINMENU){
			batch.setProjectionMatrix(gameCam.combined);
			mainMenuScene.render(batch);
			mainMenuScene.renderGUI();
		}

		/**
		 *  batch doesnt end since Batch is ended in {@link Scene#getUistage()}
		 */

		update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		gameViewport.update(width, height);
		guiViewport.update(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {}

	public void update(float delta){
		if(firstFrame){
			//scenes
			mainMenuScene = new MainMenuScene(GameStates.MAINMENU);

			//load all assets
			FontManager.loadFonts();
			mainMenuScene.loadAssets(assetManager);

			//load prefs
			preferences = Gdx.app.getPreferences(PREFERENCES);
			firstFrame = false;
		}

		if(gameState == GameStates.STARTUP){
			startupScene.update(delta);
			assetManager.update();
			if(assetManager.getProgress() >= 1 && startupScene.getLogoTime() >= StartupScene.LOGO_DISPLAY_TIME){
				mainMenuScene.switchTo();
				startupScene.dispose();

				mainMenuScene.create(assetManager);
			}
		}

		if(gameState == GameStates.MAINMENU) mainMenuScene.update(delta);

		//debug
		if(DEBUG) {
			if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
			Gdx.app.log("State", gameState.name());
		}
	}

	@Override
	public void dispose () {
		mainMenuScene.dispose();

		batch.dispose();
		FontManager.dispose();
		assetManager.dispose();
	}

	public static Vector2 cameraUnproject(int screenX, int screenY) {
		Vector3 vec = new Vector3(screenX, screenY, gameCam.position.z);
		gameCam.unproject(vec);
		return new Vector2(vec.x, vec.y);
	}

	public static Vector2 toScreenCoords(int screenX, int screenY) {
		Vector2 vec = new Vector2();
		vec.x = (float)screenX / (float)Gdx.graphics.getWidth() * Game.WIDTH;
		vec.y = -((float)screenY / (float)Gdx.graphics.getHeight() * Game.HEIGHT) + Game.HEIGHT;
		return vec;
	}

	public static float getDelta(){
		return Gdx.graphics.getDeltaTime();
	}

	public static SpriteBatch getBatch() {
		return batch;
	}

	public static OrthographicCamera getGameCam() {
		return gameCam;
	}

	public static OrthographicCamera getGuiCam() { return guiCam; }

	public static GameStates getGameState() {
		return gameState;
	}

	public static AssetManager getAssetManager() {
		return assetManager;
	}

	public static boolean isFirstFrame() {
		return firstFrame;
	}

	public static StartupScene getStartupScene() {
		return startupScene;
	}

	public static MainMenuScene getMainMenuScene() {
		return mainMenuScene;
	}

	public static void setGameState(GameStates gameState) {
		Game.gameState = gameState;
	}

	public static StretchViewport getGameViewport() {
		return gameViewport;
	}

	public static StretchViewport getGuiViewport() {
		return guiViewport;
	}

	public static Preferences getPreferences() {
		return preferences;
	}
}
