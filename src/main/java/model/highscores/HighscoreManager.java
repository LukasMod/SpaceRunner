package model.highscores;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighscoreManager {

    public static final String HIGHSCORE_TXT = "highscore.txt";
    public ArrayList<Score> scoreArrayList;
    private static final String HIGHSCORE_DAT = "highscore.dat";

    String highscoreString = "";
    static int max = 100;

    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;

    public HighscoreManager() {
        scoreArrayList = new ArrayList<>();
    }

    public ArrayList<Score> getScoreArrayList() {
        loadScoreFile();
        sort();
        return scoreArrayList;
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scoreArrayList, comparator);
    }

    public ArrayList<Score> loadScoreFile() {
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_DAT));
            scoreArrayList = (ArrayList<Score>) objectInputStream.readObject();
            System.out.println("CHECKPOINT loadScoreFile:. Ilość elem.: " + scoreArrayList.size());
            System.out.println(scoreArrayList);

        } catch (FileNotFoundException e) {
            System.out.println("[Load] FNF Error 'loadScoreFile Error': " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Load] IO Error 'loadScoreFile Error': " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Load] CNF Error 'loadScoreFile Error': " + e.getMessage());
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Load] IO Error 'loadScoreFile Error': " + e.getMessage());
            }
        }
        return scoreArrayList;
    }

    public void saveNewScore(Score newScore) throws IOException {
        scoreArrayList.add(newScore);
        updateScoreFile();
        createTxtFile();
    }

    private void updateScoreFile() {
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_DAT));
            objectOutputStream.writeObject(scoreArrayList);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error" + e.getMessage() + ", the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error 'updateScoreFile Error':" + e.getMessage());
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] IO Error 'updateScoreFile Error': " + e.getMessage());
            }
        }
    }

    public void createTxtFile() throws IOException {
        FileWriter fileWriter = new FileWriter(HIGHSCORE_TXT);
        try {
            sort();
            for (int i = 0; i < scoreArrayList.size(); i++) {
                String str = (i + 1) + ". " + scoreArrayList.get(i).toString();
                fileWriter.write(str);
                if (i < scoreArrayList.size() - 1) {
                    fileWriter.write("\n");
                }
            }
        } catch (IOException e) {
            e.getMessage();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("[Update] IO Error 'createTxtFile Error': " + e.getMessage());
            }
        }

    }

    public String getHighscoreString() {
        scoreArrayList = getScoreArrayList();

        int i = 0;
        int x = scoreArrayList.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highscoreString += (i + 1) + ".\t" + scoreArrayList.get(i).getName() + " " +
                    scoreArrayList.get(i).getScore() + "\n";
            i++;
        }
        return highscoreString;
    }

}
