package com.table_de_tri_fx;


import com.phidget22.PhidgetException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.expression.spel.ast.BooleanLiteral;

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
    String nom = new String("");
    private static Gestion gestion;
    private Thread thread;
    private Stage primaryStage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        thread = new Thread(()->{
//            try {
//                gestion = new Gestion(this);
//            } catch (PhidgetException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        thread.start();


    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    private void openNewWindow(Boolean flag) {
        labelScann.setText(""+ flag);

        // Vérifiez le badge ID et ouvrez une nouvelle fenêtre si nécessaire
        if (flag != false) {
            try {
                Stage newStage = new Stage();
                // Chargez le fichier FXML de votre nouvelle fenêtre
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PageConnexion.fxml"));
                newStage.setScene(new Scene(loader.load()));
                primaryStage.close();
                newStage.setTitle("Table Principale");
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
        }
        }
    }







    }



