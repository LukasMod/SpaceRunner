package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.LabelYellow;
import model.SpaceRunnerButton;
import model.TextFieldYellow;
import model.highscores.HighscoreManager;
import model.highscores.Score;

import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class EndViewManager {
    private AnchorPane endPane;
    private Scene endScene;
    private Stage endStage;
    private Stage mainStage;
    private TextFieldYellow textFieldName;
    private final String FONT_PATH = "src/main/resources/kenvector_future.ttf";
    private static final int END_WIDTH = 400;
    private static final int End_HEIGHT = 200;

    private HighscoreManager highscoreManager;
    private GameViewManager gameViewManager;


    public EndViewManager(GameViewManager gameViewManager, HighscoreManager highscoreManager) {
        this.gameViewManager = gameViewManager;
        this.highscoreManager = highscoreManager;
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
        createTextField();
        endStage.show();
    }

    private void createTextField() {
        textFieldName = new TextFieldYellow(300, 50, "Write your name here");
        textFieldName.setLayoutX(50);
        textFieldName.setLayoutY(50);
        textFieldName.setAlignment(Pos.CENTER);
        endPane.getChildren().add(textFieldName);

        Pattern pattern = Pattern.compile(".{0,15}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        textFieldName.setTextFormatter(formatter);

    }

    public String getTextField() {
        return textFieldName.getText();
    }

    private void createBackground() {
        Image backgroundImage = new Image("purple.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        endPane.setBackground(new Background(background));
    }

    private void createButton() {
        SpaceRunnerButton saveButton = new SpaceRunnerButton("Save");

        saveButton.setLayoutX(100);
        saveButton.setLayoutY(120);
        endPane.getChildren().add(saveButton);

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    highscoreManager.saveNewScore(getNewScore());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                endStage.close();
                mainStage.show();
            }
        });
    }

    public Score getNewScore() {
        Score newScore = new Score(getTextField(), gameViewManager.getPoints());
        return newScore;
    }
}

