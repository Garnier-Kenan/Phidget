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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Label labelScann;
    public Label labelSemaine;
    public Label labelService;
    private static Gestion gestion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            gestion = new Gestion();
            DATA_Scene.gestion = gestion;


        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
        labelService.setText("0.000");
        labelSemaine.setText(String.valueOf(gestion.initePageP()));
    }

    public void rfid(Boolean state) {
        if (state) {
            openPrincipale();
        } else {
            Platform.runLater(() -> labelScann.setText("Erreur carte inconnue"));

        }
    }

    public void openPrincipale() {
        Platform.runLater(() ->
        {
            DATA_Scene.primaryStage.setScene(DATA_Scene.scene2);
            DATA_Scene.primaryStage.setTitle("Fenêtre Principale");
            DATA_Scene.primaryStage.setFullScreen(true);
            DATA_Scene.position = true;
            DATA_Scene.controller2.labelUser.setText("Bojour " + DATA_Scene.controller2.prenom + " " + DATA_Scene.controller2.nom);
            DATA_Scene.controller2.resetTimeout();
        });
    }

    public static void close() {
        try {
            gestion.close();
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }

}



