package model.highscores;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class HighscoreManager {

    private ArrayList<Score> scoreArrayList;
    private static final String HIGHSCORE_FILE = "scores.dat";

    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;
    
    public HighscoreManager() {
        scoreArrayList = new ArrayList<Score>();
    }
    
    private ArrayList<Score> getScoreArrayList() {
        loadScoreFile();
        sort();
        return scoreArrayList;
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scoreArrayList, comparator);
    }

    private void loadScoreFile() {
    }
    
    public void addScore(String name, int score) {
        loadScoreFile();
        scoreArrayList.add(new Score(name, score));
        updateScoreFile();
    }

    private void updateScoreFile() {
    }


}
