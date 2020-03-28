package view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.LANGUAGE;

public class LanguageSceneManager extends VBox {

    private ImageView circleImage;
    private ImageView languageImage;

    private String CIRCLE_CHOSEN = "yellow_boxTick.png";
    private String CIRCLE_NOT_CHOSEN = "grey_circle.png";

    private LANGUAGE language;
    private Boolean isCircleChosen;

    public LanguageSceneManager(LANGUAGE language) {
        circleImage = new ImageView(CIRCLE_NOT_CHOSEN);
        languageImage = new ImageView(language.getUrl());
        languageImage.setFitWidth(60);
        languageImage.setFitHeight(60);

        this.language = language;
        isCircleChosen = false;

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(languageImage);
        this.getChildren().add(circleImage);
    }

    public LANGUAGE getLanguage() {
        return language;
    }

    public void setIsCircleChosen(boolean isCircleChosen) {
        this.isCircleChosen = isCircleChosen;
        String imageToSet = this.isCircleChosen ? CIRCLE_CHOSEN : CIRCLE_NOT_CHOSEN;
        circleImage.setImage(new Image(imageToSet));
    }

}
