package org.example;

import javafx.scene.image.Image;

public final class Resources {
    private Resources() {
        _flag = null;
    }

    public Image getFlag() {
        if (_flag == null) {
            _flag = new Image("/flag.png");
        }
        return _flag;
    }

    public static final Resources instance = new Resources();
    private Image _flag;
}
