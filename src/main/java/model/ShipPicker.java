package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox {

    private ImageView circleImage;
    private ImageView shipImage;

    private String CIRCLE_CHOSEN = "yellow_boxTick.png";
    private String CIRCLE_NOT_CHOOSEN = "grey_circle.png";

    private SHIP ship;
    private Boolean isCircleChoosen;

    public ShipPicker(SHIP ship) {
        circleImage = new ImageView(CIRCLE_NOT_CHOOSEN);
        shipImage = new ImageView(ship.getUrl());
        this.ship = ship;
        isCircleChoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(shipImage);
    }

    public SHIP getShip() {
        return ship;
    }

    public boolean getCircleChoosen() {
        return isCircleChoosen;
    }

    public void setIsCircleChoosen (boolean isCircleChoosen) {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen ? CIRCLE_CHOSEN : CIRCLE_NOT_CHOOSEN;
        circleImage.setImage(new Image(imageToSet));
    }


}
