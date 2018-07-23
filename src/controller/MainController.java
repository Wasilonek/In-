package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Data;
import model.GrainGrowth;
import model.MeshDrawing;


public class MainController {

    @FXML
    Canvas canvas;

    @FXML
    TextField widthField;

    @FXML
    TextField heightField;

    @FXML
    TextField numberOfGrainsField;

    private GraphicsContext graphicsContext;
   // MeshDrawing meshDrawing = new MeshDrawing();
    GrainGrowth grainGrowth;


    @FXML
    void initialize() {
        grainGrowth = new GrainGrowth();
        graphicsContext = canvas.getGraphicsContext2D();
        grainGrowth.drawHex(graphicsContext);
    }

    @FXML
    public void setSizeAction() {

        String grainW = widthField.getText();
        String grainH = heightField.getText();
        try {
            if (!grainW.matches("\\d*")) {
                wrongFormatAlertMessage();
                canvas.setWidth(Data.getMaxCanvasWidth());
            } else {
                canvas.setWidth(Double.valueOf(widthField.getText()));
            }
        } catch (Exception ignored) {
        }

        try {
            if (!grainH.matches("\\d*")) {
                wrongFormatAlertMessage();
                canvas.setHeight(Data.getHexSize());
            } else {
                canvas.setHeight(Double.valueOf(heightField.getText()));
            }
        } catch (Exception ignored) {
        }

        if (canvas.getWidth() > Data.getMaxCanvasWidth() || canvas.getHeight() > Data.getMaxCanvasHeight()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Max size is 1000x1000!");

            alert.showAndWait();
        }
        refreshCanvas();
    }

    @FXML
    public void refreshCanvas() {
        clearCanvas();
        grainGrowth.drawHex(graphicsContext);
    }

    @FXML
    public void randGrainsAction(){
       //clearCanvas();
        grainGrowth.randomGrains(5);
        refreshCanvas();
        //grainGrowth.drawHex(graphicsContext);
    }

    @FXML
    public void clearAction(){
        clearCanvas();
    }

    public void wrongFormatAlertMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Wrong format");
        alert.setContentText("Write only number!");
        alert.showAndWait();
    }

    public void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}

