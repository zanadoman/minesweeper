package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;

public final class Cell extends Button {
    public Cell() {
        Platform.runLater(() -> {
            getStyleClass().addAll("borderless", "cell", "cell" + getStyleID());
            setCursor(Cursor.HAND);
            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    reveal();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    flag();
                }
            });
        });
        _hasMine = false;
        _isFlagged = false;
        _isRevealed = false;
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
        return getX() % 2 == 0 ? getY() % 2 == 0 ? 1 : 2
                : getY() % 2 == 0 ? 2 : 1;
    }

    public void placeMine() {
        _hasMine = true;
    }

    private void flag() {
        if (!getField().isInitialized() || _isRevealed) {
            return;
        }
        _isFlagged = !_isFlagged;
        if (_isFlagged) {
            ImageView image = new ImageView(Resources.instance.getFlag());
            image.setFitWidth(getWidth());
            image.setFitHeight(getHeight());
            image.setPreserveRatio(true);
            setGraphic(image);
        } else {
            setGraphic(null);
        }
    }

    private void reveal() {
        if (_isRevealed || _isFlagged) {
            return;
        }
        _isRevealed = true;
        getStyleClass().remove("cell" + getStyleID());
        if (!getField().isInitialized()) {
            getField().initialize(getX(), getY());
        }
        if (_hasMine) {
            getStyleClass().add("cell" + getStyleID() + "-exploded");
            setGraphic(null);
            for (Cell[] cells : getField().getCells()) {
                for (Cell cell : cells) {
                    if (cell._hasMine) {
                        cell._isFlagged = false;
                        cell.reveal();
                    }
                }
            }
        } else {
            getStyleClass().add("cell" + getStyleID() + "-revealed");
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
        if (getAdjacentMineCount() == 0) {
            forEachAdjacentCell(cell -> cell.explore());
        }
    }

    private void forEachAdjacentCell(Consumer<Cell> operation) {
        for (int i = getX() - 1; i <= getX() + 1; i++) {
            for (int j = getY() - 1; j <= getY() + 1; j++) {
                if (0 <= i && i < getField().getColumnCount() && 0 <= j
                        && j < getField().getRowCount()
                        && (i != getX() || j != getY())) {
                    operation.accept(getField().getCells()[i][j]);
                }
            }
        }
    }

    private int getAdjacentMineCount() {
        AtomicInteger count = new AtomicInteger();
        forEachAdjacentCell(cell -> {
            if (cell._hasMine) {
                count.incrementAndGet();
            }
        });
        return count.get();
    }

    private boolean _hasMine;
    private boolean _isFlagged;
    private boolean _isRevealed;
}
