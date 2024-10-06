package org.example;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public final class Mine {
    public Mine(int x, int y, boolean hasMine) {
        _style = y % 2 == 0 ? x % 2 == 0 ? 1 : 2 : x % 2 == 0 ? 2 : 1;
        _button = new Button();
        // _button.setPrefSize(50, 50);
        _button.setFocusTraversable(false);
        _button.setCursor(Cursor.HAND);
        _button.getStyleClass().add("borderless");
        _button.getStyleClass().add("mine");
        _button.getStyleClass().add("mine" + _style);
        this.x = x;
        this.y = y;
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
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
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
        if (hasMine) {
            _button.setStyle(_button.getStyle() + "-fx-background-color: red;");
        } else {
            _button.getStyleClass().remove("mine" + _style);
            _button.getStyleClass().add("mine" + _style + "-explored");
        }
        if (0 < getNeighbourCount()) {
            _button.setText(Integer.toString(getNeighbourCount()));
            String color;
            switch (getNeighbourCount()) {
                case 1:
                    color = "#0000FD";
                    break;
                case 2:
                    color = "#017E00";
                    break;
                case 3:
                    color = "#FE0000";
                    break;
                case 4:
                    color = "#010082";
                    break;
                case 5:
                    color = "#830003";
                    break;
                case 6:
                    color = "#008080";
                    break;
                case 7:
                    color = "#000000";
                    break;
                default:
                    color = "#808080";
                    break;
            }
            _button.setStyle(_button.getStyle() + "-fx-text-fill: " + color + ';');
        }
    }

    private Button _button;
    public final int x;
    public final int y;
    public final boolean hasMine;
    private int _neighbourCount;
    private boolean _isFlagged;
    private boolean _isCleared;
    private final int _style;
}
