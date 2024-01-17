package com.table_de_tri_fx;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("PageConnexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 10,10);
        stage.setResizable(false);
        stage.setTitle("Table de tri");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> System.exit(0));
    }

    public static void main(String[] args) {
        launch();

    }
}