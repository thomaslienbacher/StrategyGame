package dev.thomaslienbacher.strategygame.assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import dev.thomaslienbacher.strategygame.utils.Utils;

/**
 *	This class represents a Font which can be used to render Text
 * 
 * @author Thomas Lienbacher
 */
public class Font {
	private BitmapFont bitmapFont;
	private float scale;
	
	public Font(BitmapFont bitmapFont, float scale){
		this.bitmapFont = bitmapFont;
		this.bitmapFont.setUseIntegerPositions(false);
		this.bitmapFont.getData().markupEnabled = true;
		this.scale = scale;
	}

	public void render(PolygonSpriteBatch batch, String text, float x, float y){
		scaleFont();
		bitmapFont.draw(batch, text, x, y);
	}
	
	public void render(PolygonSpriteBatch batch, String text, float x, float y, Color color){
		scaleFont();
		bitmapFont.setColor(color);
		bitmapFont.draw(batch, text, x, y);
		bitmapFont.setColor(Color.WHITE);
	}

	public void renderCentered(PolygonSpriteBatch batch, String text, float x, float y, Color color){
		scaleFont();
		bitmapFont.setColor(color);
		bitmapFont.draw(batch, text, x - Utils.calculateStringWidth(this, text) / 2, y + Utils.calculateStringHeight(this, text) / 2);
		bitmapFont.setColor(Color.WHITE);
	}

	private void scaleFont() {
		bitmapFont.getData().scaleX = scale;
		bitmapFont.getData().scaleY = scale;
	}

	public BitmapFont getBitmapFont() {
		return bitmapFont;
	}

	public float getScale() {
		return scale;
	}
}
