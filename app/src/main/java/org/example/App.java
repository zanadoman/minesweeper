package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public final class App extends Application {
    @Override
    public void start(Stage stage) {
        // GridPane gridPane = new GridPane();
        // _minefield = new Minefield(gridPane, 11, 11);
        // Scene scene = new Scene(gridPane);
        // scene.getStylesheets().add("/style.css");
        // stage.setScene(scene);
        // stage.show();

        Field field = new Field(10, 10);
        Scene scene = new Scene(field);
        scene.getStylesheets().add("/style.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // private Minefield _minefield;
}
