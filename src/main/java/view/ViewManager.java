package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.InfoLabel;
import model.SHIP;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;
import model.highscores.HighscoreManager;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    private final String FONT_PATH = "src/main/resources/kenvector_future.ttf";

    public static final int WIDTH = 900;
    public static final int HEIGHT = 680;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    public final static int MENU_BUTTONS_START_X = 50;
    public final static int MENU_BUTTONS_START_Y = 150;

    private SpaceRunnerSubScene creditsSubScene;
    private SpaceRunnerSubScene helpSubScene;
    private SpaceRunnerSubScene scoresSubScene;
    private SpaceRunnerSubScene shipChooserSubScene;

    private SpaceRunnerSubScene sceneToHide;

    private GameViewManager gameViewManager = new GameViewManager();
    private HighscoreManager highscoreManager = new HighscoreManager();
    private EndViewManager endViewManager = new EndViewManager(gameViewManager, highscoreManager);
    private ScoreboardSceneManager scoreboardSceneManager = new ScoreboardSceneManager(highscoreManager);

    List<SpaceRunnerButton> menuButtons;
    List<ShipPickerSceneManager> shipPickerSceneManagerList;

    private SHIP chosenShip;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
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

        helpSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(helpSubScene);

        createShipChooserSubScene();
        createScoresSubScene();

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
        shipChooserSubScene.getPane().getChildren().add(createButtonToScore(endViewManager));
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

    private HBox createShipsToChoose() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        shipPickerSceneManagerList = new ArrayList<>();
        for (SHIP ship : SHIP.values()) {
            ShipPickerSceneManager shipToPick = new ShipPickerSceneManager(ship);
            shipPickerSceneManagerList.add(shipToPick);
            hBox.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (ShipPickerSceneManager ship : shipPickerSceneManagerList) {
                        ship.setIsCircleChoosen(false);
                    }
                    shipToPick.setIsCircleChoosen(true);
                    chosenShip = shipToPick.getShip();
                }
            });
        }
        hBox.setLayoutX(300 - (118 * 2));
        hBox.setLayoutY(100);
        return hBox;
    }

    private SpaceRunnerButton createButtonToStart(GameViewManager gameViewManager) {
        SpaceRunnerButton startButton = new SpaceRunnerButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (chosenShip != null) {
                    gameViewManager.createNewGame(mainStage, chosenShip);
                }
            }
        });

        return startButton;
    }

    private SpaceRunnerButton createButtonToScore(EndViewManager endViewManager) {
//        this.endViewManager = endViewManager;
        SpaceRunnerButton scoreButton = new SpaceRunnerButton("Add score");
        scoreButton.setLayoutX(100);
        scoreButton.setLayoutY(300);

        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                endViewManager.createWindow(mainStage);
            }
        });

        return scoreButton;
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
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createStartButton() {
        SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
        addMenuButton(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(shipChooserSubScene);
            }
        });

    }

    private void createScoresButton() {
        SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
        addMenuButton(scoresButton);
        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(scoresSubScene);
                scoreboardSceneManager.fillTable();
            }
        });
    }

    private void createHelpButton() {
        SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
        addMenuButton(helpButton);
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton() {
        SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
        addMenuButton(creditsButton);
        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton() {
        SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
        addMenuButton(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                mainStage.close();
                Platform.exit();
                System.exit(0);
            }
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
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(new DropShadow());
            }
        });
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(null);
            }
        });
        mainPane.getChildren().add(logo);
    }

}





