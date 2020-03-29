package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import view.components.SpaceRunnerButton;
import view.components.TextFieldYellow;
import model.HighscoreManager;
import model.Score;

import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class EndViewManager {
    private AnchorPane endPane;
    private Stage endStage;

    private TextFieldYellow textFieldName;

    private static final int END_WIDTH = 400;
    private static final int End_HEIGHT = 200;


    private HighscoreManager highscoreManager;
    private GameViewManager gameViewManager;
    private ViewManager viewManager;


    public EndViewManager(GameViewManager gameViewManager, HighscoreManager highscoreManager) {
        this.gameViewManager = gameViewManager;
        this.highscoreManager = highscoreManager;
        initializeStage();
    }

    public void initializeStage() {
        endPane = new AnchorPane();
        Scene endScene = new Scene(endPane, END_WIDTH, End_HEIGHT);
        endStage = new Stage();
        endStage.setResizable(false);
        endStage.setScene(endScene);
        endStage.setTitle("Gameover");
    }

    public void createWindow() {
        createBackground();
        createButton();
        createTextField();
        endStage.show();
    }

    private void createTextField() {
        textFieldName = new TextFieldYellow(300, 50, "end.view.name");
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
        SpaceRunnerButton saveButton = new SpaceRunnerButton("save.end.view");

        saveButton.setLayoutX(100);
        saveButton.setLayoutY(120);
        endPane.getChildren().add(saveButton);

        saveButton.setOnAction(event -> {

            try {
                highscoreManager.saveNewScore(getNewScore());
            } catch (IOException e) {
                e.printStackTrace();
            }

            endStage.close();
            viewManager = new ViewManager();
            viewManager.getMainStage().show();
        });
    }

    public Score getNewScore() {
        return new Score(getTextField(), gameViewManager.getPoints());
    }
}

