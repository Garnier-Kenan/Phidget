package com.table_de_tri_fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.control.Button;




import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader_Principale = new FXMLLoader(getClass().getResource("PagePrincipale_V2.fxml"));
            Scene scene_Principale = new Scene(fxmlLoader_Principale.load());
            FXMLLoader fxmlLoader_Connexion = new FXMLLoader(Application.class.getResource("PageDeConnexion_V2.fxml"));
            Scene scene_Connexion = new Scene(fxmlLoader_Connexion.load());
            primaryStage.setTitle("Table de Tri");
            primaryStage.setResizable(false);
            Screen screen = Screen.getPrimary();
            primaryStage.setWidth(screen.getVisualBounds().getWidth());
            primaryStage.setHeight(screen.getVisualBounds().getHeight());
            primaryStage.setScene(scene_Connexion);
            primaryStage.setFullScreenExitHint("");
            primaryStage.setFullScreen(true);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
                Controller.close();
            });
            DATA_Scene.primaryStage=primaryStage;
            DATA_Scene.scene1=scene_Connexion;
            DATA_Scene.scene2=scene_Principale;
            DATA_Scene.controller=fxmlLoader_Connexion.getController();
            DATA_Scene.controller2=fxmlLoader_Principale.getController();
            DATA_Scene.position = false;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}