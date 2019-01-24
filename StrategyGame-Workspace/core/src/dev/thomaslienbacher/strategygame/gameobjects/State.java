package dev.thomaslienbacher.strategygame.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import java.util.ArrayList;

/**
 * @author Thomas Lienbacher
 */
public class State {

    private int id;
    private String name;
    private Color color;
    private Texture background;
    private Texture emblem;
    private ArrayList<Province> provinces;

    public State(int id, String name, Texture emblem, int[] color, Province province) {
        this.id = id;
        this.name = name;
        this.color = new Color((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, 1);
        this.emblem = emblem;
        this.provinces = new ArrayList<Province>();
        this.provinces.add(province);

        Pixmap bg = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bg.setColor(this.color);
        bg.fill();
        this.background = new Texture(bg);
        bg.dispose();

        for (Province p : this.provinces) {
            p.setOccupier(this);
        }
    }

    public void draw(PolygonSpriteBatch batch) {
        for (Province p : provinces) {
            p.draw(batch);
        }
    }

    public void dispose() {
        emblem.dispose();
        background.dispose();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Province> getProvinces() {
        return provinces;
    }

    public Texture getBackground() {
        return background;
    }

    public Texture getEmblem() {
        return emblem;
    }
}
