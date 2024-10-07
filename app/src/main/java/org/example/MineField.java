package org.example;

import java.util.Random;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

public final class MineField extends GridPane {
    public MineField(int columnCount, int rowCount) {
        getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if (!change.wasAdded()) {
                    continue;
                }
                for (Node cell : change.getAddedSubList()) {
                    getCells()[GridPane.getColumnIndex(cell)][GridPane
                            .getRowIndex(cell)] = (Cell) cell;
                }
            }
        });
        clear(columnCount, rowCount);
    }

    public boolean isInitialized() {
        return _isInitialized;
    }

    public void initialize(int x, int y) {
        if (isInitialized()) {
            return;
        }
        Random random = new Random();
        for (int i = 0; i < getColumnCount(); i++) {
            for (int j = 0; j < getRowCount(); j++) {
                if ((1 < Math.abs(i - x) || 1 < Math.abs(j - y))
                        && 0.8 < random.nextDouble()) {
                    getCells()[i][j].placeMine();
                }
            }
        }
        _isInitialized = true;
    }

    public void clear(int columnCount, int rowCount) {
        getChildren().clear();
        _isInitialized = false;
        _cells = new Cell[columnCount][rowCount];
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                add(new Cell(), i, j);
            }
        }
    }

    public Cell[][] getCells() {
        return _cells;
    }

    private boolean _isInitialized;
    private Cell[][] _cells;
    public final Random random = new Random();
}
