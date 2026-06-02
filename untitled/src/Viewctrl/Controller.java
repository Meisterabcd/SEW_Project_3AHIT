package Viewctrl;

import Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Canvas canvas;
    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();
        canvas.setWidth(1480);
        canvas.setHeight(1020);
        draw();
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(model.getColor());
        gc.fillRect(0, 0, 1480, 1020);
    }
}