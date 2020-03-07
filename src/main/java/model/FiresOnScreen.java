package model;

import javafx.scene.image.ImageView;
import view.GameViewManager;

public class FiresOnScreen extends ImageView {

    private ImageView laserBlue;
    private String LASER_BLUE_IMAGE = "weapons/laserBlue03.png";

    private GameViewManager gameViewManager;

    public FiresOnScreen() {
            laserBlue = new ImageView(LASER_BLUE_IMAGE);
    }

}
