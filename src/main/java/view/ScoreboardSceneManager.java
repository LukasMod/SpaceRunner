package view;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HighscoreManager;
import model.Score;
import view.components.SpaceRunnerColumn;

import java.util.ArrayList;

public class ScoreboardSceneManager extends TableView<Score> {

    public ArrayList<Score> uploadScoreArrayList;
    private HighscoreManager highscoreManager;

    public ScoreboardSceneManager(HighscoreManager highscoreManager) {
        this.highscoreManager = highscoreManager;
    }

    public void createScoreboard() {
        createTable();
        highscoreManager.loadScoreFile();
    }

    public void createTable() {
        setPrefWidth(500);
        setPrefHeight(250);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        getStyleClass().add("TableScoreboard");

        SpaceRunnerColumn<Score, String> columnName = new SpaceRunnerColumn<>();
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        SpaceRunnerColumn<Score, Integer> columnScore = new SpaceRunnerColumn<>();
        columnScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        getColumns().add(columnName);
        getColumns().add(columnScore);
    }

    public void fillTable() {
        this.uploadScoreArrayList = highscoreManager.getScoreArrayList();
        getItems().clear();
        getItems().addAll(uploadScoreArrayList);
    }
}