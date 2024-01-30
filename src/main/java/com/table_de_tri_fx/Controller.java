package com.table_de_tri_fx;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Label labelAlimentaires;
    public Label labelPain;
    public Label labelEmballages;
    public Label labelScann;
    public Button btnSimulate ;

    String prenom = new String("");
    Boolean state = true;
    String nom = new String("");
    private static Gestion gestion;
    private Thread thread;
    private Stage primaryStage;
    private Timeline timeoutTimeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialisation le Timeline pour le timeout
        timeoutTimeline = new Timeline(new KeyFrame(
                Duration.seconds(10), //Temp du timeout
                event -> eventTimeout()));
        btnSimulate.setOnAction(event -> {
            processBoolean(true);
            resetTimeout();
        });
    }


    private void openWindow() {
        try {
            Stage newStage = new Stage();
            // Chargez le fichier FXML de votre seconde fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PageConnexion.fxml"));
            newStage.setScene(new Scene(loader.load()));
            newStage.setFullScreen(true);
            newStage.setTitle("Table Principale");
            newStage.show();
            closePrimaryStage();
            resetTimeout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // ...
    private void eventTimeout() {
        System.out.println("Timeout occurred. Returning to main window.");

        // Code pour revenir à la fenêtre principale
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PageMain.fxml"));
            newStage.setScene(new Scene(loader.load()));
            newStage.setFullScreen(true);

            Controller mainController = loader.getController();
            mainController.setPrimaryStage(newStage);

            newStage.setTitle("Fenêtre Principale");
            newStage.show();


            // Fermez la fenêtre actuelle
            Stage currentStage = (Stage) btnSimulate.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private void processBoolean(boolean condition) {
            if (condition) {
                closePrimaryStage();
                openWindow();
            }
        }

    private void closePrimaryStage() {
        Stage stage = (Stage) btnSimulate.getScene().getWindow();
        stage.close();
    }







    private void resetTimeout() {
        timeoutTimeline.stop();
        timeoutTimeline.play();
    }




}



