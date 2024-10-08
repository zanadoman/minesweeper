package org.example;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public final class Menu extends HBox {
    public Menu() {
        getStyleClass().add("menu");
        progress = new Label();
        progress.getStyleClass().add("menu-label");
        stopwatch = new Stopwatch();
        getChildren().addAll(
                progress,
                stopwatch,
                newButton(Resources.instance.getRestart(),
                        eventHandler -> App.getMineField().clear(
                                App.getMineField().getColumnCount(),
                                App.getMineField().getRowCount())),
                newButton(Resources.instance.getQuit(),
                        eventHandler -> Platform.exit()));
    }

    public final Label progress;

    public final Stopwatch stopwatch;

    private static Button newButton(Image image,
            EventHandler<? super MouseEvent> eventHandler) {
        Button button = new Button();
        button.getStyleClass().add("menu-button");
        ImageView imageView = new ImageView(image);
        Platform.runLater(() -> {
            imageView.setFitWidth(button.getWidth());
            imageView.setFitHeight(button.getHeight());
        });
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setCursor(Cursor.HAND);
        button.setOnMouseClicked(eventHandler);
        return button;
    }
}
