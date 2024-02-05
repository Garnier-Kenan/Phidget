package com.table_de_tri_fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.control.Button;




import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("PageDeConnexion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setFullScreenExitHint("");
            primaryStage.setTitle("Table de Tri");
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> System.exit(0));
            primaryStage.setFullScreen(true);
            primaryStage.show();
            Controller.setPrimaryStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}