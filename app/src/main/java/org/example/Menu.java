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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public final class Menu extends StackPane {
    public Menu() {
        getStyleClass().add("menu");
        setOnMousePressed(pressEvent -> {
            setOnMouseDragged(dragEvent -> {
                App.getStage()
                        .setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                App.getStage()
                        .setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        Region gap1 = new Region();
        gap1.setPrefWidth(5);
        progress = new Label();
        progress.getStyleClass().add("menu-label");
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        Region gap2 = new Region();
        gap2.setPrefWidth(10);
        Region gap3 = new Region();
        gap3.setPrefWidth(10);
        HBox hBox = new HBox(gap1, progress, filler,
                newButton(Resources.instance.getRestart(),
                        eventHandler -> App.getMineField().clear(
                                App.getMineField().getColumnCount(),
                                App.getMineField().getRowCount())),
                gap2,
                newButton(Resources.instance.getMinimize(),
                        eventHandler -> App.getStage().setIconified(true)),
                gap3,
                newButton(Resources.instance.getQuit(),
                        eventHandler -> Platform.exit()));
        hBox.getStyleClass().add("menu");
        stopwatch = new Stopwatch();
        getChildren().addAll(hBox, stopwatch);
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
