package dev.thomaslienbacher.strategygame.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import dev.thomaslienbacher.strategygame.utils.PolygonUtils;

/**
 * @author Thomas Lienbacher
 */
public class Province {

    public static final float EMBLEM_SIZE_MOD = 1.35f; //size modifier to get the emblem the right size
    public static final float MAX_EMBLEM_SIZE = 45;

    private int id;
    private Polygon polygon;
    private Texture emblem;
    private PolygonRegion polygonRegion;
    private Circle center;
    private State occupier;

    public Province(int id, Texture emblem, float[] vertices, short[] triangles) {
        this.id = id;
        this.polygon = new Polygon(vertices);
        this.emblem = emblem;
        this.polygonRegion = new PolygonRegion(new TextureRegion(emblem), vertices, triangles);
        this.center = PolygonUtils.getVisualCenter(this.polygon);
    }

    public void draw(PolygonSpriteBatch batch) {
        batch.draw(polygonRegion, 0,0);

        Rectangle r = polygon.getBoundingRectangle();
        float w = 0, h = 0;

        if(emblem.getWidth() > emblem.getHeight()) {
            w = center.radius * EMBLEM_SIZE_MOD;
            if(w > MAX_EMBLEM_SIZE) w = MAX_EMBLEM_SIZE;
            h = emblem.getHeight() * (w / emblem.getWidth());
        } else {
            h = center.radius * EMBLEM_SIZE_MOD;
            if(h > MAX_EMBLEM_SIZE) h = MAX_EMBLEM_SIZE;
            w = emblem.getWidth() * (h / emblem.getHeight());
        }

        float x = center.x - w / 2;
        float y = center.y - h / 2;

        batch.draw(emblem, x, y, w, h);
    }

    public int getId() {
        return id;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public State getOccupier() {
        return occupier;
    }

    public void setOccupier(State occupier) {
        this.emblem = occupier.getEmblem();
        polygonRegion.getRegion().setRegion(occupier.getBackground());
        this.occupier = occupier;
    }

    public PolygonRegion getPolygonRegion() {
        return polygonRegion;
    }

    public Circle getCenter() {
        return center;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Province province = (Province) o;

        return id == province.id;
    }
}
