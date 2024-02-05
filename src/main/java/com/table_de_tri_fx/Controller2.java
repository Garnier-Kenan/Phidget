package com.table_de_tri_fx;

import com.phidget22.PhidgetException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {
    public Label labelPain;
    public Label labelEmballages;
    public Label labelAlimentaires;
    public String prenom = new String(""), nom = new String("");
    public Boolean state = false;
    private Timeline timeoutTimeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    public void rfid() {
        if (state) {
            Platform.runLater(() -> {
                resetTimeout();
            });
        } else {
            try {
                openConnexion();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void openConnexion() throws IOException {
        DATA_Scene.primaryStage.setScene(DATA_Scene.scene1);
        DATA_Scene.primaryStage.setTitle("Fenêtre Connection");
        DATA_Scene.primaryStage.setFullScreen(true);
        DATA_Scene.primaryStage.show();
        Platform.runLater(() -> DATA_Scene.controller.labelScann.setText("Erreur utilisateur"));
        DATA_Scene.position = false;
    }

    public void resetTimeout() {
        timeoutTimeline.stop();
        timeoutTimeline.play();
    }
}
