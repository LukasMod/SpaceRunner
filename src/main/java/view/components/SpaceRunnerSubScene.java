package view.components;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import view.ViewManager;


public class SpaceRunnerSubScene extends SubScene {

    private final static String BACKGOUND_IMAGE = "yellow_panel.png";

    private boolean isHidden = true;

    public SpaceRunnerSubScene() {
        super(new AnchorPane(), 600, 400);
        prefHeight(600);
        prefWidth(400);

        BackgroundImage image = new BackgroundImage(new Image(BACKGOUND_IMAGE, 600, 400, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        setLayoutX(ViewManager.WIDTH + 1);
        setLayoutY(150);
    }

    public void moveSubScene() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.3));
        translateTransition.setNode(this);

        if (isHidden) {
            translateTransition.setToX(ViewManager.MENU_BUTTONS_START_X - ViewManager.WIDTH + 210);
            isHidden = false;
        } else {
            translateTransition.setToX(0);
            isHidden = true;
        }
        translateTransition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }

}
