package view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.LANGUAGE;
import view.components.InfoLabel;
import model.SHIP;
import view.components.LabelYellow;
import view.components.SpaceRunnerButton;
import view.components.SpaceRunnerSubScene;
import model.HighscoreManager;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 680;
    private AnchorPane mainPane;
    private Stage mainStage;

    public final static int MENU_BUTTONS_START_X = 50;
    public final static int MENU_BUTTONS_START_Y = 150;

    private SpaceRunnerSubScene creditsSubScene;
    private SpaceRunnerSubScene scoresSubScene;
    private SpaceRunnerSubScene shipChooserSubScene;
    private SpaceRunnerSubScene optionsSubScene;

    private SpaceRunnerSubScene sceneToHide;

    private HighscoreManager highscoreManager = new HighscoreManager();
    private ScoreboardSceneManager scoreboardSceneManager = new ScoreboardSceneManager(highscoreManager);
    private GameViewManager gameViewManager = new GameViewManager(highscoreManager);

    List<SpaceRunnerButton> menuButtons;
    List<ShipPickerSceneManager> shipPickerSceneManagerList;
    List<LanguageSceneManager> languagePickerSceneManagerList;

    private SHIP chosenShip;
    private LANGUAGE chosenLanguage;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        Scene mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainScene.getStylesheets().add("Style.css");

        mainStage = new Stage();
        mainStage.setScene(mainScene);

        createSubScene();
        createButtons();
        createBackground();
        createLogo();

    }

    private void showSubScene(SpaceRunnerSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    public void createSubScene() {
        creditsSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(creditsSubScene);

        createShipChooserSubScene();
        createScoresSubScene();
        createOptionsChooserSubScene();
    }

    private void createShipChooserSubScene() {
        shipChooserSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(shipChooserSubScene);
        InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
        chooseShipLabel.setLayoutX(110);
        chooseShipLabel.setLayoutY(25);
        shipChooserSubScene.getPane().getChildren().add(chooseShipLabel);
        shipChooserSubScene.getPane().getChildren().add(createShipsToChoose());
        shipChooserSubScene.getPane().getChildren().add(createButtonToStart(gameViewManager));
        shipChooserSubScene.getPane().getChildren().add(createLabelHelp());
    }

    private void createScoresSubScene() {
        scoresSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(scoresSubScene);

        InfoLabel scoreboardLabel = new InfoLabel("SCOREBOARD");
        scoreboardLabel.setLayoutX(110);
        scoreboardLabel.setLayoutY(25);
        scoresSubScene.getPane().getChildren().add(scoreboardLabel);

        scoreboardSceneManager.setLayoutX(50);
        scoreboardSceneManager.setLayoutY(110);
        scoreboardSceneManager.createScoreboard();

        scoresSubScene.getPane().getChildren().add(scoreboardSceneManager);
    }

    private void createOptionsChooserSubScene() {
        optionsSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(optionsSubScene);

        InfoLabel optionsLabel = new InfoLabel("OPTIONS");
        optionsLabel.setLayoutX(110);
        optionsLabel.setLayoutY(25);
        optionsSubScene.getPane().getChildren().add(optionsLabel);

        LabelYellow languageLabel = new LabelYellow("Choose language", 400, 30);
        languageLabel.setAlignment(Pos.TOP_CENTER);
        languageLabel.setLayoutX(100);
        languageLabel.setLayoutY(100);
        optionsSubScene.getPane().getChildren().add(languageLabel);

        optionsSubScene.getPane().getChildren().add(createLanguageToChoose());
    }

    private HBox createLanguageToChoose() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        languagePickerSceneManagerList = new ArrayList<>();
        for (LANGUAGE language : LANGUAGE.values()) {
            LanguageSceneManager languageToPick = new LanguageSceneManager(language);
            languagePickerSceneManagerList.add(languageToPick);
            hBox.getChildren().add(languageToPick);
            languageToPick.setOnMouseClicked(mouseEvent -> {
                for (LanguageSceneManager language1 : languagePickerSceneManagerList) {
                    language1.setIsCircleChosen(false);
                }
                languageToPick.setIsCircleChosen(true);
                chosenLanguage = languageToPick.getLanguage();
            });
        }
        hBox.setLayoutX(230);
        hBox.setLayoutY(150);
        return hBox;
    }

    private HBox createShipsToChoose() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        shipPickerSceneManagerList = new ArrayList<>();
        for (SHIP ship : SHIP.values()) {
            ShipPickerSceneManager shipToPick = new ShipPickerSceneManager(ship);
            shipPickerSceneManagerList.add(shipToPick);
            hBox.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(mouseEvent -> {
                for (ShipPickerSceneManager ship1 : shipPickerSceneManagerList) {
                    ship1.setIsCircleChosen(false);
                }
                shipToPick.setIsCircleChosen(true);
                chosenShip = shipToPick.getShip();
            });
        }
        hBox.setLayoutX(70);
        hBox.setLayoutY(100);
        return hBox;
    }

    private InfoLabel createLabelHelp() {
        InfoLabel languageLabel = new InfoLabel(
                "Type 'S' to shoot, use left-right arrows to move", 14, 49, 500);
        languageLabel.setAlignment(Pos.CENTER);
        languageLabel.setLayoutX(50);
        languageLabel.setLayoutY(250);
        return languageLabel;
    }

    private SpaceRunnerButton createButtonToStart(GameViewManager gameViewManager) {
        SpaceRunnerButton startButton = new SpaceRunnerButton("START");
        startButton.setLayoutX(355);
        startButton.setLayoutY(320);

        startButton.setOnAction(event -> {
            if (chosenShip != null) {
                gameViewManager.createNewGame(mainStage, chosenShip);
            }
        });

        return startButton;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(SpaceRunnerButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 80);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    public void createButtons() {
        createStartButton();
        createScoresButton();
        createOptionsButton();
        createCreditsButton();
        createExitButton();
    }

    private void createStartButton() {
        SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
        addMenuButton(startButton);
        startButton.setOnAction(event -> showSubScene(shipChooserSubScene));

    }

    private void createScoresButton() {
        SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
        addMenuButton(scoresButton);
        scoresButton.setOnAction(event -> {
            showSubScene(scoresSubScene);
            scoreboardSceneManager.fillTable();

        });
    }

    private void createOptionsButton() {
        SpaceRunnerButton optionsButton = new SpaceRunnerButton("OPTIONS");
        addMenuButton(optionsButton);
        optionsButton.setOnAction(event -> showSubScene(optionsSubScene));
    }

    private void createCreditsButton() {
        SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
        addMenuButton(creditsButton);
        creditsButton.setOnAction(event -> showSubScene(creditsSubScene));
    }

    private void createExitButton() {
        SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
        addMenuButton(exitButton);
        exitButton.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private void createBackground() {
        Image backgroundImage = new Image("purple.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo() {
        ImageView logo = new ImageView("SpaceRunnerBig.png");

        logo.setFitWidth(800);
        logo.setPreserveRatio(true);

        logo.setLayoutX(15);
        logo.setLayoutY(-150);
        logo.setOnMouseEntered(mouseEvent -> logo.setEffect(new DropShadow()));
        logo.setOnMouseEntered(mouseEvent -> logo.setEffect(null));
        mainPane.getChildren().add(logo);
    }

}





