package org.example;

import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

public final class MineField extends GridPane {
    public MineField(int columnCount, int rowCount) {
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
        _isInitialized = false;
        _cells = new Cell[columnCount][rowCount];
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                add(new Cell(i, j), i, j);
            }
        }
    }

    @Override
    public ObservableList<Node> getChildren() {
        return FXCollections.observableList(super.getChildren());
    }

    public Cell[][] getCells() {
        return _cells;
    }

    @Override
    public void add(Node cell, int x, int y) {
        if (!(cell instanceof Cell)) {
            throw new IllegalArgumentException("Illegal Node");
        }
        super.add(cell, x, y);
        getCells()[x][y] = (Cell) cell;
    }

    @Override
    public void add(Node cell, int x, int y, int xSpan, int ySpan) {
        throw new UnsupportedOperationException("Illegal operation");
    }

    @Override
    public void addRow(int y, Node... cells) {
        if (cells.length != getColumnCount()) {
            throw new IllegalArgumentException("Illegal row");
        }
        for (Node cell : cells) {
            if (!(cell instanceof Cell)) {
                throw new IllegalArgumentException("Illegal Node");
            }
        }
        super.addRow(y, cells);
        for (Node cell : cells) {
            getCells()[GridPane.getColumnIndex(cell)][GridPane.getRowIndex(cell)] = (Cell) cell;
        }
    }

    @Override
    public void addColumn(int x, Node... cells) {
        if (cells.length != getRowCount()) {
            throw new IllegalArgumentException("Illegal column");
        }
        for (Node cell : cells) {
            if (!(cell instanceof Cell)) {
                throw new IllegalArgumentException("Illegal Node");
            }
        }
        super.addColumn(x, cells);
        for (Node cell : cells) {
            getCells()[GridPane.getColumnIndex(cell)][GridPane.getRowIndex(cell)] = (Cell) cell;
        }
    }

    private boolean _isInitialized;
    private Cell[][] _cells;
}
