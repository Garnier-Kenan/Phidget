package com.table_de_tri_fx;

import com.phidget22.PhidgetException;
import com.table_de_tri_fx.Ihm.DATA_Scene;
import com.table_de_tri_fx.Phidget.Gestion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage){
        try {
            Gestion gestion = new Gestion();
            DATA_Scene.gestion = gestion;

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
                try {
                    DATA_Scene.gestion.close();
                } catch (PhidgetException e) {
                    throw new RuntimeException(e);
                }
            });

            DATA_Scene.primaryStage=primaryStage;
            DATA_Scene.scene1=scene_Connexion;
            DATA_Scene.scene2=scene_Principale;
            DATA_Scene.controller=fxmlLoader_Connexion.getController();
            DATA_Scene.controller2=fxmlLoader_Principale.getController();
            DATA_Scene.position = false;

        } catch (IOException | PhidgetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}