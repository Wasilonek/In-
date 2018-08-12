package model;

import controller.MainController;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Kamil on 2018-08-12.
 */
public class GrainTask extends Task {
    private GrainGrowth grainGrowth;
    private MainController mainController;
    private GraphicsContext graphicsContext;
    private boolean stopStatus;
    private int speed;

    public GrainTask(GrainGrowth grainGrowth, GraphicsContext graphicsContext , MainController mainController) {
        this.grainGrowth = grainGrowth;
        this.mainController = mainController;
        this.graphicsContext = graphicsContext;
        stopStatus = false;
        speed = 500;
    }

    @Override
    protected Object call() throws Exception {
        while (!stopStatus) {
            grainGrowth.drawHex(graphicsContext);
            if(grainGrowth.setNeigboursForEachGrain()){
                stopStatus = true;
                mainController.setOneDrawnigThread(true);
                mainController.setStartOn(false);
            }

            Thread.sleep(speed);
        }
        return 1;
    }

    public void setStopStatus(boolean stopStatus) {
        this.stopStatus = stopStatus;
    }
}
