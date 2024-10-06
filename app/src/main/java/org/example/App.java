package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class App extends Application {
    @Override
    public void start(Stage stage) {
        MineField field = new MineField(15, 15);
        Scene scene = new Scene(field);
        scene.getStylesheets().add("/style.css");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Minesweeper");
        stage.getIcons().add(Resources.instance.getFlag());
        stage.setMinWidth(field.getColumnCount() * 50);
        stage.setMinHeight(field.getRowCount() * 50);
        stage.setMaxWidth(stage.getMinWidth());
        stage.setMaxHeight(stage.getMinHeight());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
