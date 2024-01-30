package com.table_de_tri_fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PageMain.fxml"));
            Parent root = loader.load();
            // Accédez au contrôleur directement
           Controller mainController = loader.getController();
            primaryStage.setTitle("Fenêtre Principale");
            primaryStage.setFullScreen(true);
            primaryStage.setScene(new Scene(root, 30, 200));

            // Passez la référence de la fenêtre principale au contrôleur
            mainController.setPrimaryStage(primaryStage);

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}