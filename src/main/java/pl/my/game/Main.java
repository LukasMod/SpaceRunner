package pl.my.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.my.game.view.ViewManager;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        try{
            ViewManager manager = new ViewManager();
            stage = manager.getMainStage();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
