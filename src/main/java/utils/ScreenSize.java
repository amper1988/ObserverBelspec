package utils;


import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenSize {
    private static ScreenSize instance;
    private double maxX;
    private double maxY;
    private double minX;
    private double minY;


    public static ScreenSize getInstance() {
        if (instance == null) {
            instance = new ScreenSize();
        }
        return instance;
    }

    private ScreenSize() {
        refresh();
    }

    private void refresh() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        minX = bounds.getMinX();
        minY = bounds.getMinY();
        maxX = bounds.getWidth();
        maxY = bounds.getHeight();
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    private double getMinX() {
        return minX;
    }

    private double getMinY() {
        return minY;
    }

    public void maximizedStage(Stage stage) {
        if (stage != null) {
            refresh();
            stage.setX(getMinX());
            stage.setY(getMinY());
            stage.setWidth(getMaxX());
            stage.setHeight(getMaxY());
        }

    }
}
