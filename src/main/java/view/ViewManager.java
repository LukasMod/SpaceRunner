package view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.HighscoreManager;
import model.LANGUAGE;
import model.SHIP;
import model.utils.I18N;
import view.components.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewManager {


    public static final int WIDTH = 900;
    public static final int HEIGHT = 680;
    private AnchorPane mainPane;
    private Stage mainStage;

    public final static int MENU_BUTTONS_START_X = 50;
    public final static int MENU_BUTTONS_START_Y = 150;

    private SpaceRunnerSubScene shipChooserSubScene;
    private SpaceRunnerSubScene scoresSubScene;
    private SpaceRunnerSubScene optionsSubScene;
    private SpaceRunnerSubScene creditsSubScene;

    private SpaceRunnerSubScene sceneToHide;

    private HighscoreManager highscoreManager = new HighscoreManager();
    private ScoreboardSceneManager scoreboardSceneManager = new ScoreboardSceneManager(highscoreManager);
    private GameViewManager gameViewManager = new GameViewManager(highscoreManager);

    List<SpaceRunnerButton> menuButtons;
    List<ShipPickerSceneManager> shipPickerSceneManagerList;
    List<LanguageSceneManager> languagePickerSceneManagerList;

    private SHIP chosenShip;
    private LANGUAGE chosenLanguage;

    public ResourceBundle myBundle;

    public ViewManager() {

        myBundle = ResourceBundle.getBundle("bundles/messages", Locale.getDefault());

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
        createCreditsSubScene();
    }

    private void createShipChooserSubScene() {
        shipChooserSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(shipChooserSubScene);
        InfoLabel chooseShipLabel = new InfoLabel("chooseYourShipLabel");
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

        InfoLabel scoreboardLabel = new InfoLabel("scoreboardLabel");
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

        InfoLabel optionsLabel = new InfoLabel("optionsLabel");
        optionsLabel.setLayoutX(110);
        optionsLabel.setLayoutY(25);
        optionsSubScene.getPane().getChildren().add(optionsLabel);

        LabelYellow languageLabel = new LabelYellow("chooseLanguageLabel", 400, 30);
        languageLabel.setAlignment(Pos.TOP_CENTER);
        languageLabel.setLayoutX(100);
        languageLabel.setLayoutY(100);
        optionsSubScene.getPane().getChildren().add(languageLabel);

        optionsSubScene.getPane().getChildren().add(createLanguageToChoose());
    }

    private void createCreditsSubScene() {
        creditsSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(creditsSubScene);

        InfoLabel creditsLabel = new InfoLabel("creditsLabel");
        creditsLabel.setLayoutX(110);
        creditsLabel.setLayoutY(25);
        creditsSubScene.getPane().getChildren().add(creditsLabel);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setLayoutX(100);
        vBox.setLayoutY(100);
        creditsSubScene.getPane().getChildren().add(vBox);

        LabelTransparent baseLabel = new LabelTransparent("baseLabel", 400, 60);
        baseLabel.setAlignment(Pos.TOP_LEFT);
        vBox.getChildren().add(baseLabel);

        LabelTransparent authorLabel = new LabelTransparent("authorLabel", 400, 30);
        authorLabel.setAlignment(Pos.TOP_LEFT);
        vBox.getChildren().add(authorLabel);

        LabelTransparent newLabel = new LabelTransparent("newLabel", 400, 120);
        newLabel.setAlignment(Pos.TOP_LEFT);
        vBox.getChildren().add(newLabel);

        LabelTransparent versionLabel = new LabelTransparent("versionLabel", 400, 30);
        versionLabel.setAlignment(Pos.TOP_LEFT);
        vBox.getChildren().add(versionLabel);

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
                setChosenLanguage();

            });
        }
        hBox.setLayoutX(230);
        hBox.setLayoutY(150);
        return hBox;
    }

    public void setChosenLanguage() {
        switch (chosenLanguage) {
            case PL:
                I18N.setLocale(I18N.getLocalePL());
                break;
            case ENG:
                I18N.setLocale(Locale.ENGLISH);
                break;
        }
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
        InfoLabel languageLabel = new InfoLabel("helpLabel", 14, 49, 500);
        languageLabel.setLayoutX(55);
        languageLabel.setLayoutY(250);
        languageLabel.setAlignment(Pos.CENTER_LEFT);
        return languageLabel;
    }

    private SpaceRunnerButton createButtonToStart(GameViewManager gameViewManager) {
        SpaceRunnerButton startButton = new SpaceRunnerButton("startButton");
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
        SpaceRunnerButton startButton = new SpaceRunnerButton("playButton");
        addMenuButton(startButton);
        startButton.setOnAction(event -> showSubScene(shipChooserSubScene));

    }

    private void createScoresButton() {
        SpaceRunnerButton scoresButton = new SpaceRunnerButton("scoresButton");
        addMenuButton(scoresButton);
        scoresButton.setOnAction(event -> {
            showSubScene(scoresSubScene);
            scoreboardSceneManager.fillTable();

        });
    }

    private void createOptionsButton() {
        SpaceRunnerButton optionsButton = new SpaceRunnerButton("optionsButton");
        addMenuButton(optionsButton);
        optionsButton.setOnAction(event -> showSubScene(optionsSubScene));
    }

    private void createCreditsButton() {
        SpaceRunnerButton creditsButton = new SpaceRunnerButton("creditsButton");
        addMenuButton(creditsButton);
        creditsButton.setOnAction(event -> showSubScene(creditsSubScene));
    }

    private void createExitButton() {
        SpaceRunnerButton exitButton = new SpaceRunnerButton("exitButton");
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





