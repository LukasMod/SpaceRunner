package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.highscores.HighscoreManager;
import model.highscores.Score;

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

//        columnName.setStyle("-fx-background-color: #ffcc00;");
//        columnScore.setStyle("-fx-background-color: #ffcc00;");
//        columnName.setStyle("src/main/resources/Style.css");


        getColumns().add(columnName);
        getColumns().add(columnScore);
    }


    public void fillTable() {
        this.uploadScoreArrayList = highscoreManager.getScoreArrayList();
        getItems().clear();
        getItems().addAll(uploadScoreArrayList);
    }

}