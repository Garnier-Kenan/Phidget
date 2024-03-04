package com.table_de_tri_fx;


import com.phidget22.PhidgetException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Label labelScann;
    public Boolean state = false;
    private static Gestion gestion;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            gestion = new Gestion();
            DATA_Scene.gestion = gestion;
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }
    public void rfid() {
        if (state) {
            Platform.runLater(() -> {
                DATA_Scene.controller2.resetTimeout();
                try {
                    openPrincipale();
                    DATA_Scene.controller2.labelUser.setText("Bojour " + DATA_Scene.controller2.prenom + " " + DATA_Scene.controller2.nom);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            Platform.runLater(() -> {
                labelScann.setText("Erreur carte inconnue");
            });
        }
    }

    public void openPrincipale() throws IOException {
        DATA_Scene.primaryStage.setScene(DATA_Scene.scene2);
        DATA_Scene.primaryStage.setTitle("Fenêtre Principale");
        DATA_Scene.position = true;
    }
    public static void close() {
        try {
            gestion.close();
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }
}



