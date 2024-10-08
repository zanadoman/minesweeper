package org.example;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public final class Menu extends HBox {
    public Menu() {
        getStyleClass().add("menu");
        getChildren().addAll(
                newButton(Resources.instance.getRestart(),
                        eventHandler -> App.mineField.clear(
                                App.mineField.getColumnCount(),
                                App.mineField.getRowCount())),
                newButton(Resources.instance.getQuit(),
                        eventHandler -> Platform.exit()));
    }

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
