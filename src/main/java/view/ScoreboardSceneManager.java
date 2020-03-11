package view;

import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.SHIP;
import model.highscores.HighscoreManager;
import model.highscores.Score;

import java.util.ArrayList;

public class ScoreboardSceneManager extends TableView<Score> {

    private ImageView circleImage;
    private ImageView shipImage;
    private HighscoreManager highscoreManager;

    public ScoreboardSceneManager() {
        buildScoreboard();
    }

    private void buildScoreboard() {
        setPrefWidth(500);
        setPrefHeight(250);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Score, String> columnName = new TableColumn<>("Name");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.setResizable(false);
        columnName.setPrefWidth(250);
        columnName.setSortable(false);

        TableColumn<Score, Integer> columnScore = new TableColumn<>("Score");
        columnScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        columnScore.setResizable(false);
        columnScore.setPrefWidth(250);
        columnScore.setSortable(false);

        getColumns().add(columnName);
        getColumns().add(columnScore);

        getItems().add(new Score("Johny", 20));
        getItems().add(new Score("Anna", 30));
        getItems().add(new Score("Brad", 10));
    }

    private void uploadScoreboard() {
        highscoreManager.getScoreArrayList();
    }


}
