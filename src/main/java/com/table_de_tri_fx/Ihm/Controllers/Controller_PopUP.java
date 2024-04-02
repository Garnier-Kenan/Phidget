package com.table_de_tri_fx.Ihm.Controllers;

import com.table_de_tri_fx.BDD.Gestion_BDD;
import com.table_de_tri_fx.Ihm.DATA_Scene;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_PopUP implements Initializable {

    public ImageView Image1;
    public ImageView Image2;
    public Label Label;
    public TextField Text;
    public Button Button;
    public Boolean popup_rfid = false;
    public Boolean popup_balance = false;
    public Boolean popup_bdd = false;
    private Boolean popup_state = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init_page() {
        Image1.setImage(new Image("images/logo.png"));
        Image2.setImage(new Image("images/image.png"));
        Button.setOnAction(event -> System.out.println(""));
        Button.setVisible(false);
        Text.setVisible(false);
    }

    public void popUP() {
        Platform.runLater(() -> {
            if (popup_rfid && popup_balance) {
                Label.setText("Errreur Balance + RFID Regardez Branchement");
                Button.setVisible(false);
                Text.setVisible(false);
            } else if (popup_balance) {
                Label.setText("Errreur Balance Regardez Branchement");
                Button.setVisible(false);
                Text.setVisible(false);
            } else if (popup_rfid) {
                Label.setText("Errreur RFID Regardez Branchement");
                Button.setVisible(false);
                Text.setVisible(false);
            } else if (popup_bdd) {
                Label.setText("Errreur BDD Regardez serveur Client");
                Button.setVisible(true);
                Text.setVisible(true);
            } else {
                Label.setText("Errreur Inconue");
            }
            if (!popup_state) {
                popup_state = true;
                init_page();
                stage();
            }
        });
    }public void stage() {
        Platform.runLater(() -> {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setResizable(false);
            popupStage.setTitle("🛑 Alerte Message");
            popupStage.setScene(DATA_Scene.scene_PopUP);
            Button.setOnAction(event -> {
                Text.getText();
            });
            popupStage.setOnCloseRequest(event -> {
                popupStage.close();
                popup_state = false;
                popup_balance = false;
                popup_rfid = false;
            });
            popupStage.showAndWait();
        });
    }
}
