package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import model.Data;
import model.GrainGrowth;

import javax.imageio.ImageIO;
import java.io.File;


public class MainController {

    @FXML
    Canvas canvas;

    @FXML
    TextField widthField;

    @FXML
    TextField heightField;

    @FXML
    TextField numberOfGrainsField;

    @FXML
    Label widthLabel, heightLabel;

    @FXML
    ChoiceBox<String> grainSizeChoicebox;


    private GraphicsContext graphicsContext;
    GrainGrowth grainGrowth;

    private WritableImage writableImage;


    @FXML
    void initialize() {
        grainGrowth = new GrainGrowth();

        setGrainSizeChoiceBoxItems();
        grainSizeChoicebox.setValue("4");

        numberOfGrainsField.setText("10000");

        graphicsContext = canvas.getGraphicsContext2D();

        Data.setHexHeight(Integer.parseInt(grainSizeChoicebox.getValue()));
        grainGrowth.changeGridSize((int) canvas.getWidth(), (int) canvas.getHeight());

        setCanvasSizeLabels();
    }

    private void setGrainSizeChoiceBoxItems() {
        grainSizeChoicebox.getItems().addAll("4", "8", "12");
    }

    public void setCanvasSizeLabels() {
        widthLabel.setText(String.valueOf(canvas.getWidth()));
        heightLabel.setText(String.valueOf(canvas.getHeight()));
    }

    @FXML
    public void setSizeAction() {

        String grainW = widthField.getText();
        String grainH = heightField.getText();
        try {
            if ((!grainW.matches("\\d*"))) {
                wrongFormatAlertMessage();

            } else {
                if (Double.valueOf(widthField.getText()) > 1000) {
                    showToBigCanvasAlert();
                    canvas.setWidth(Data.getMaxCanvasWidth());
                } else {
                    canvas.setWidth(Double.valueOf(widthField.getText()));
                }
            }
        } catch (Exception e) {
            System.out.println("Bład z szerokością");
        }

        try {
            if (!grainH.matches("\\d*")) {
                wrongFormatAlertMessage();

            } else {
                if (Double.valueOf(heightField.getText()) > 1000) {
                    showToBigCanvasAlert();
                    canvas.setHeight(Data.getMaxCanvasHeight());
                } else {
                    canvas.setHeight(Double.valueOf(heightField.getText()));
                }
            }
        } catch (Exception e) {
            System.out.println("Bład z wysokościa");
        }

        setCanvasSizeLabels();

        Data.setHexHeight(Integer.parseInt(grainSizeChoicebox.getValue()));
        grainGrowth.changeGridSize((int) canvas.getWidth(), (int) canvas.getHeight());
        clearCanvas();
    }

    public void showToBigCanvasAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setContentText("Max size is 1000x1000!");
        alert.showAndWait();
    }

    @FXML
    public void refreshCanvas() {
        clearCanvas();
        grainGrowth.drawHex(graphicsContext, Integer.parseInt(grainSizeChoicebox.getValue()));
    }

    @FXML
    public void randGrainsAction() {
        Data.setHexHeight(Integer.parseInt(grainSizeChoicebox.getValue()));
        grainGrowth.changeGridSize((int) canvas.getWidth(), (int) canvas.getHeight());
        grainGrowth.randomGrains(Integer.parseInt(numberOfGrainsField.getText()));
        refreshCanvas();
    }

    @FXML
    public void onePeriodAction() {
        grainGrowth.setNeigboursForEachGrain();
        grainGrowth.drawHex(graphicsContext, Integer.parseInt(grainSizeChoicebox.getValue()));
    }

    @FXML
    public void clearAction() {
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

    public void saveImageAction() {
        writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);

        File file = new File("CanvasImage.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (Exception s) {
        }
    }

}

