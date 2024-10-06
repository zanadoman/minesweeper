package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class App extends Application {
    @Override
    public void start(Stage stage) {
        MineField field = new MineField(15, 15);
        field.getChildren().remove(3);
        Scene scene = new Scene(field);
        scene.getStylesheets().add("/style.css");
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        Platform.runLater(() -> {
            stage.setMinWidth(field.getWidth());
            stage.setMinHeight(field.getHeight());
            stage.setMaxWidth(stage.getMinWidth());
            stage.setMaxHeight(stage.getMinHeight());
            stage.setWidth(stage.getMinWidth());
            stage.setHeight(stage.getMinHeight());
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
