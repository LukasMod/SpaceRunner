package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;
import model.highscores.HighscoreManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameViewManager {

    private HighscoreManager highscoreManager;
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 600;

    private Stage menuStage;
    private ImageView ship;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private int angle;
    private AnimationTimer gameTimer;

    private GridPane gridPane1;
    private GridPane gridPane2;
    private final static String BACKGROUND_IMAGE = "purple.png";

    private final static String METEOR_BROWN_IMAGE = "meteorBrown_small2.png";
    private final static String METEOR_GREY_IMAGE = "meteorGrey_small2.png";
    private final static String METEOR_RED_IMAGE = "meteorRed_small2.png";

    private ImageView meteors;
    private ImageView meteorsRed;
    List<ImageView> meteorsList;
    List<ImageView> extraMeteorsList;
    Random randomPositionGenerator;
    Random randomMeteorsColor;
    private int numberMeteorsColor;
    private int numberOfMeteorsAtStart = 10;

    private ImageView star;
    private SmallInfoLabel pointsLabel;
    private ImageView[] playerLifes;
    private int playerLifeAtStart = 3;
    private int playerLife;
    private int points;
    private final static String GOLD_STAR_IMAGE = "hud/star_gold.png";

    private final static int STAR_RADIUS = 12;
    private final static int SHIP_RADIUS = 27;
    private final static int METEOR_RADIUS = 20;
    private final static int WEAPON_RADIUS = 10;

    //weapons and shooting
    private final static String LASER_BLUE_IMAGE = "weapons/laserBlue03.png";
    private boolean isSKeyPressed = false;
    List<ImageView> laserBlueList;
    private double shipCoordinateX;
    private double shipCoordinateY;

    public GameViewManager(HighscoreManager highscoreManager) {
        this.highscoreManager = highscoreManager;
        initializeStage();
        createKeyListeners();
        randomPositionGenerator = new Random();
        randomMeteorsColor = new Random();
    }

    private void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                } else if (keyEvent.getCode() == KeyCode.S) {
                    isSKeyPressed = true;
                }
            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                } else if (keyEvent.getCode() == KeyCode.S) {
                    isSKeyPressed = false;
                }
            }
        });
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setResizable(false);
        gameStage.setScene(gameScene);

    }

    public void createNewGame(Stage menuStage, SHIP chosenShip) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createShip(chosenShip);
        createGameElements(chosenShip);
        createShooting();
        createGameLoop();
        gameStage.show();
    }

    private void createGameElements(SHIP chosenShip) {
        playerLife = 2;
        star = new ImageView(GOLD_STAR_IMAGE);
        setNewElementPosition(star);
        gamePane.getChildren().add(star);
        pointsLabel = new SmallInfoLabel("POINTS : 00");
        pointsLabel.setLayoutX(460);
        pointsLabel.setLayoutY(20);
        gamePane.getChildren().add(pointsLabel);
        playerLifes = new ImageView[playerLifeAtStart];

        for (int i = 0; i < playerLifes.length; i++) {
            playerLifes[i] = new ImageView(chosenShip.getUrlLife());
            playerLifes[i].setLayoutX(455 + (i * 50));
            playerLifes[i].setLayoutY(80);
            gamePane.getChildren().add(playerLifes[i]);
        }
        createMeteors(numberOfMeteorsAtStart);
    }

    private void createMeteors(int numberMeteorsAtStart) {
        meteorsList = new ArrayList<>();
        for (int i = 0; i < numberMeteorsAtStart; i++) {
            meteorsList.add(setMeteorColor(meteors));
            setNewElementPosition(meteorsList.get(i));
            gamePane.getChildren().add(meteorsList.get(i));
        }
    }

    private ImageView setMeteorColor(ImageView meteors) {
        numberMeteorsColor = randomMeteorsColor.nextInt(3);
        if (numberMeteorsColor == 0) {
            this.meteors = new ImageView(METEOR_BROWN_IMAGE);
        } else if (numberMeteorsColor == 1) {
            this.meteors = new ImageView(METEOR_GREY_IMAGE);
        } else if (numberMeteorsColor == 2) {
            this.meteors = new ImageView(METEOR_RED_IMAGE);
        }
        return this.meteors;
    }

    private void addExtraMeteors(int numberMeteors) {
        extraMeteorsList = new ArrayList<>();
        for (int i = 0; i < numberMeteors; i++) {
            meteorsRed = new ImageView(METEOR_RED_IMAGE);
            extraMeteorsList.add(meteorsRed);
//            extraMeteorsList.add(setMeteorColor(meteors));
            gamePane.getChildren().add(extraMeteorsList.get(i));
            setNewElementPosition(extraMeteorsList.get(i));
        }
        meteorsList.addAll(extraMeteorsList);
    }

    private void createShooting() {
        laserBlueList = new ArrayList<>();
    }

    private double getCoordinatesShipX() {
        this.shipCoordinateX = ship.getLayoutX();
        return shipCoordinateX;
    }

    private double getCoordinatesShipY() {
        this.shipCoordinateY = ship.getLayoutY();
        return shipCoordinateY;
    }

    private void shooting() {
        if (isSKeyPressed) {
            ImageView laserBlue = new ImageView(LASER_BLUE_IMAGE);
            laserBlue.setLayoutY(getCoordinatesShipY() - 10);
            laserBlue.setLayoutX(getCoordinatesShipX() + ship.getImage().getWidth() / 2 - 4);
            laserBlueList.add(laserBlue);
            gamePane.getChildren().add(laserBlue);
            isSKeyPressed = false;
        }
    }

    private void shotsOutOfRange() {
        for (int j = 0; j < laserBlueList.size(); j++) {
            if (laserBlueList.get(j).getLayoutY() < 0) {
                laserBlueList.get(j).setImage(null);
                laserBlueList.remove(j);
                break;
            }
        }
    }

    private void moveShooting() {
        shooting();
        for (ImageView imageView : laserBlueList) {
            imageView.setVisible(true);
            imageView.setLayoutY(imageView.getLayoutY() - 40);
        }
    }

    private void setNewElementPosition(ImageView image) {
        image.setLayoutX(randomPositionGenerator.nextInt(600));
        image.setLayoutY(-(randomPositionGenerator.nextInt(3200) + 600));
    }

    private void moveGameElements() {

        star.setLayoutY(star.getLayoutY() + 5);
        for (int i = 0; i < meteorsList.size(); i++) {
            meteorsList.get(i).setLayoutY(meteorsList.get(i).getLayoutY() + 7);
            meteorsList.get(i).setRotate(meteorsList.get(i).getRotate() + 4);
        }
    }

    private void checkIfElementsAreBehindTheShipAndRelocate() {

        if (star.getLayoutY() > 1200) {
            setNewElementPosition(star);
        }
        for (int i = 0; i < meteorsList.size(); i++) {
            if (meteorsList.get(i).getLayoutY() > 900) {
                setNewElementPosition(meteorsList.get(i));
            }
        }
    }

    private void createShip(SHIP chosenShip) {
        ship = new ImageView(chosenShip.getUrl());
        ship.setLayoutX(GAME_WIDTH / 2);
        ship.setLayoutY(GAME_HEIGHT - 90);
        gamePane.getChildren().add(ship);
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveBackground();
                moveGameElements();
                checkIfElementsAreBehindTheShipAndRelocate();
                checkIfElementsCollide();
                shotsOutOfRange();
                moveShip();
                moveShooting();
            }
        };
        gameTimer.start();
    }

    private void moveShip() {
        if (isLeftKeyPressed && !isRightKeyPressed) {
            if (angle > -30) {
                angle = -5;
            }
            ship.setRotate(angle);
            if (ship.getLayoutX() > -20) {
                ship.setLayoutX(ship.getLayoutX() - 12);
            }
            shooting();
        }
        if (!isLeftKeyPressed && isRightKeyPressed) {
            if (angle < 30) {
                angle = 5;
            }
            ship.setRotate(angle);
            if (ship.getLayoutX() < 480) {
                ship.setLayoutX(ship.getLayoutX() + 12);
            }
            shooting();
        }
        if (isLeftKeyPressed && isRightKeyPressed) {
            if (angle < 0) {
                angle += 5;
            } else if (angle > 0) {
                angle -= 5;
            }
            ship.setRotate(angle);
        }
        if (!isLeftKeyPressed && !isRightKeyPressed) {
            if (angle < 0) {
                angle += 5;
            } else if (angle > 0) {
                angle -= 5;
            }
            ship.setRotate(angle);
        }
    }

    private void createBackground() {
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();

        for (int i = 0; i < 12; i++) {
            ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
            ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
            GridPane.setConstraints(backgroundImage1, i % 3, i / 3);
            GridPane.setConstraints(backgroundImage2, i % 3, i / 3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
        }
        gridPane1.setLayoutY(-1024);
        gamePane.getChildren().addAll(gridPane1, gridPane2);
    }

    private void moveBackground() {
        gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
        gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);

        if (gridPane1.getLayoutY() >= 1024) {
            gridPane1.setLayoutY(-1024);
        }
        if (gridPane2.getLayoutY() >= 1024) {
            gridPane2.setLayoutY(-1024);
        }
    }

    private void addPoints(int newPoints) {
        points = points + newPoints;
        String textToSet = "POINTS : ";
        if (points < 10) {
            textToSet = textToSet + "0";
        }
        pointsLabel.setText(textToSet + points);
    }

    private void checkIfElementsCollide() {
        try {
            if (SHIP_RADIUS + STAR_RADIUS > calculateDistance(ship.getLayoutX() + 49,
                    star.getLayoutX() + 15, ship.getLayoutY() + 37, star.getLayoutY() + 15)) {
                setNewElementPosition(star);
                addPoints(10);
                addExtraMeteors(4);
            }
            for (int i = 0; i < meteorsList.size(); i++) {
                if (METEOR_RADIUS + SHIP_RADIUS > calculateDistance(ship.getLayoutX() + 49,
                        meteorsList.get(i).getLayoutX() + 20, ship.getLayoutY() + 37, meteorsList.get(i).getLayoutY() + 20)) {
                    removeLife();
                    setNewElementPosition(meteorsList.get(i));

                }
            }
            for (int j = 0; j < laserBlueList.size(); j++) {
                for (int i = 0; i < meteorsList.size(); i++) {
                    if (METEOR_RADIUS + WEAPON_RADIUS > calculateDistance(laserBlueList.get(j).getLayoutX() + 10,
                            meteorsList.get(i).getLayoutX() + 20, laserBlueList.get(j).getLayoutY() + 10,
                            meteorsList.get(i).getLayoutY() + 20)) {
                        laserBlueList.get(j).setImage(null);
                        laserBlueList.remove(j);
                        setNewElementPosition(meteorsList.get(i));
                        addPoints(1);
                        addExtraMeteors(2);
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }

    }

    private void removeLife() {
        gamePane.getChildren().remove(playerLifes[playerLife]);
        playerLife--;
        if (playerLife < 0) {
            gameTimer.stop();
            EndViewManager endViewManager = new EndViewManager(this, highscoreManager);
            endViewManager.createWindow();
            gameStage.close();

        }
    }

    private double calculateDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public int getPoints() {
        return points;
    }

}
