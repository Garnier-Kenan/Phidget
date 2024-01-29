package com.table_de_tri_fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("PageConnexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Table de tri ");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            Controller.close();
            System.exit(0);
        });

    }
    public static void main(String[] args) {

        launch();

    }
}