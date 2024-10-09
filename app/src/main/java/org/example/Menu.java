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
        Region region1 = new Region();
        region1.setPrefWidth(5);
        progress = new Label();
        progress.getStyleClass().add("menu-label");
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        Region region3 = new Region();
        region3.setPrefWidth(10);
        Region region4 = new Region();
        region4.setPrefWidth(10);
        HBox hBox = new HBox(region1, progress, region2,
                newButton(Resources.restart,
                        eventHandler -> App.getMineField().clear(
                                App.getMineField().getColumnCount(),
                                App.getMineField().getRowCount())),
                region3,
                newButton(Resources.minimize,
                        eventHandler -> App.getStage().setIconified(true)),
                region4,
                newButton(Resources.quit, eventHandler -> Platform.exit()));
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
