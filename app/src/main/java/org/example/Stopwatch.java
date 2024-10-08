package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public final class Stopwatch extends Label {
    public Stopwatch() {
        getStyleClass().add("menu-label");
        setText(0, 0);
        _timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), actionEvent -> update()));
        _timeline.setCycleCount(Timeline.INDEFINITE);
        _startTimeMillis = -1;
    }

    public void start() {
        _startTimeMillis = System.currentTimeMillis();
        _timeline.play();
    }

    public void stop() {
        _timeline.stop();
        _startTimeMillis = -1;
    }

    public void reset() {
        stop();
        setText(0, 0);
    }

    private void update() {
        long elapsedTimeSeconds = (System.currentTimeMillis()
                - _startTimeMillis) / 1000;
        setText(elapsedTimeSeconds / 60, elapsedTimeSeconds % 60);
    }

    private void setText(long minutes, long seconds) {
        setText(String.format("%02d:%02d", minutes, seconds));
    }

    private Timeline _timeline;
    private long _startTimeMillis;
}
