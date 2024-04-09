package com.table_de_tri_fx.Ihm.Controllers;

import com.table_de_tri_fx.Ihm.DATA_Scene;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    public Boolean popup_rfid = false;
    public Boolean popup_balance = false;
    public Boolean popup_bdd = false;
    private Boolean popup_state = false;
    private Stage popupStage;
    private Thread pingBDD;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pingBDD = new Thread(()->{
            while (true){
                DATA_Scene.gestion_bdd.pingBDD();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void init_page() {
        Image1.setImage(new Image("images/logo.png"));
        Image2.setImage(new Image("images/image.png"));
    }

    public void popUP() {
        Platform.runLater(() -> {
            if (popup_bdd){
                pingBDD.start();
            }else{
                if (pingBDD.isAlive()){
                    pingBDD.stop();
                }
            }
            if (popup_bdd || popup_rfid || popup_balance){
                if (popup_bdd && popup_rfid && popup_balance) {
                    Label.setText("Errreur Base de données.\nVerifiez le serveur Client.\nErrreur Balance + Errreur Lecteur de carte.\nVerifiez le Branchement.");
                } else if (popup_bdd && popup_rfid){
                    Label.setText("Errreur Base de données.\nVerifiez le serveur Client.\nErrreur Lecteur de carte.\nVerifiez le Branchement.");
                } else if(popup_bdd && popup_balance){
                    Label.setText("Errreur Base de données.\nVerifiez le serveur Client.\nErrreur Balance\nVerifiez le Branchement.");
                } else if (popup_rfid && popup_balance) {
                    Label.setText("Errreur Balance + Errreur Lecteur de carte.\nVerifiez le Branchement.");
                } else if (popup_balance) {
                    Label.setText("Errreur Balance. Verifiez le Branchement.");
                } else if (popup_rfid) {
                    Label.setText("Errreur Lecteur de carte. Verifiez le Branchement.");
                } else if (popup_bdd) {
                    Label.setText("Errreur Base de données. Verifiez le serveur Client.");
                }
                if (!popup_state) {
                    popup_state = true;
                    init_page();
                    stage();
                }
            }
        });
    }

    public void stage() {
        Platform.runLater(() -> {
            popupStage = new Stage();
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setResizable(false);
            popupStage.setTitle("🛑 Alerte Message");
            popupStage.setScene(DATA_Scene.scene_PopUP);
            popupStage.setOnCloseRequest(event -> {
                popupStage.close();
                popup_state = false;
                popup_balance = false;
                popup_rfid = false;
                popup_bdd = false;
            });
            popupStage.showAndWait();
        });
    }

    public void popup_close() {
        Platform.runLater(() -> {
            if (popup_bdd){
                pingBDD.start();
            }else{
                if (pingBDD.isAlive()){
                    pingBDD.stop();
                }
            }
            if (popup_bdd || popup_rfid || popup_balance){
                if (popup_bdd && popup_rfid && popup_balance) {
                    Label.setText("Errreur Base de données.\nVerifiez le serveur Client.\nErrreur Balance + Errreur Lecteur de carte.\nVerifiez le Branchement.");
                } else if (popup_bdd && popup_rfid){
                    Label.setText("Errreur Base de données.\nVerifiez le serveur Client.\nErrreur Lecteur de carte.\nVerifiez le Branchement.");
                } else if(popup_bdd && popup_balance){
                    Label.setText("Errreur Base de données.\nVerifiez le serveur Client.\nErrreur Balance\nVerifiez le Branchement.");
                } else if (popup_rfid && popup_balance) {
                    Label.setText("Errreur Balance + Errreur Lecteur de carte.\nVerifiez le Branchement.");
                } else if (popup_balance) {
                    Label.setText("Errreur Balance. Verifiez le Branchement.");
                } else if (popup_rfid) {
                    Label.setText("Errreur Lecteur de carte. Verifiez le Branchement.");
                } else if (popup_bdd) {
                    Label.setText("Errreur Base de données. Verifiez le serveur Client.");
                }
            }
            else{
                if (popupStage != null) {
                    if (popupStage.isShowing()) {
                        popupStage.close();
                        popup_state = false;
                    }
                }
            }
        });
    }
}
