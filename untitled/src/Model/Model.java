package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final List<Double> xPoints = new ArrayList<Double>();
    private final List<Double> yPoints = new ArrayList<Double>();
    
    public Model() {

    }

    public void addPoint(double x, double y) {
    xPoints.add(x);
    yPoints.add(y);
    }
    public List<Double> getXPoints() {
        return xPoints;
    }
    public List<Double> getYPoints() {
        return yPoints;
    }
    public void clear() {
        xPoints.clear();
        yPoints.clear();
    }
}
