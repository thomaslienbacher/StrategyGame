package dev.thomaslienbacher.strategygame.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dev.thomaslienbacher.strategygame.assets.Data;

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

    public Array<Province> getProvinces() {
        return provinces;
    }

    public ArrayList<State> getStates() {
        return states;
    }
}
