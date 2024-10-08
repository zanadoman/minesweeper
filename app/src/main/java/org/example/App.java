package org.example;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class App extends Application {
    public static final Random random = new Random();

    public static void main() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        MineField mineField = new MineField(15, 15);
        Scene scene = new Scene(new VBox(new Menu(), mineField));
        scene.getStylesheets().add("/style.css");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Minesweeper");
        stage.getIcons().add(Resources.instance.getFlag());
        stage.setMinWidth(mineField.getColumnCount() * 50);
        stage.setMinHeight(75 + mineField.getRowCount() * 50);
        stage.setMaxWidth(stage.getMinWidth());
        stage.setMaxHeight(stage.getMinHeight());
        stage.setScene(scene);
        stage.show();
    }
}
