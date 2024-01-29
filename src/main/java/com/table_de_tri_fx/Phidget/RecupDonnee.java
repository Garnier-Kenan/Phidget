package com.table_de_tri_fx.Phidget;

import com.table_de_tri_fx.Application;
import com.table_de_tri_fx.BDD.BDD;
import com.table_de_tri_fx.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class RecupDonnee {
    public boolean state ;
    public String nom;
    public String prenom;
    Controller ctrl ;
    public RecupDonnee(Controller ctrl) {
        this.ctrl = ctrl;
    }

    public void  Connexion(boolean state  , String nom , String prenom){
        this.state = state;
        this.nom = nom;
        this.prenom  = prenom;
        ctrl.labelPain.setText("test");
        ctrl.labelEmballages.setText("");
        ctrl.labelAlimentaires.setText("");




    }
    public void timeout(){

    }

}
