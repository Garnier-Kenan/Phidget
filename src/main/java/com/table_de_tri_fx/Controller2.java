package com.table_de_tri_fx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {
    public Label labelPain;
    public Label labelEmballage;
    public Label labelAlimentaire;
    public Label labelUser;
    public String prenom, nom;
    public Button btngraphics;
    public Label labeltotaux;
    public Label labelhebdo;
    public Label labelheure;
    private LineChart<?,?> lineChart;

    @FXML
    private Double  poidTotalPain = 0.00 , poidTotalAlimentaire = 0.00 , poidTotalEmballages = 0.00,poid0,poid1,poid2;
    private boolean flag = true;

    public Boolean state = false;
    private Timeline timeoutTimeline;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            labelPain.setText("0.00");
            labelAlimentaire.setText("0.00");
            labelEmballage.setText("0.00");

        });
        timeoutTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(30),
                        event -> {
                            try {
                                flag = false;
                                iniLinechart();
                                openConnexion();
                                Platform.runLater(() -> DATA_Scene.controller.labelScann.setText("Bonjour veuillez scanner votre carte pour vous connecter"));
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
                labelUser.setText("Bonjour " + prenom + " " + nom);
            });
        } else {
            try {
                openConnexion();
                Platform.runLater(() -> DATA_Scene.controller.labelScann.setText("Erreur carte inconnue"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void openConnexion() throws IOException {
        DATA_Scene.primaryStage.setScene(DATA_Scene.scene1);
        DATA_Scene.primaryStage.setTitle("Fenêtre Connection");
        DATA_Scene.position = false;
    }

    public void resetTimeout() {
        timeoutTimeline.stop();
        timeoutTimeline.play();
        flag = true;
    }
    public void renvoiPoid(double poid0 , double poid1, double poid2){
        if (flag){
            resetTimeout();
            this.poid0 = poidTotalPain + poid0;
            this.poid1 = poidTotalAlimentaire + poid1;
            this.poid2 = poidTotalEmballages + poid2;
            Platform.runLater(() -> {
                labelPain.setText(String.valueOf(Double.parseDouble(labelPain.getText())+(this.poid0 - poidTotalPain)));
                labelAlimentaire.setText(String.valueOf(Double.parseDouble(labelAlimentaire.getText())+(this.poid1 - poidTotalAlimentaire)));
                labelEmballage.setText(String.valueOf(Double.parseDouble(labelEmballage.getText())+(this.poid2 - poidTotalEmballages)));
                labeltotaux.setText(String.valueOf(Double.parseDouble(labeltotaux.getText())+poidTotalPain+poidTotalEmballages+poidTotalAlimentaire));
            });
        }
        else {
        }
    }
    private void iniLinechart(){
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Monday",poid1));
        series.getData().add(new XYChart.Data("Monday",poid1));
        series.getData().add(new XYChart.Data("Monday",poid1));
        series.getData().add(new XYChart.Data("Monday",poid1));
        series.getData().add(new XYChart.Data("Monday",poid1));
        lineChart.getData().addAll(series);
    }
}
