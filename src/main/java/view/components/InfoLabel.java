package view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static model.utils.I18N.createStringBinding;

public class InfoLabel extends Label {

    public final static String FONT_PATH = "src/main/resources/kenvector_future.ttf";
    public final static String BACKGROUND_IMAGE = "yellow_button13.png";

    public InfoLabel(String text) {
        setPrefWidth(380);
        setPrefHeight(49);
        setPadding(new Insets(7,7,7,7));
        setAlignment(Pos.CENTER);
        textProperty().bind(createStringBinding(text));
        setWrapText(true);
        setLabelFont();

        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, 380, 49, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
    }
    public InfoLabel(String text, int height, int imageHeight,int imageWidth) {
        setPrefWidth(imageWidth);
        setPrefHeight(imageHeight);
        setPadding(new Insets(7,7,7,7));
        setWrapText(true);
        setAlignment(Pos.CENTER);
        textProperty().bind(createStringBinding(text));
        setLabelFontHeight(height);

        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, imageWidth, imageHeight, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 23));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Loading font problem. Font set to Verdana\"");
            setFont(Font.font("Verdana", 23));
        }
    }
    private void setLabelFontHeight(int high) {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), high));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Loading font problem. Font set to Verdana\"");
            setFont(Font.font("Verdana", high));
        }
    }
}
