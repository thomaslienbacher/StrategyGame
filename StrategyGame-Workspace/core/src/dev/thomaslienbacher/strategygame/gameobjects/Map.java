package dev.thomaslienbacher.strategygame.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dev.thomaslienbacher.strategygame.assets.Data;
import dev.thomaslienbacher.strategygame.utils.Utils;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Thomas Lienbacher
 */
public class Map {

    private static final Color BACKGROUND = Color.valueOf("00C7FFFF");

    private Array<Province> provinces = new Array<Province>();
    private ArrayList<State> states = new ArrayList<State>();

    public Map() {
        JsonValue root = new JsonReader().parse(Gdx.files.internal(Data.MAPDATA_JSON));

        JsonValue provinces = root.get("provinces");
        JsonValue states = root.get("states");

        for(JsonValue j : provinces) {
            int id = Integer.parseInt(j.getString("id"));
            float[] vertices = j.get("vertices").asFloatArray();
            short[] triangles = j.get("triangles").asShortArray();
            this.provinces.add(new Province(id, vertices, triangles));
        }

        for(JsonValue j : states) {
            int id = Integer.parseInt(j.getString("id"));
            String name = j.getString("name");
            int[] color = j.get("color").asIntArray();
            int[] occupied = j.get("occupied").asIntArray();

            ArrayList<Province> pl = new ArrayList<Province>(occupied.length);

            for(int i = 0; i < occupied.length; i++) {
                pl.add(this.provinces.get(occupied[i]));
            }

            this.states.add(new State(id, name, color, pl));
        }
    }


    public void render(PolygonSpriteBatch batch) {
        for(Province p : provinces) {
            batch.draw(p.getPolygonRegion(), 0, 0);
        }
    }

    public void dispose() {

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

    public Array<Province> getProvinces() {
        return provinces;
    }

    public ArrayList<State> getStates() {
        return states;
    }
}
