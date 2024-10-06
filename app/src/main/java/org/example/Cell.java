package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import javafx.scene.control.Button;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;

public final class Cell extends Button {
    public Cell() {
        getStyleClass().addAll("borderless", "mine", "mine1");
        setCursor(Cursor.HAND);
        setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY:
                    reveal();
                    break;
                case SECONDARY:
                    flag();
                    break;
                default:
                    break;
            }
        });
        _hasMine = false;
        _isFlagged = false;
        _isRevealed = false;
    }

    public void placeMine() {
        _hasMine = true;
    }

    private MineField getField() {
        Parent parent = getParent();
        return (MineField) parent;
    }

    private int getX() {
        return GridPane.getColumnIndex(this);
    }

    private int getY() {
        return GridPane.getRowIndex(this);
    }

    private int getStyleID() {
        return getX() % 2 == 0
                ? getY() % 2 == 0
                        ? 1
                        : 2
                : getY() % 2 == 0
                        ? 2
                        : 1;
    }

    private void flag() {
        if (_isRevealed) {
            return;
        }
        _isFlagged = !_isFlagged;
        if (_isFlagged) {
            ImageView imageView = new ImageView(Resources.instance.getFlag());
            imageView.setFitWidth(getWidth());
            imageView.setFitHeight(getHeight());
            imageView.setPreserveRatio(true);
            setGraphic(imageView);
        } else {
            setGraphic(null);
        }
    }

    private void reveal() {
        if (_isRevealed || _isFlagged) {
            return;
        }
        _isRevealed = true;
        getStyleClass().remove("mine" + getStyleID());
        if (!getField().isInitialized()) {
            getField().initialize(getX(), getY());
        }
        if (_hasMine) {
            getStyleClass().add("mine" + getStyleID() + "-exploded");
            setGraphic(null);
            for (Cell[] cells : getField().getCells()) {
                for (Cell cell : cells) {
                    if (cell._hasMine) {
                        cell.reveal();
                    }
                }
            }
        } else {
            getStyleClass().add("mine" + getStyleID() + "-cleared");
            setGraphic(null);
            if (0 < getAdjacentMineCount()) {
                getStyleClass().add("neighbour-count" + getAdjacentMineCount());
                setText(Integer.toString(getAdjacentMineCount()));
            } else {
                forEachAdjacentCell(cell -> cell.explore());
            }
        }
        setCursor(Cursor.DEFAULT);
    }

    private void explore() {
        if (_hasMine || _isRevealed) {
            return;
        }
        reveal();
        System.out.println(getAdjacentMineCount());
        if (getAdjacentMineCount() == 0) {
            System.out.println(getAdjacentMineCount());
            forEachAdjacentCell(cell -> cell.explore());
        }
    }

    private void forEachAdjacentCell(Consumer<Cell> operation) {
        for (int x = getX() - 1; x <= getX() + 1; x++) {
            for (int y = getY() - 1; y <= getY() + 1; y++) {
                if (0 <= x && x < getField().getColumnCount()
                        && 0 <= y && y < getField().getRowCount()) {
                    operation.accept(getField().getCells()[x][y]);
                }
            }
        }
    }

    private int getAdjacentMineCount() {
        AtomicInteger adjacentMineCount = new AtomicInteger();
        forEachAdjacentCell(cell -> {
            if (cell._hasMine) {
                adjacentMineCount.incrementAndGet();
            }
        });
        return adjacentMineCount.get();
    }

    private boolean _hasMine;
    private boolean _isFlagged;
    private boolean _isRevealed;
}
