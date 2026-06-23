package Viewctrl;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Canvas canvas;

    @FXML
    private Button startBtn;

    private Model model;
    private FileHandler fileManager;
    private List<Enemy> enemies = new ArrayList<>();


    //Enemy Variablen
    private double spawnTimer = 0;
    private double spawnDelay = 0.2;
    private int enemiesToSpawn = 100;
    private AnimationTimer gameLoop;
    private long lastTime = 0;
    private int enemySpeed = 80;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();
        fileManager = new FileHandler();
        canvas.setWidth(1480);
        canvas.setHeight(1020);

        draw();

        try {
            FileHandler.loadPath("path.txt", model);
        } catch (FileNotFoundException e) {
            showError("Datei nicht gefunden", "path.txt wurde nicht gefunden.");
        } catch (IOException e) {
            showError("Fehler", "Die Datei konnte nicht gelesen werden.");
        }
        drawTrack();
    }
    private void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
        startBtn.setVisible(false);
    }
    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(215,255,215));
        gc.fillRect(0, 0, 1480, 1020);
    }

    public void drawTrack() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(65);

        List<Double> xPoints = model.getXPoints();
        List<Double> yPoints = model.getYPoints();

        if (!xPoints.isEmpty()) {

            gc.beginPath();
            gc.moveTo(xPoints.get(0), yPoints.get(0));

            for (int i = 1; i < xPoints.size(); i++) {
                gc.lineTo(xPoints.get(i), yPoints.get(i));
            }

            gc.stroke();
        }

    }

    private void render() {
        draw();
        drawTrack();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            for (Enemy e : enemies) {
                gc.setFill(Color.RED);
                gc.fillOval(e.getX() - 5, e.getY() - 5, 30, 30);
        }
    }

    @FXML
    void startGame(ActionEvent event) {
        startBtn.setVisible(false);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                List<Double> xPoints = model.getXPoints();
                List<Double> yPoints = model.getYPoints();

                // SPAWN LOGIK
                spawnTimer += deltaTime;

                if (enemiesToSpawn > 0 && spawnTimer >= spawnDelay) {
                    enemies.add(new Enemy(
                            xPoints.get(0),
                            yPoints.get(0),
                            enemySpeed
                    ));

                    spawnTimer = 0;
                    enemiesToSpawn--;
                }

                //UPDATE ALL ENEMIES
                for (Enemy e : enemies) {
                    e.update(deltaTime, xPoints, yPoints);
                }

                double endX = xPoints.get(xPoints.size() - 1);
                double endY = yPoints.get(yPoints.size() - 1);

                enemies.removeIf(e -> {
                    double dx = e.getX() - endX;
                    double dy = e.getY() - endY;

                    return Math.sqrt(dx * dx + dy * dy) < enemySpeed/4;
                });
                render();
            }
        };

        gameLoop.start();
    }

}