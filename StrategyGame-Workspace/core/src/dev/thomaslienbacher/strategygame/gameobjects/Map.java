package dev.thomaslienbacher.strategygame.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.kotcrab.vis.ui.util.ColorUtils;
import dev.thomaslienbacher.strategygame.assets.Data;

import java.util.ArrayList;

/**
 * @author Thomas Lienbacher
 */
public class Map {

    private static final Color BACKGROUND = Color.valueOf("00C7FFFF");

    private ArrayList<Province> provinces = new ArrayList<Province>();
    private ArrayList<State> states = new ArrayList<State>();
    private Texture colorcode;
    private Texture mapTexture;

    public Map(Texture colorcodeTexture) {
        this.colorcode = colorcodeTexture;

        JsonValue root = new JsonReader().parse(Gdx.files.internal(Data.MAPDATA_JSON));

        JsonValue provinces = root.get("provinces");
        JsonValue states = root.get("states");

        for(JsonValue j : provinces) {
            int id = Integer.parseInt(j.getString("id"));
            int[] colorcode = j.get("colorcode").asIntArray();
            this.provinces.add(new Province(id, colorcode));
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

        updateMapTexture();
    }

    private void updateMapTexture() {
        Pixmap pixmap = new Pixmap(colorcode.getWidth(), colorcode.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(BACKGROUND);
        pixmap.fill();

        if(!colorcode.getTextureData().isPrepared()) colorcode.getTextureData().prepare();
        Pixmap cc = colorcode.getTextureData().consumePixmap();
        pixmap.drawPixmap(cc, 0, 0);
        cc.dispose();

        //draw states
        for(int x = 0; x < pixmap.getWidth(); x++) {
            for(int y = 0; y < pixmap.getHeight(); y++) {
                int c = pixmap.getPixel(x, y);

                if((c >> 24) == 2) {
                    pixmap.setColor(provinces.get((c >> 16) & 0xFF).getOccupier().getColor());
                    pixmap.drawPixel(x, y);
                }
            }
        }

        if(mapTexture == null) mapTexture = new Texture(pixmap);
        else mapTexture.draw(pixmap, 0, 0);
        pixmap.dispose();
    }

    public void render(SpriteBatch batch) {
        batch.draw(mapTexture, 0, 0);
    }

    public void dispose() {
        mapTexture.dispose();
    }

    public State getState(int x, int y) {
        return getProvince(x, y).getOccupier();
    }

    public Province getProvince(int x, int y) {
        if(!colorcode.getTextureData().isPrepared()) colorcode.getTextureData().prepare();
        Pixmap cc = colorcode.getTextureData().consumePixmap();

        int c = cc.getPixel(x, cc.getHeight() - y);
        Province p = provinces.get((c >> 16) & 0xFF);

        cc.dispose();

        return p;
    }
}
