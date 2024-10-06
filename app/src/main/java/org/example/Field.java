package org.example;

import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

public final class Field extends GridPane {
    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[this.width][this.height];
    }

    @Override
    public ObservableList<Node> getChildren() {
        throw new UnsupportedOperationException("Illegal operation");
    }

    public Cell getChildren(int x, int y) {
        return cells[x][y];
    }

    @Override
    public void add(Node cell, int x, int y) {
        if (!(cell instanceof Cell)) {
            throw new IllegalArgumentException("Illegal Node");
        }
        super.add(cell, x, y);
        cells[x][y] = (Cell) cell;
    }

    @Override
    public void add(Node cell, int x, int y, int xSpan, int ySpan) {
        throw new UnsupportedOperationException("Illegal operation");
    }

    @Override
    public void addRow(int y, Node... cells) {
        if (cells.length != width) {
            throw new IllegalArgumentException("Illegal row");
        }
        for (Node cell : cells) {
            if (!(cell instanceof Cell)) {
                throw new IllegalArgumentException("Illegal Node");
            }
        }
        super.addRow(y, cells);
        for (Node cell : cells) {
            this.cells[GridPane.getColumnIndex(cell)][GridPane.getRowIndex(cell)] = (Cell) cell;
        }
    }

    @Override
    public void addColumn(int x, Node... cells) {
        if (cells.length != height) {
            throw new IllegalArgumentException("Illegal column");
        }
        for (Node cell : cells) {
            if (!(cell instanceof Cell)) {
                throw new IllegalArgumentException("Illegal Node");
            }
        }
        super.addColumn(x, cells);
        for (Node cell : cells) {
            this.cells[GridPane.getColumnIndex(cell)][GridPane.getRowIndex(cell)] = (Cell) cell;
        }
    }

    public final int width;
    public final int height;
    public final Cell[][] cells;
}
