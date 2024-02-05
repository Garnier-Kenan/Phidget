package com.table_de_tri_fx;

import com.phidget22.PhidgetException;
import com.table_de_tri_fx.BDD.Gestion_BDD;
import com.table_de_tri_fx.Phidget.Gestion_RFID;
import com.table_de_tri_fx.Phidget.Vinput;
import javafx.application.Platform;

public class Gestion {
    private Gestion_RFID gestion_rfid;
    private Gestion_BDD gestion_bdd;
    private Vinput vInput0, vInput1, vInput2;
    private String oldtag = "", trame = "2";
    private String poid0, poid1, poid2;
    private int id_user;
    private Controller controller;

    public Gestion(Controller controller) throws PhidgetException {

        this.controller = controller;

        gestion_bdd = new Gestion_BDD();

        gestion_rfid = new Gestion_RFID(this);

//        vInput0 = new Vinput(this, 0);
//
//        vInput1 = new Vinput(this, 1);
//
//        vInput2 = new Vinput(this, 2);

    }

    public void afficheNom(String tag) {
        if (!tag.equals(oldtag)) {
            trame = gestion_bdd.verifCarte(tag);
            oldtag = tag;

            switch (trame.charAt(0)) {
                case '1':
                    String[] tab = trame.split("/");
                    id_user = Integer.valueOf(tab[3]);
                    controller.nom = tab[2];
                    controller.prenom = tab[1];
                    controller.state = true;
                    controller.rfid();
                    break;
                case '2':
                    controller.state = false;
                    controller.rfid();
                    break;
            }
        }
    }
    public void ecrire(){
        Boolean reponsse = gestion_bdd.dechet(id_user, poid0, poid1, poid2);
        if (reponsse) {
            System.out.println("BDD c'est bon");
        } else {
            System.err.println("BDD C'est pas bon");
        }
    }

    public void poids(int i, String poid) {
        switch (i) {
            case 0 -> poid0 = poid;
            case 1 -> poid1 = poid;
            case 2 -> poid2 = poid;
        }
        Platform.runLater(() ->{
            controller.labelPain.setText(poid0);
            controller.labelAlimentaires.setText(poid1);
            controller.labelEmballages.setText(poid2);
        });
    }

    public void close() throws PhidgetException {
        vInput0.close();
        vInput1.close();
        vInput2.close();
        gestion_rfid.close();
    }
}
