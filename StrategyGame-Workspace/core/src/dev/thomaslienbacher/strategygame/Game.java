package dev.thomaslienbacher.strategygame;

import com.badlogic.gdx.Application;
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
import dev.thomaslienbacher.strategygame.scene.GameStates;


/**
 * Logo only shows for 0.1 seconds because of easier development change to 3 seconds when shipping
 * 
 * @author Thomas Lienbacher
 */
public class Game extends ApplicationAdapter {
	
	//constants
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080; //WIDTH / 9 * 16;
	public static final float ASPECT_RATIO = (float)WIDTH / (float)HEIGHT;
	public static final String APP_NAME = "Strategy Game";
	public static final String PREFERENCES = "strategygame-prefs";

	private static SpriteBatch batch;
	private static StretchViewport viewport;
	private static StretchViewport guiViewport;
	private static OrthographicCamera cam;
	private static OrthographicCamera guiCam;
	private static GameStates gameState;
	private static AssetManager assetManager;
	private static boolean firstFrame;
	private static Preferences preferences;
	private static int highscore;

	//Scenes
	/*private static StartupScene startupScene;
	private static MenuScene menuScene;
	private static GameScene gameScene;
	private static PauseScene pauseScene;*/

	//debug
	public final static boolean DEBUG = false;

	@Override
	public void create () {
		//Gdx.input.setCatchBackKey(true);

		gameState = GameStates.STARTUP;
		firstFrame = true;
		highscore = 0;

		assetManager = new AssetManager();
		Texture.setAssetManager(assetManager);

		//cam and viewport
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
		viewport = new StretchViewport(WIDTH, HEIGHT, cam);

		guiCam = new OrthographicCamera();
		guiCam.setToOrtho(false, WIDTH, HEIGHT);
		guiViewport = new StretchViewport(WIDTH, HEIGHT, guiCam);

		//resize
		if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
			float d = 0.85f;
			//Gdx.graphics.setWindowedMode((int) (Gdx.graphics.getDisplayMode().height * d * ASPECT_RATIO), (int) (Gdx.graphics.getDisplayMode().height * d));
		}

		//batch
		batch = new SpriteBatch();

		//setup loadingscene
		/*startupScene = new StartupScene(GameStates.STARTUP);
		startupScene.loadAssets(assetManager);
		assetManager.finishLoading(); //this should never be called but this is an exception
		startupScene.create(assetManager);
		startupScene.switchTo();*/
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		batch.begin();

		if(gameState == GameStates.STARTUP){
			batch.setProjectionMatrix(cam.combined);
			//startupScene.render(batch);
		}

		if(gameState == GameStates.MENU){
			batch.setProjectionMatrix(cam.combined);
			//menuScene.render(batch);

			batch.setProjectionMatrix(guiCam.combined);
			//menuScene.renderGUI(batch);
		}

		if(gameState == GameStates.GAME){
			batch.setProjectionMatrix(cam.combined);
			//gameScene.render(batch);

			batch.setProjectionMatrix(guiCam.combined);
			//gameScene.renderGUI(batch);
		}

		batch.end();

		update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		guiViewport.update(width, height);
	}

	@Override
	public void pause() {
		saveGame();
	}

	@Override
	public void resume() {}

	public void update(float delta){
		if(delta > 0.2f) delta = 0.2f;

		if(firstFrame){
			//scenes
			/*menuScene = new MenuScene(GameStates.MENU);
			gameScene = new GameScene(GameStates.GAME);
			pauseScene = new PauseScene(GameStates.PAUSE, gameScene);*/

			//load all assets
			FontManager.loadFonts();
			/*menuScene.loadAssets(assetManager);
			gameScene.loadAssets(assetManager);
			pauseScene.loadAssets(assetManager);*/

			//load prefs
			preferences = Gdx.app.getPreferences(PREFERENCES);
			highscore = Game.getPreferences().getInteger("highscore", 0);

			firstFrame = false;
		}

		if(gameState == GameStates.STARTUP){
			/*startupScene.update(delta);
			assetManager.update();
			if(assetManager.getProgress() >= 1 && startupScene.logoTime >= StartupScene.LOGO_DISLAY_TIME){
				menuScene.switchTo();
				startupScene.dispose();

				menuScene.create(assetManager);
				gameScene.create(assetManager);
				pauseScene.create(assetManager);
			}*/
		}

		/*if(gameState == GameStates.MENU) menuScene.update(delta);
		if(gameState == GameStates.GAME) gameScene.update(delta);
		if(gameState == GameStates.PAUSE) pauseScene.update(delta);*/

		//debug
		if(DEBUG) {
			if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
		}
	}

	@Override
	public void dispose () {
		/*menuScene.dispose();
		gameScene.dispose();
		pauseScene.dispose();*/

		batch.dispose();
		FontManager.dispose();
		assetManager.dispose();
		saveGame();
	}

	public void saveGame() {
		Game.getPreferences().putInteger("highscore", highscore);
		preferences.flush();
	}

	public static Vector2 cameraUnproject(int screenX, int screenY) {
		Vector3 vec = new Vector3(screenX, screenY, cam.position.z);
		cam.unproject(vec);
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

	public static OrthographicCamera getCam() {
		return cam;
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

	/*public static StartupScene getStartupScene() {
		return startupScene;
	}

	public static MenuScene getMenuScene() {
		return menuScene;
	}

	public static GameScene getGameScene() {
		return gameScene;
	}

	public static PauseScene getPauseScene() {
		return pauseScene;
	}*/

	public static void setGameState(GameStates gameState) {
		Game.gameState = gameState;
	}

	public static StretchViewport getViewport() {
		return viewport;
	}

	public static StretchViewport getGuiViewport() {
		return guiViewport;
	}

	public static Preferences getPreferences() {
		return preferences;
	}

	public static int getHighscore() {
		return highscore;
	}

	public static void setHighscore(int highscore) {
		Game.highscore = highscore;
	}
}
