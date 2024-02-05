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

    public Label labelAlimentaires;
    private static Stage primaryStage;

    public Label labelEmballages;
    public Label labelScann;
    public Label labelPain;


    String prenom = new String("");
    Boolean state = true;
    String nom = new String("");
    private static Gestion gestion;
    private Thread thread;
    private Timeline timeoutTimeline;
    String data = "152121";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            gestion = new Gestion(this);
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
        timeoutTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(10),
                        event -> {
                            try {
                                openConnexion();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));
    }
    public static void setPrimaryStage(Stage refprimaryStage) {
        primaryStage = refprimaryStage;
    }
    public void rfid() {
        if (state) {
            Platform.runLater(() -> {
                try {
                    openPrincipale();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                resetTimeout();
            });
        } else {
            Platform.runLater(() -> labelScann.setText("Erreur utilisateur"));
        }
    }

    private void openPrincipale() throws IOException {
        ;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PagePrincipale.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Fenêtre Principale");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }


    private void openConnexion() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PageDeConnexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Fenêtre Connection");
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void resetTimeout() {
        timeoutTimeline.stop();
        timeoutTimeline.play();
    }
}



