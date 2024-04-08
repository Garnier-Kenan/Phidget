package com.table_de_tri_fx.Ihm.Controllers;


import com.table_de_tri_fx.Ihm.DATA_Scene;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller_Connexion implements Initializable {
    public Label labelScann;
    public Label labelSemaine;
    public Label labelService;
    private DecimalFormat decimalFormat = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.US));
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelService.setText("0.000");
    }

    public void bilan_semaine(){
        labelSemaine.setText(decimalFormat.format(DATA_Scene.gestion.initePageP()));
    }
    public void rfid(Boolean state) {
        if (state) {
            openPrincipale();
        } else {
            Platform.runLater(() -> labelScann.setText("🛑 Erreur carte inconnue"));
        }
    }

    public void openPrincipale() {
        Platform.runLater(() ->
        {
            DATA_Scene.primaryStage.setScene(DATA_Scene.scene_Principale);
            DATA_Scene.primaryStage.setTitle("Fenêtre Principale");
            DATA_Scene.primaryStage.setFullScreen(true);
            DATA_Scene.position = true;
            DATA_Scene.controller_Principale.labelUser.setText("🍕 Bonjour " + DATA_Scene.controller_Principale.prenom + " " + DATA_Scene.controller_Principale.nom);
            DATA_Scene.controller_Principale.resetTimeout();
        });
    }
}



