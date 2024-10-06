package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        _minefield = new Minefield(gridPane, 11, 11);
        Scene scene = new Scene(gridPane, _minefield.Width * 50,
                _minefield.Height * 50);
        scene.getStylesheets()
                .add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private Minefield _minefield;
}
