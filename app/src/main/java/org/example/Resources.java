package org.example;

import javafx.scene.image.Image;

public final class Resources {
    public static final Resources instance = new Resources();

    public Image getFlag() {
        if (_flag == null) {
            _flag = new Image("/flag.png");
        }
        return _flag;
    }

    private Resources() {
        _flag = null;
    }

    private Image _flag;
}
