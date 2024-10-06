package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public final class Cell extends Button {
    public Cell(boolean hasMine) throws Exception {
        getStyleClass().addAll("borderless", "mine", "mine" + getStyleID());
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
        this.hasMine = hasMine;
        _isFlagged = false;
        _isRevealed = false;
    }

    private Field getField() throws Exception {
        Parent parent = getParent();
        if (!(parent instanceof Field)) {
            throw new IllegalStateException("Illegal Parent");
        }
        return (Field) parent;
    }

    private int getX() {
        return GridPane.getColumnIndex(this);
    }

    private int getY() {
        return GridPane.getRowIndex(this);
    }

    private int getStyleID() {
        return getX() % 2 == 0 ? getY() % 2 == 0 ? 1 : 2 : getY() % 2 == 0 ? 2 : 1;
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
        }
    }

    private void reveal() throws Exception {
        if (_isRevealed || _isFlagged) {
            return;
        }
        _isRevealed = true;
        getStyleClass().remove("mine" + getStyleID());
        if (hasMine) {
            getStyleClass().add("mine" + getStyleID() + "-exploded");
            setGraphic(null);
            for (Cell[] cells : getField().cells) {
                for (Cell cell : cells) {
                    if (cell.hasMine) {
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
            }
            forEachAdjacentCell(cell -> cell.explore());
        }
        setCursor(Cursor.DEFAULT);
    }

    private void explore() throws Exception {
        if (hasMine || _isRevealed) {
            return;
        }
        reveal();
        if (getAdjacentMineCount() == 0) {
            forEachAdjacentCell(cell -> cell.explore());
        }
    }

    private void forEachAdjacentCell(Consumer<Cell> operation) throws Exception {
        for (int x = getX() - 1; x <= getX() + 1; x++) {
            for (int y = getY() - 1; y <= getY() + 1; y++) {
                if (0 <= x && x < getField().width && 0 <= y && y < getField().height) {
                    operation.accept(getField().cells[x][y]);
                }
            }
        }
    }

    private int getAdjacentMineCount() throws Exception {
        AtomicInteger adjacentMineCount = new AtomicInteger();
        forEachAdjacentCell(cell -> {
            if (cell.hasMine) {
                adjacentMineCount.incrementAndGet();
            }
        });
        return adjacentMineCount.get();
    }

    private final boolean hasMine;
    private boolean _isFlagged;
    private boolean _isRevealed;
}
