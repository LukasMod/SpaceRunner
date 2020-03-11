package model.highscores;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighscoreManager {

    public ArrayList<Score> scoreArrayList;
    private static final String HIGHSCORE_FILE = "scores.dat";

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
        sort();
        return scoreArrayList;
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scoreArrayList, comparator);
    }

    public void loadScoreFile() {
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            scoreArrayList = (ArrayList<Score>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Load] FNF Error" + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Load] IO Error" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Load] CNF Error" + e.getMessage());
        } finally {
            try {
                if (objectOutputStream != null) {
                    //   objectOutputStream.flush();  -already in close()
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Load] IO Error" + e.getMessage());
            }
        }
    }

    public void addScore(String name, int score) throws IOException {
        loadScoreFile();
        scoreArrayList.add(new Score(name, score));
        scoreArrayList.add(new Score("Iga", 215000000));
        scoreArrayList.add(new Score("jack", 100));
        updateScoreFile();
        createTxtFile();
    }

    private void updateScoreFile() {
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            objectOutputStream.writeObject(scoreArrayList);
//            for (int i = 0; i < scoreArrayList.size(); i++) {
//                objectOutputStream.writeObject(scoreArrayList.get(i));
//            }
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error" + e.getMessage() + ", the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error" + e.getMessage());
        } finally {
            try {
                if (objectOutputStream != null) {
                    //  objectOutputStream.flush();  -already in close()
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] IO Error" + e.getMessage());
            }
        }
    }

    public void createTxtFile() throws IOException {
        FileWriter fileWriter = new FileWriter("highscore.txt");
        try {
            sort();
            for (int i = 0; i < scoreArrayList.size(); i++) {
                String str =  (i+1) + ". " + scoreArrayList.get(i).toString();
                fileWriter.write(str);
                if (i < scoreArrayList.size() - 1) {
                    fileWriter.write("\n");
                }
            }
        } catch (IOException e) {
            e.getMessage();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] IO Error" + e.getMessage());
            }
        }

    }

    public String getHighscoreString() {
        ArrayList<Score> scoreArrayList;
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
