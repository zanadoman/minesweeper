package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public final class MineField extends GridPane {
    public MineField(int columnCount, int rowCount) {
        getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if (!change.wasAdded()) {
                    continue;
                }
                for (Node cell : change.getAddedSubList()) {
                    _cells[GridPane.getColumnIndex(cell)][GridPane
                            .getRowIndex(cell)] = (Cell) cell;
                }
            }
        });
        clear(columnCount, rowCount);
    }

    public void clear(int columnCount, int rowCount) {
        getChildren().clear();
        _isInitialized = false;
        _cells = new Cell[columnCount][rowCount];
        _isExploded = false;
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                add(new Cell(), i, j);
            }
        }
    }

    public boolean isInitialized() {
        return _isInitialized;
    }

    public boolean isExploded() {
        return _isExploded;
    }

    public void reveal(int x, int y) {
        if (isExploded() || _cells[x][y].isRevealed()
                || _cells[x][y].isFlagged()) {
            return;
        }
        if (!isInitialized()) {
            initialize(x, y);
        }
        if (_cells[x][y].hasMine()) {
            _isExploded = true;
            for (Cell[] cells : _cells) {
                for (Cell cell : cells) {
                    if (cell.hasMine() && !cell.isRevealed()) {
                        cell.explode();
                    }
                }
            }
        } else {
            int adjacentMineCount = getAdjacentMineCount(x, y);
            _cells[x][y].reveal(adjacentMineCount);
            if (adjacentMineCount == 0) {
                forEachAdjacentCell(x, y, (i, j) -> explore(i, j));
            }
        }
    }

    private void initialize(int x, int y) {
        if (isInitialized()) {
            return;
        }
        _isInitialized = true;
        for (int i = 0; i < getColumnCount(); i++) {
            for (int j = 0; j < getRowCount(); j++) {
                if ((1 < Math.abs(i - x) || 1 < Math.abs(j - y))
                        && 0.8 < App.random.nextDouble()) {
                    _cells[i][j].placeMine();
                }
            }
        }
    }

    private void explore(int x, int y) {
        if (_cells[x][y].hasMine() || _cells[x][y].isRevealed()
                || _cells[x][y].isFlagged()) {
            return;
        }
        int adjacentMineCount = getAdjacentMineCount(x, y);
        _cells[x][y].reveal(adjacentMineCount);
        if (adjacentMineCount == 0) {
            forEachAdjacentCell(x, y, (i, j) -> explore(i, j));
        }
    }

    private void forEachAdjacentCell(int x, int y,
            BiConsumer<Integer, Integer> operation) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (0 <= i && i < getColumnCount() && 0 <= j
                        && j < getRowCount() && (i != x || j != y)) {
                    operation.accept(i, j);
                }
            }
        }
    }

    private int getAdjacentMineCount(int x, int y) {
        AtomicInteger adjacentMineCount = new AtomicInteger();
        forEachAdjacentCell(x, y, (i, j) -> {
            if (_cells[i][j].hasMine()) {
                adjacentMineCount.incrementAndGet();
            }
        });
        return adjacentMineCount.get();
    }

    private boolean _isInitialized;
    private Cell[][] _cells;
    private boolean _isExploded;
}
