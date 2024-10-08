package org.example;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class App extends Application {
    public static void main() {
        launch();
    }

    public static final Random random = new Random();

    public static Menu getMenu() {
        return _menu;
    }

    public static MineField getMineField() {
        return _mineField;
    }

    @Override
    public void start(Stage stage) {
        _menu = new Menu();
        _mineField = new MineField(15, 15);
        Scene scene = new Scene(new VBox(getMenu(), getMineField()));
        scene.getStylesheets().add("/style.css");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Minesweeper");
        stage.getIcons().add(Resources.instance.getFlag());
        stage.setMinWidth(getMineField().getColumnCount() * 50);
        stage.setMinHeight(75 + getMineField().getRowCount() * 50);
        stage.setMaxWidth(stage.getMinWidth());
        stage.setMaxHeight(stage.getMinHeight());
        stage.setScene(scene);
        stage.show();
    }

    private static Menu _menu = null;
    private static MineField _mineField = null;
}
