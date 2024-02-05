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
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("PagePrincipale.fxml"));
            Scene scene2 = new Scene(fxmlLoader2.load());
            FXMLLoader fxmlLoader1 = new FXMLLoader(Application.class.getResource("PageDeConnexion.fxml"));
            Scene scene1 = new Scene(fxmlLoader1.load());
            primaryStage.setFullScreenExitHint("");
            primaryStage.setTitle("Table de Tri");
            primaryStage.setScene(scene1);
            primaryStage.setFullScreen(true);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
                Controller.close();
            });
            DATA_Scene.controller=fxmlLoader1.getController();
            DATA_Scene.primaryStage=primaryStage;
            DATA_Scene.controller2=fxmlLoader2.getController();
            DATA_Scene.scene1=scene1;
            DATA_Scene.scene2=scene2;
            DATA_Scene.position = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}