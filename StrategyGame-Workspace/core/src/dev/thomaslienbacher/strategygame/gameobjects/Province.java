package dev.thomaslienbacher.strategygame.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;

/**
 * @author Thomas Lienbacher
 */
public class Province {

    private int id;
    private Polygon polygon;
    private PolygonRegion polygonRegion;
    private State occupier;

    public static TextureRegion t = new TextureRegion(new Texture("austria_old_2.png"));

    public Province(int id, float[] vertices, short[] triangles) {
        this.id = id;
        this.polygon = new Polygon(vertices);

        /*short[] tris = new short[((vertices.length / 2) - 2) * 3];
        int c = 0;

        for(int i = 1; i < (vertices.length / 2) -1;) {
            tris[c++] = 0;
            tris[c++] = (short) i++;
            tris[c++] = (short) i;
        }*/

        this.polygonRegion = new PolygonRegion(t, vertices, triangles);
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
        this.occupier = occupier;
    }

    public PolygonRegion getPolygonRegion() {
        return polygonRegion;
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
                ", occupier=" + occupier.getId() + " / " + occupier.getName() +
                '}';
    }
}
