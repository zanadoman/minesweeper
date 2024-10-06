package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class App extends Application {
    @Override
    public void start(Stage stage) {
        MineField field = new MineField(15, 20);
        Scene scene = new Scene(field);
        scene.getStylesheets().add("/style.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
