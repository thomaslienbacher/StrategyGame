package dev.thomaslienbacher.strategygame.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dev.thomaslienbacher.strategygame.assets.Data;

import java.util.ArrayList;

/**
 * @author Thomas Lienbacher
 */
public class Map {

    public static final float MAP_PADDING = 0.5f;//percent of how much padding is between the background and the map

    private Array<Province> provinces = new Array<Province>();
    private ArrayList<State> states = new ArrayList<State>();
    private Rectangle bounds;
    private float padding = 0;

    public Map() {
        JsonValue root = new JsonReader().parse(Gdx.files.internal(Data.MAP_DATA_JSON));

        JsonValue provinces = root.get("provinces");

        for(JsonValue j : provinces) {
            int id = Integer.parseInt(j.getString("id"));

            String name = j.getString("name");
            Texture emblem = new Texture(Data.EMBLEM_PATH + j.getString("emblem") + ".png");
            int[] color = j.get("color").asIntArray();
            float[] vertices = j.get("vertices").asFloatArray();
            short[] triangles = j.get("triangles").asShortArray();

            Province p = new Province(id, emblem, vertices, triangles);
            this.provinces.add(p);
            this.states.add(new State(id, name, emblem, color, p));
        }

        bounds = new Rectangle(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

        for(Province p : this.provinces) {
            Rectangle r = p.getPolygon().getBoundingRectangle();
            if(r.x < bounds.x) bounds.x = r.x;
            if(r.y < bounds.y) bounds.y = r.y;
            if(r.width + r.x > bounds.width) bounds.width = r.width + r.x;
            if(r.height + r.y > bounds.height) bounds.height = r.height + r.y;
        }

        padding = (bounds.width > bounds.height ? bounds.width : bounds.height) * MAP_PADDING;
    }


    public void render(PolygonSpriteBatch batch) {
        for(State s : states) {
            s.draw(batch);
        }
    }

    public void dispose() {
        for(State s : states) {
            s.dispose();
        }
    }

    public State getState(int x, int y) {
        Province p = getProvince(x, y);
        return p == null ? null : p.getOccupier();
    }

    public Province getProvince(int x, int y) {
        for(Province p : provinces) {
            if(p.getPolygon().contains(x, y)) return p;
        }

        return null;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Array<Province> getProvinces() {
        return provinces;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public float getAbsolutePadding() {
        return padding;
    }
}
