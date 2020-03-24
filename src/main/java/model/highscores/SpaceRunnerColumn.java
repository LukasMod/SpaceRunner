package model.highscores;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class SpaceRunnerColumn<S, T> extends TableColumn<S, T> {


    public SpaceRunnerColumn() {
        setResizable(false);
        setPrefWidth(250);
        setSortable(false);
        getStyleClass().add("ColumnScoreboard");

    }

}
