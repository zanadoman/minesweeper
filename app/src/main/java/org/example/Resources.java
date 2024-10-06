package org.example;

import javafx.scene.image.Image;

public final class Resources {
    private Resources() {
        _flag = null;
        _mine = null;
    }

    public Image getFlag() {
        if (_flag == null) {
            _flag = new Image("/flag.png");
        }
        return _flag;
    }

    public Image getMine() {
        if (_mine == null) {
            _mine = new Image("/mine.png");
        }
        return _mine;
    }

    public static final Resources instance = new Resources();
    private Image _flag;
    private Image _mine;
}
