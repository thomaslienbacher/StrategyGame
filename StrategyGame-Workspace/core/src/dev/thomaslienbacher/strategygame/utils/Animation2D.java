package dev.thomaslienbacher.strategygame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

/**
 * This class manages is like a sprite
 *
 * @author Thomas Lienbacher
 */
public class Animation2D {

    private Animation animation;
    private float time = 0;
    private TextureAtlas textureAtlas;
    private Vector2 position;
    private float scale = 1;
    private float rotation = 0;

    public Animation2D(TextureAtlas textureAtlas, float frameDuration, boolean looping, Vector2 position) {
        this.textureAtlas = textureAtlas;
        this.position = position;

        for (TextureRegion t : this.textureAtlas.getRegions()) {
            t.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        this.animation = new Animation<TextureRegion>(frameDuration, this.textureAtlas.getRegions(), looping ? PlayMode.LOOP : PlayMode.NORMAL);
    }

    public void render(SpriteBatch batch) {
        Sprite s = getKeySprite();
        s.setScale(scale);
        s.setRotation(rotation);
        s.setPosition(position.x, position.y);
        s.draw(batch);
    }

    public void update(float delta) {
        time += delta;
    }

    public Sprite getKeySprite() {
        return new Sprite((TextureRegion) animation.getKeyFrame(time));
    }

    public void setFrameDuration(float time) {
        animation.setFrameDuration(time);
    }

    public float getFrameDuration() {
        return animation.getFrameDuration();
    }

    public void setPlayMode(PlayMode mode) {
        animation.setPlayMode(mode);
    }

    public Animation getAnimation() {
        return animation;
    }

    public float getTime() {
        return time;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void setPosition(Vector2 v) {
        this.position.x = v.x;
        this.position.y = v.y;
    }

    public void setPositionX(float x) {
        this.position.x = x;
    }

    public void setPositionY(float y) {
        this.position.y = y;
    }

    public void translate(float x, float y) {
        this.position.x += x;
        this.position.y += y;
    }

    public void translate(Vector2 v) {
        this.position.x += v.x;
        this.position.y += v.y;
    }

    public void translateX(float x) {
        this.position.x += x;
    }

    public void translateY(float y) {
        this.position.y += y;
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
    }

    public void rotate(float degrees) {
        this.rotation += degrees;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void scaleMul(float mul) {
        this.scale *= scale;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Vector2 getPosition() {
        return position;
    }
}
