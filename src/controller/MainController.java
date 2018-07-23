package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.MeshDrawing;


public class MainController {

    @FXML
    Canvas canvas;

    private GraphicsContext graphicsContext;
    MeshDrawing meshDrawing = new MeshDrawing();


    @FXML
    void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        meshDrawing.drawHex(graphicsContext);
    }
}

