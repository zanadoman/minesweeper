package org.example;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

public final class Mine implements Cloneable {
    public Mine(boolean hasMine) {
        _button = new Button();
        _button.setStyle("-fx-background-color: lightgrey;");
        _button.setCursor(Cursor.HAND);
        this.hasMine = hasMine;
        setNeighbourCount(0);
        _isFlagged = false;
        _isCleared = false;
        _button.setPrefSize(50, 50);
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
        _isFlagged = !IsFlagged();
        if (!IsCleared()) {
            _button.setText(IsFlagged() ? "X" : "");
        }
    }

    public boolean IsCleared() {
        return _isCleared;
    }

    public void clear() {
        _isCleared = true;
        if (hasMine) {
            _button.setStyle("-fx-background-color: red;");
        } else {
            _button.setStyle("-fx-background-color: lightgreen;");
        }
        if (0 < getNeighbourCount()) {
            _button.setText(Integer.toString(getNeighbourCount()));
        }
    }

    public Object clone() {
        return new Mine(hasMine);
    }

    private Button _button;
    public final boolean hasMine;
    private int _neighbourCount;
    private boolean _isFlagged;
    private boolean _isCleared;
}
