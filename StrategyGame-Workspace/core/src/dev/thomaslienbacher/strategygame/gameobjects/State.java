package dev.thomaslienbacher.strategygame.gameobjects;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

/**
 * @author Thomas Lienbacher
 */
public class State {

    private int id;
    private String name;
    private Color color;
    private int colorInt;
    private ArrayList<Province> provinces;

    public State(int id, String name, int[] color, ArrayList<Province> provinces) {
        this.id = id;
        this.name = name;
        this.color = new Color((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, 1);
        this.provinces = provinces;
        this.colorInt = Color.toIntBits(color[0], color[1], color[2], 255);

        for(Province p : this.provinces) {
            p.setOccupier(this);
        }
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

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", provinces=" + provinces +
                '}';
    }
}
