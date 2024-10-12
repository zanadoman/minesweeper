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

    public static Stage getStage() {
        return _stage;
    }

    public static Menu getMenu() {
        return _menu;
    }

    public static MineField getMineField() {
        return _mineField;
    }

    @Override
    public void start(Stage stage) {
        _stage = stage;
        _menu = new Menu();
        _mineField = new MineField(15, 15);
        Scene scene = new Scene(new VBox(getMenu(), getMineField()));
        scene.getStylesheets().add("/style.css");
        _stage.initStyle(StageStyle.UNDECORATED);
        _stage.setMinWidth(getMineField().getColumnCount() * 50);
        _stage.setMinHeight(75 + getMineField().getRowCount() * 50);
        _stage.setMaxWidth(_stage.getMinWidth());
        _stage.setMaxHeight(_stage.getMinHeight());
        _stage.setTitle("Minesweeper");
        _stage.getIcons().add(Resources.flag);
        _stage.setScene(scene);
        _stage.show();
    }

    private static Stage _stage = null;
    private static Menu _menu = null;
    private static MineField _mineField = null;
}
