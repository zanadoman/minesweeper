package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

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
                for (Node node : change.getAddedSubList()) {
                    _cells[GridPane.getColumnIndex(node)][GridPane
                            .getRowIndex(node)] = (Cell) node;
                }
            }
        });
        clear(columnCount, rowCount);
    }

    public void clear(int columnCount, int rowCount) {
        getChildren().clear();
        _isInitialized = false;
        _isExploded = false;
        _cells = new Cell[columnCount][rowCount];
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                add(new Cell(), i, j);
            }
        }
        _safeCellCount = getColumnCount() * getRowCount();
        _revealedCellCount = 0;
        updateProgress();
        App.getMenu().stopwatch.reset();
    }

    public boolean isInitialized() {
        return _isInitialized;
    }

    public boolean isExploded() {
        return _isExploded;
    }

    public void reveal(int columnIndex, int rowIndex) {
        if (isExploded() || _cells[columnIndex][rowIndex].isRevealed()
                || _cells[columnIndex][rowIndex].isFlagged()) {
            return;
        }
        if (!isInitialized()) {
            initialize(columnIndex, rowIndex);
        }
        if (_cells[columnIndex][rowIndex].hasMine()) {
            for (Cell[] cells : _cells) {
                for (Cell cell : cells) {
                    if (cell.hasMine() && !cell.isRevealed()) {
                        cell.explode();
                    }
                }
            }
            _isExploded = true;
            App.getMenu().stopwatch.stop();
        } else {
            int adjacentMineCount = getAdjacentMineCount(columnIndex, rowIndex);
            _cells[columnIndex][rowIndex].reveal(adjacentMineCount);
            _revealedCellCount++;
            updateProgress();
            if (adjacentMineCount == 0) {
                forEachAdjacentCell(columnIndex, rowIndex,
                        (i, j) -> explore(i, j));
            }
        }
    }

    private void initialize(int columnIndex, int rowIndex) {
        if (isInitialized() || isExploded()) {
            return;
        }
        for (int i = 0; i < getColumnCount(); i++) {
            for (int j = 0; j < getRowCount(); j++) {
                if (!_cells[i][j].hasMine() && !_cells[i][j].isRevealed()
                        && (1 < Math.abs(i - columnIndex)
                                || 1 < Math.abs(j - rowIndex))
                        && 0.8 < App.random.nextDouble()) {
                    _cells[i][j].placeMine();
                    _safeCellCount--;
                }
            }
        }
        _isInitialized = true;
        App.getMenu().stopwatch.start();
    }

    private void explore(int columnIndex, int rowIndex) {
        if (!isInitialized() || isExploded()
                || _cells[columnIndex][rowIndex].hasMine()
                || _cells[columnIndex][rowIndex].isRevealed()
                || _cells[columnIndex][rowIndex].isFlagged()) {
            return;
        }
        int adjacentMineCount = getAdjacentMineCount(columnIndex, rowIndex);
        _cells[columnIndex][rowIndex].reveal(adjacentMineCount);
        _revealedCellCount++;
        updateProgress();
        if (adjacentMineCount == 0) {
            forEachAdjacentCell(columnIndex, rowIndex, (i, j) -> explore(i, j));
        }
    }

    private void updateProgress() {
        if (isExploded()) {
            return;
        }
        _isExploded = _revealedCellCount == _safeCellCount;
        if (isExploded()) {
            App.getMenu().progress.setText("You Win!");
            App.getMenu().stopwatch.stop();
        } else {
            App.getMenu().progress.setText(Integer
                    .toString((int) Math.floor((double) _revealedCellCount
                            / (double) _safeCellCount * 100.))
                    + '%');
        }
    }

    private int getAdjacentMineCount(int columnIndex, int rowIndex) {
        AtomicInteger adjacentMineCount = new AtomicInteger();
        forEachAdjacentCell(columnIndex, rowIndex, (i, j) -> {
            if (_cells[i][j].hasMine()) {
                adjacentMineCount.getAndIncrement();
            }
        });
        return adjacentMineCount.get();
    }

    private void forEachAdjacentCell(int columnIndex, int rowIndex,
            BiConsumer<Integer, Integer> operation) {
        for (int i = columnIndex - 1; i <= columnIndex + 1; i++) {
            for (int j = rowIndex - 1; j <= rowIndex + 1; j++) {
                if (0 <= i && i < getColumnCount() && 0 <= j
                        && j < getRowCount()
                        && (i != columnIndex || j != rowIndex)) {
                    operation.accept(i, j);
                }
            }
        }
    }

    private boolean _isInitialized;
    private boolean _isExploded;
    private Cell[][] _cells;
    private int _safeCellCount;
    private int _revealedCellCount;
}
