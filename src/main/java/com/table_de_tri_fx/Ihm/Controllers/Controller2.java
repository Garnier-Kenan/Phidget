package com.table_de_tri_fx.Ihm.Controllers;

import com.phidget22.PhidgetException;
import com.table_de_tri_fx.Ihm.DATA_Scene;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {
    public Label labelPain;
    public Label labelEmballage;
    public Label labelAlimentaire;
    public Label labelUser;
    public ImageView imagePain;
    public ImageView imageEmballage;
    public ImageView imageAlimentaire;
    public ImageView imageLogo;

    public String prenom, nom;
    private Double poidTotalPain = 0.000, poidTotalAlimentaire = 0.000, poidTotalEmballages = 0.000;
    private Boolean flag = false, lockdown = false;
    private Timeline timeoutTimeline;
    private DecimalFormat decimalFormat = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.US));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            labelPain.setText("0.000");
            labelAlimentaire.setText("0.000");
            labelEmballage.setText("0.000");
        });
        timeoutTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(5),
                        event -> {
                            try {
                                ecrire();
                                openConnexion(1);
                            } catch (IOException | PhidgetException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));
    }

    public void ecrire() throws PhidgetException, InterruptedException, IOException {
        flag = false;
        timeoutTimeline.stop();
        poidTotalPain = poidTotalPain + Double.parseDouble(labelPain.getText());
        poidTotalAlimentaire = poidTotalAlimentaire + Double.parseDouble(labelAlimentaire.getText());
        poidTotalEmballages = poidTotalEmballages + Double.parseDouble(labelEmballage.getText());
        if (lockdown){
            Platform.runLater(() -> {
                labelPain.setText("0.000");
                labelAlimentaire.setText("0.000");
                labelEmballage.setText("0.000");
            });
            DATA_Scene.gestion.ecrire(0.000,0.000,0.000);
        }else{
            DATA_Scene.gestion.ecrire(Double.parseDouble(labelPain.getText()), Double.parseDouble(labelAlimentaire.getText()), Double.parseDouble(labelEmballage.getText()));
        }
        lockdown(false);
        Platform.runLater(() -> {
            labelPain.setText("0.000");
            labelAlimentaire.setText("0.000");
            labelEmballage.setText("0.000");
        });
    }

    public void rfid(Boolean state) throws PhidgetException, InterruptedException, IOException {
        if (state) {
            ecrire();
            Platform.runLater(() -> labelUser.setText("🍕 Bonjour " + prenom + " " + nom));
            resetTimeout();
        } else {
            ecrire();
            openConnexion(2);
        }
    }

    private void openConnexion(int i) throws IOException, PhidgetException, InterruptedException {
        Platform.runLater(() ->
        {
            DATA_Scene.primaryStage.setScene(DATA_Scene.scene1);
            DATA_Scene.primaryStage.setTitle("Fenêtre Connection");
            DATA_Scene.primaryStage.setFullScreen(true);
            DATA_Scene.position = false;
            DATA_Scene.controller.labelService.setText(String.valueOf(poidTotalPain + poidTotalAlimentaire + poidTotalEmballages));
            switch (i) {
                case 1 -> DATA_Scene.controller.labelScann.setText("Bonjour veuillez scanner votre carte pour vous connecter");
                case 2 -> DATA_Scene.controller.labelScann.setText("Erreur carte inconnue");
            }
        });
    }

    public void resetTimeout() {
        timeoutTimeline.stop();
        timeoutTimeline.play();
        flag = true;
    }

    public void renvoiPoid(int index, double poid) {
        if (DATA_Scene.position) {
            if (flag) {
                switch (index) {
                    case 0 -> {
                        poid = poid - poidTotalPain;
                        if (poid < -0.010) {
                            lockdown(true);
                        }
                        labelPain.setText(decimalFormat.format(poid));
                    }
                    case 1 -> {
                        poid = poid - poidTotalEmballages;
                        if (poid < -0.010) {
                            lockdown(true);
                        }
                        labelEmballage.setText(decimalFormat.format(poid));
                    }
                    case 2 -> {
                        poid = poid - poidTotalAlimentaire;
                        if (poid < -0.010) {
                            lockdown(true);
                        }
                            labelAlimentaire.setText(decimalFormat.format(poid));
                    }
                }
                if(!lockdown){
                    resetTimeout();
                }
            }
        }
    }

    public void lockdown(Boolean lockdown) {
        this.lockdown = lockdown;
        if (lockdown){
            Platform.runLater(() -> {
                labelPain.setVisible(false);
                labelAlimentaire.setVisible(false);
                labelEmballage.setVisible(false);
                imagePain.setVisible(true);
                imageAlimentaire.setVisible(true);
                imageEmballage.setVisible(true);
                imageLogo.setImage(new Image("images/Angry.png"));
                labelUser.setTextFill(Color.RED);
                labelUser.setText("Erreur Poid, tentative de fraude");
            });
        }else{
            Platform.runLater(() -> {
                labelPain.setVisible(true);
                labelAlimentaire.setVisible(true);
                labelEmballage.setVisible(true);
                imagePain.setVisible(false);
                imageAlimentaire.setVisible(false);
                imageEmballage.setVisible(false);
                imageLogo.setImage(new Image("images/logo.png"));
                labelUser.setTextFill(Color.BLACK);
                labelUser.setText("");
            });
        }

    }
}
