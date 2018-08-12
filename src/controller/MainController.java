package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import model.GrainGrowth;
import model.GrainTask;

import javax.imageio.ImageIO;
import java.io.File;


public class MainController {

    @FXML
    Canvas canvas;

    @FXML
    TextField numberOfGrainsField;


    private GraphicsContext graphicsContext;
    private GrainGrowth grainGrowth;
    private WritableImage writableImage;

    private boolean isStartOn, isOneDrawnigThread;

    private GrainTask grainTask;

    @FXML
    void initialize() {
        grainGrowth = new GrainGrowth();

        isStartOn = false;
        isOneDrawnigThread = true;

        graphicsContext = canvas.getGraphicsContext2D();

        // ustaw wstępną ilość ziaren do wylosowania
        numberOfGrainsField.setText("40");

//        grainGrowth.randColorForEveryId(Integer.parseInt(numberOfGrainsField.getText()));
    }

    @FXML
    public void refreshCanvas() {
        clearCanvas();
        grainGrowth.drawHex(graphicsContext);
    }

    @FXML
    public void randGrainsAction() {
        grainGrowth.randColorForEveryId(Integer.parseInt(numberOfGrainsField.getText()));
        grainGrowth.randomGrains(Integer.parseInt(numberOfGrainsField.getText()));
        refreshCanvas();
    }

    @FXML
    public void onePeriodAction() {
        grainGrowth.setNeigboursForEachGrain();
        grainGrowth.drawHex(graphicsContext);
    }

    @FXML
    public void growthStartAction() {
        isStartOn = true;
        if (isOneDrawnigThread) {
            grainTask = new GrainTask(grainGrowth, graphicsContext , this);
            Thread thread = new Thread(grainTask);
            thread.setDaemon(true);
            thread.start();
            isOneDrawnigThread = false;
        }
    }

    @FXML
    public void growthStopAction() {
        isStartOn = false;
        isOneDrawnigThread = true;
        try {
            grainTask.setStopStatus(true);
        } catch (NullPointerException e) {
            System.out.println("Rozrost nie wystartował");
        }
    }

    @FXML
    public void clearAction() {
        clearCanvas();
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

    public void setStartOn(boolean startOn) {
        isStartOn = startOn;
    }

    public void setOneDrawnigThread(boolean oneDrawnigThread) {
        isOneDrawnigThread = oneDrawnigThread;
    }
}

