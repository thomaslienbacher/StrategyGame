package dev.thomaslienbacher.strategygame.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Thomas Lienbacher
 */
public class Province {

    private int id;
    private Color colorcode;
    private int colorcodeInt;
    private State occupier;
    private Pixmap selectedOverlay;

    public Province(int id, int[] colorcode) {
        this.id = id;
        this.colorcode = new Color((float) colorcode[0] / 255, (float) colorcode[1] / 255, (float) colorcode[2] / 255, 1);
        this.colorcodeInt = Color.toIntBits(colorcode[0], colorcode[1], colorcode[2], 255);
    }

    public int getId() {
        return id;
    }

    public Color getColorcode() {
        return colorcode;
    }

    public State getOccupier() {
        return occupier;
    }

    public void setOccupier(State occupier) {
        this.occupier = occupier;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Province province = (Province) o;

        return id == province.id;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", colorcode=" + colorcode +
                ", occupier=" + occupier.getId() + " / " + occupier.getName() +
                '}';
    }

    public static Province getProvinceFromList(ArrayList<Province> provinces, int id) {
        for(Province p : provinces) {
            if(p.id == id) return p;
        }

        return null;
    }
}
