package org.example;

import javafx.scene.image.Image;

public final class Resources {
    public static final Resources instance = new Resources();

    public Image getRestart() {
        if (_restart == null) {
            _restart = new Image("/restart.png");
        }
        return _restart;
    }

    public Image getMinimize() {
        if (_minimize == null) {
            _minimize = new Image("/minimize.png");
        }
        return _minimize;
    }

    public Image getQuit() {
        if (_quit == null) {
            _quit = new Image("/quit.png");
        }
        return _quit;
    }

    public Image getFlag() {
        if (_flag == null) {
            _flag = new Image("/flag.png");
        }
        return _flag;
    }

    private Resources() {
        _restart = null;
        _minimize = null;
        _quit = null;
        _flag = null;
    }

    private Image _restart;
    private Image _minimize;
    private Image _quit;
    private Image _flag;
}
