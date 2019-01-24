package dev.thomaslienbacher.strategygame.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @author Thomas Lienbacher
 */
public class PolygonUtils {

    public static Circle getVisualCenter(Polygon polygon) {
        final int TEST_SIZE = 24; // a*2 x a*2 grid of points
        final int ITERATIONS = 5;
        final float DECREASE = 1.0f / (float) Math.sqrt(2);

        Rectangle bounding = polygon.getBoundingRectangle();
        Vector2 bestPoint = new Vector2(bounding.x + bounding.width / 2, bounding.y + bounding.height / 2);
        float maxWidth = bounding.width;
        float maxHeight = bounding.height;

        for (int i = 0; i < ITERATIONS; i++) {
            ArrayList<Vector2> allPoints = new ArrayList<Vector2>();

            //generate test points
            for (int x = -TEST_SIZE; x < TEST_SIZE; x++) {
                for (int y = -TEST_SIZE; y < TEST_SIZE; y++) {

                    float nx = bestPoint.x + (x * maxWidth / TEST_SIZE);
                    float ny = bestPoint.y + (y * maxHeight / TEST_SIZE);

                    allPoints.add(new Vector2(nx, ny));
                }
            }

            //remove testpoints that are outside
            ArrayList<Vector2> testPoints = new ArrayList<Vector2>();

            for (Vector2 v : allPoints) {
                if (polygon.contains(v)) testPoints.add(v);
            }

            //test points
            TreeMap<Float, Vector2> sortedPoints = new TreeMap<Float, Vector2>();

            for (Vector2 v : testPoints) {
                sortedPoints.put(distanceToPolygon(polygon, v), v);
            }

            //get best one
            bestPoint = sortedPoints.lastEntry().getValue();
            maxWidth *= DECREASE;
            maxHeight *= DECREASE;
        }

        float d = distanceToPolygon(polygon, bestPoint);
        return new Circle(bestPoint.x, bestPoint.y, d);
    }

    //get smallest distance from point to polygon --from polylabel.js
    private static float distanceToPolygon(Polygon polygon, Vector2 point) {
        float minDistSq = Float.POSITIVE_INFINITY;

        for (int i = 0; i < polygon.getVertices().length; i++) {
            float ax = polygon.getVertices()[i++ % polygon.getVertices().length];
            float ay = polygon.getVertices()[i++ % polygon.getVertices().length];
            float bx = polygon.getVertices()[i++ % polygon.getVertices().length];
            float by = polygon.getVertices()[i % polygon.getVertices().length];

            float d = getSegDistSq(point, new Vector2(ax, ay), new Vector2(bx, by));

            if (d < minDistSq) minDistSq = d;
        }

        return (float) Math.sqrt(minDistSq);
    }

    //get squared distance from a point to a segment --from polylabel.js
    private static float getSegDistSq(Vector2 point, Vector2 a, Vector2 b) {
        float x = a.x;
        float y = a.y;
        float dx = b.x - x;
        float dy = b.y - y;

        if (dx != 0 || dy != 0) {
            float t = ((point.x - x) * dx + (point.y - y) * dy) / (dx * dx + dy * dy);

            if (t > 1) {
                x = b.x;
                y = b.y;

            } else if (t > 0) {
                x += dx * t;
                y += dy * t;
            }
        }

        dx = point.x - x;
        dy = point.y - y;

        return dx * dx + dy * dy;
    }
}
