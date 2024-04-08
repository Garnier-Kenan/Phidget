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

            FXMLLoader fxmlLoader_PopUP = new FXMLLoader(getClass().getResource("PagePopup.fxml"));
            Scene scene_PopUP = new Scene(fxmlLoader_PopUP.load());
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
                    gestion.close();
                } catch (PhidgetException e) {
                    throw new RuntimeException(e);
                }
            });
            DATA_Scene.gestion = gestion;
            DATA_Scene.scene_PopUP =scene_PopUP;
            DATA_Scene.primaryStage =primaryStage;
            DATA_Scene.scene_Connexion =scene_Connexion;
            DATA_Scene.scene_Principale =scene_Principale;
            DATA_Scene.controller_Connexion =fxmlLoader_Connexion.getController();
            DATA_Scene.controller_Principale =fxmlLoader_Principale.getController();
            DATA_Scene.controller_popUP =fxmlLoader_PopUP.getController();
            DATA_Scene.position = false;
            DATA_Scene.controller_Connexion.bilan_semaine();
            gestion.start();

        } catch (IOException | PhidgetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}