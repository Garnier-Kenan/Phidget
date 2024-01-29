package com.table_de_tri_fx;


import com.phidget22.PhidgetException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Label labelAlimentaires;
    public Label labelPain;
    public Label labelEmballages;
    public Label labelScann;
    String prenom = new String("");
    Boolean state = false;
    String nom = new String("");
    private static Gestion gestion;
    private Thread thread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thread = new Thread(()->{
            try {
                gestion = new Gestion(this);
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }
    public static void close(){
        try {
            gestion.close();
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }
}
