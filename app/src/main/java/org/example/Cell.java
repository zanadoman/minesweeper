package org.example;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public final class Cell extends Button {
    public Cell() {
        Platform.runLater(() -> {
            getStyleClass().addAll("borderless", "cell", "cell" + getStyleID());
            setCursor(Cursor.HAND);
            setOnMouseClicked(mouseEvent -> {
                if (((MineField) getParent()).isExploded() || isRevealed()) {
                    return;
                }
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (!isFlagged()) {
                        ((MineField) getParent()).reveal(
                                GridPane.getColumnIndex(this),
                                GridPane.getRowIndex(this));
                    }
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    if (!((MineField) getParent()).isInitialized()) {
                        return;
                    }
                    if (isFlagged()) {
                        removeFlag();
                    } else {
                        placeFlag();
                    }
                }
            });
        });
        _hasMine = false;
        _isRevealed = false;
        _isFlagged = false;
    }

    public boolean hasMine() {
        return _hasMine;
    }

    public void placeMine() {
        if (((MineField) getParent()).isInitialized()
                || ((MineField) getParent()).isExploded()) {
            return;
        }
        if (isRevealed() || hasMine()) {
            return;
        }
        _hasMine = true;
    }

    public boolean isRevealed() {
        return _isRevealed;
    }

    public void reveal(int adjacentMineCount) {
        if (!((MineField) getParent()).isInitialized()
                || ((MineField) getParent()).isExploded()) {
            return;
        }
        if (hasMine() || isRevealed() || isFlagged()) {
            return;
        }
        getStyleClass().remove("cell" + getStyleID());
        getStyleClass().add("cell-revealed" + getStyleID());
        if (0 < adjacentMineCount) {
            getStyleClass().add("cell-adjacent-mine-count" + adjacentMineCount);
            setText(Integer.toString(adjacentMineCount));
        }
        setCursor(Cursor.DEFAULT);
        _isRevealed = true;
    }

    public void explode() {
        if (!((MineField) getParent()).isInitialized()
                || ((MineField) getParent()).isExploded()) {
            return;
        }
        if (!hasMine() || isRevealed()) {
            return;
        }
        if (isFlagged()) {
            removeFlag();
        }
        getStyleClass().remove("cell" + getStyleID());
        getStyleClass().add("cell-exploded" + App.random.nextInt(1, 9));
        Circle circle = new Circle((getWidth() + getHeight()) / 8);
        circle.getStyleClass().add("cell-circle");
        setGraphic(circle);
        setCursor(Cursor.DEFAULT);
        _hasMine = false;
        _isRevealed = true;
    }

    public boolean isFlagged() {
        return _isFlagged;
    }

    public void placeFlag() {
        if (!((MineField) getParent()).isInitialized()
                || ((MineField) getParent()).isExploded()) {
            return;
        }
        if (isRevealed() || isFlagged()) {
            return;
        }
        ImageView imageView = new ImageView(Resources.instance.getFlag());
        imageView.setFitWidth(getWidth() * 2);
        imageView.setFitHeight(getHeight());
        // imageView.setPreserveRatio(true);
        setGraphic(imageView);
        _isFlagged = true;
    }

    public void removeFlag() {
        if (!((MineField) getParent()).isInitialized()
                || ((MineField) getParent()).isExploded()) {
            return;
        }
        if (isRevealed() || !isFlagged()) {
            return;
        }
        setGraphic(null);
        _isFlagged = false;
    }

    private int getStyleID() {
        return GridPane.getColumnIndex(this) % 2 == 0
                ? GridPane.getRowIndex(this) % 2 == 0 ? 1 : 2
                : GridPane.getRowIndex(this) % 2 == 0 ? 2 : 1;
    }

    private boolean _hasMine;
    private boolean _isRevealed;
    private boolean _isFlagged;
}
