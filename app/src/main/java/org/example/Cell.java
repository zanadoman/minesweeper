package org.example;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
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
                if (isRevealed()) {
                    return;
                }
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (!isFlagged()) {
                        ((MineField) getParent()).reveal(getX(), getY());
                    }
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
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
        _hasMine = true;
    }

    public boolean isRevealed() {
        return _isRevealed;
    }

    public void reveal(int adjacentMineCount) {
        if (isRevealed() || isFlagged()) {
            return;
        }
        _isRevealed = true;
        getStyleClass().remove("cell" + getStyleID());
        getStyleClass().add("cell-revealed" + getStyleID());
        if (0 < adjacentMineCount) {
            getStyleClass().add("cell-adjacent-mine-count" + adjacentMineCount);
            setText(Integer.toString(adjacentMineCount));
        }
        setCursor(Cursor.DEFAULT);
    }

    public void explode() {
        if (!hasMine() || isRevealed()) {
            return;
        }
        if (isFlagged()) {
            removeFlag();
        }
        _hasMine = false;
        _isRevealed = true;
        getStyleClass().remove("cell" + getStyleID());
        getStyleClass().add("cell-exploded" + App.random.nextInt(1, 9));
        Circle circle = new Circle((getWidth() + getHeight()) / 8);
        circle.getStyleClass().add("cell-circle");
        setGraphic(circle);
        setCursor(Cursor.DEFAULT);
    }

    public boolean isFlagged() {
        return _isFlagged;
    }

    public void placeFlag() {
        if (isRevealed() || isFlagged()) {
            return;
        }
        _isFlagged = true;
        ImageView imageView = new ImageView(Resources.instance.getFlag());
        imageView.setFitWidth(getWidth());
        imageView.setFitHeight(getHeight());
        imageView.setPreserveRatio(true);
        setGraphic(imageView);
    }

    public void removeFlag() {
        if (isRevealed() || !isFlagged()) {
            return;
        }
        _isFlagged = false;
        setGraphic(null);
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

    private boolean _hasMine;
    private boolean _isRevealed;
    private boolean _isFlagged;
}
