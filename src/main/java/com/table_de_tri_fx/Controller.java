package com.table_de_tri_fx;



import com.table_de_tri_fx.Phidget.RecupDonnee;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Label labelAlimentaires;
    public Label labelPain;
    public Label labelEmballages;
    public Label labelScann;
    String prenom = new String("");
    Boolean state = false;
    String nom= new String("");
    RecupDonnee recupDonnee = new RecupDonnee(this);



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recupDonnee.Connexion(state, prenom, nom);
        System.out.println(recupDonnee.state + recupDonnee.prenom + recupDonnee.nom);
    }
}