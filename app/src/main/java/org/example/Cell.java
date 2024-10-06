package org.example;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public final class Cell extends Button {
    public Cell(boolean hasMine) throws Exception {
        this.hasMine = hasMine;
    }

    private GridPane getGrid() throws Exception {
        Parent parent = getParent();
        if (!(parent instanceof GridPane)) {
            throw new IllegalStateException("Illegal Parent");
        }
        return (GridPane) parent;
    }

    // private Cell getCell(int x, int y) throws Exception {
    //     GridPane grid = getGrid();
    //     grid.getChildren();
    // }

    private int getStyleID() throws Exception {
        int x = GridPane.getColumnIndex(this);
        int y = GridPane.getRowIndex(this);
        return x % 2 == 0 ? y % 2 == 0 ? 1 : 2 : y % 2 == 0 ? 2 : 1;
    }

    private final boolean hasMine;
    private boolean _flagged;
    private boolean _explored;
}
