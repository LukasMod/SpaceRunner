package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.highscores.HighscoreManager;
import model.highscores.Score;

import java.util.ArrayList;

public class ScoreboardSceneManager extends TableView<Score> {

    private ImageView circleImage;
    private ImageView shipImage;
    private HighscoreManager highscoreManager;

    public ScoreboardSceneManager() {
        buildScoreboard();
        System.out.println("konstruktor ScoreboardSceneManager");
    }

    public void buildScoreboard() {
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

        fillTable();
        System.out.println("buildScoreboard: ");
    }

    public void fillTable() {
        HighscoreManager highscoreManager = new HighscoreManager();

        ArrayList<Score> uploadScoreArrayList = highscoreManager.getScoreArrayList();
        System.out.println("fillTable ilość elem: " + uploadScoreArrayList.size());
        refresh();


        for (int i = 0; i < uploadScoreArrayList.size(); i++) {
            getItems().add(new Score(uploadScoreArrayList.get(i).getName(), uploadScoreArrayList.get(i).getScore()));
        }

    }

}
