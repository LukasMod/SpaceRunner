package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class TextFieldYellow extends TextField {

    private final String FONT_PATH = "src/main/resources/kenvector_future.ttf";

    public TextFieldYellow(int prefWidth, int prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("yellow_button01.png", prefWidth, prefHeight, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 10, 10, 10));
        setLabelFont();
        setPromptText("Write your name here");

    }

private void setLabelFont(){
        try{
        setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),15));
        }catch(FileNotFoundException e){
        setFont(Font.font("Verdana",15));
        }
        }
        }
