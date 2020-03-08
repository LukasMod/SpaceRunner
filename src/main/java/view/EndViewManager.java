package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.SHIP;
import model.SpaceRunnerButton;

import java.util.Random;

public class EndViewManager {
    private AnchorPane endPane;
    private Scene endScene;
    private Stage endStage;
    private Stage mainStage;
    private final String FONT_PATH = "src/main/resources/kenvector_future.ttf";
    private static final int END_WIDTH = 400;
    private static final int End_HEIGHT = 200;

    private SpaceRunnerButton saveButton;

    public EndViewManager() {
        initializeStage();
    }

    public void initializeStage() {
        endPane = new AnchorPane();
        endScene = new Scene(endPane, END_WIDTH, End_HEIGHT);
        endStage = new Stage();
        endStage.setResizable(false);
        endStage.setScene(endScene);
        endStage.setTitle("Gameover");
    }

    public void createWindow(Stage stage) {
        this.mainStage = stage;
        this.mainStage.hide();
        createBackground();
        createButton();
        endStage.show();
    }

    private void createBackground() {
        Image backgroundImage = new Image("purple.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        endPane.setBackground(new Background(background));
    }

    private void createButton() {
        SpaceRunnerButton saveButton = new SpaceRunnerButton("Save");
        saveButton.setLayoutX(110);
        saveButton.setLayoutY(150);
        endPane.getChildren().add(saveButton);

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                endStage.close();
                mainStage.show();
            }
        });
    }
}

