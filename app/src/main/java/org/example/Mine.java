package org.example;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public final class Mine {
    public Mine(int x, int y, boolean hasMine) {
        _style = y % 2 == 0 ? x % 2 == 0 ? 1 : 2 : x % 2 == 0 ? 2 : 1;
        _button = new Button();
        _button.setCursor(Cursor.HAND);
        _button.getStyleClass().add("borderless");
        _button.getStyleClass().add("mine");
        _button.getStyleClass().add("mine" + _style);
        this.hasMine = hasMine;
        setNeighbourCount(0);
        _isFlagged = false;
    }

    public Button getButton() {
        return _button;
    }

    public int getNeighbourCount() {
        return _neighbourCount;
    }

    public void setNeighbourCount(int neighbourCount) {
        _neighbourCount = neighbourCount;
    }

    public boolean IsFlagged() {
        return _isFlagged;
    }

    public void flag() {
        if (IsCleared()) {
            return;
        }
        _isFlagged = !IsFlagged();
        if (IsFlagged()) {
            ImageView imageView = new ImageView(Resources.instance.getFlag());
            imageView.setFitWidth(_button.getWidth());
            imageView.setFitHeight(_button.getHeight());
            imageView.setPreserveRatio(true);
            _button.setGraphic(imageView);
        } else {
            _button.setGraphic(null);
        }
    }

    public boolean IsCleared() {
        return _isCleared;
    }

    public void clear() {
        _isCleared = true;
        _button.getStyleClass().remove("mine" + _style);
        _button.getStyleClass().add("mine" + _style + '-' +
                (hasMine ? "exploded" : "cleared"));
        if (!hasMine && 0 < getNeighbourCount()) {
            _button.setText(Integer.toString(getNeighbourCount()));
            _button.getStyleClass().add("neighbour-count" + getNeighbourCount());
        }
        _button.setCursor(Cursor.DEFAULT);
        _button.setGraphic(null);
    }

    private Button _button;
    public final boolean hasMine;
    private int _neighbourCount;
    private boolean _isFlagged;
    private boolean _isCleared;
    private final int _style;
}
