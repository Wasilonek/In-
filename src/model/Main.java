package model;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Kamil on 2018-07-17.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/view/MainView.fxml"));
        Pane pane = fxmlLoader.load();

        MeshDrawing meshDrawing = new MeshDrawing();


        Scene scene = new Scene(pane);
        //primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Grains");


       // meshDrawing.drawHex();

        primaryStage.show();
        primaryStage.setResizable(false);
    }
}
