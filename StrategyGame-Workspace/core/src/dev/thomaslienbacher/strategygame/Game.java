package dev.thomaslienbacher.strategygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.VisUI;
import dev.thomaslienbacher.strategygame.assets.Data;
import dev.thomaslienbacher.strategygame.assets.FontManager;
import dev.thomaslienbacher.strategygame.gameobjects.Province;
import dev.thomaslienbacher.strategygame.scenes.*;


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
	public static final String PREFERENCES = "strategygame-prefs";

	private static PolygonSpriteBatch batch;
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
	private static GameScene gameScene;

	//debug
	public final static boolean DEBUG = false;

	@Override
	public void create () {
		VisUI.load(Data.SKIN);
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
			float d = 0.86f;
			Gdx.graphics.setWindowedMode((int) (Gdx.graphics.getDisplayMode().height * d * ASPECT_RATIO), (int) (Gdx.graphics.getDisplayMode().height * d));
		}

		//batch
		batch = new PolygonSpriteBatch();

		//setup loadingscene
		startupScene = new StartupScene(GameStates.STARTUP);
		startupScene.loadAssets(assetManager);
		assetManager.finishLoading(); //this should never be called but this is an exception
		startupScene.create(assetManager);
		startupScene.switchTo();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.6f,0.6f,0.6f,1);
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

		if(gameState == GameStates.GAME){
			batch.setProjectionMatrix(gameCam.combined);
			gameScene.render(batch);
			gameScene.renderGUI();
		}

		/**
		 *  batch doesn't end since Batch is ended in {@link Scene#getUistage()}
		 */

		update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		gameViewport.update(width, height);
		guiViewport.update(width, height);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	public void update(float delta){
		if(firstFrame){
			//scenes
			mainMenuScene = new MainMenuScene(GameStates.MAINMENU);
			gameScene = new GameScene(GameStates.GAME);

			//load all assets
			FontManager.loadFonts();
			Data.loadI18N(assetManager);
			mainMenuScene.loadAssets(assetManager);
			gameScene.loadAssets(assetManager);

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

				Data.createI18N(assetManager);
				mainMenuScene.create(assetManager);
				gameScene.create(assetManager);
			}
		}

		if(gameState == GameStates.MAINMENU) mainMenuScene.update(delta);
		if(gameState == GameStates.GAME) gameScene.update(delta);

		//debug
		if(DEBUG) {
			if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
		}
	}

	@Override
	public void dispose () {
		mainMenuScene.dispose();
		gameScene.dispose();

		batch.dispose();
		FontManager.dispose();
		assetManager.dispose();
		VisUI.dispose();
	}

	public static float getDelta(){
		return Gdx.graphics.getDeltaTime();
	}

	public static PolygonSpriteBatch getBatch() {
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

	public static GameScene getGameScene() {
		return gameScene;
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
