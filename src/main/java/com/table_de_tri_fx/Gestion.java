package com.table_de_tri_fx;

import com.phidget22.PhidgetException;
import com.table_de_tri_fx.BDD.Gestion_BDD;
import com.table_de_tri_fx.Phidget.DATA_Balance;
import com.table_de_tri_fx.Phidget.Gestion_RFID;
import com.table_de_tri_fx.Phidget.Vinput;
import javafx.application.Platform;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Gestion {
    private Gestion_RFID gestion_rfid;
    private Gestion_BDD gestion_bdd;
    private Vinput vInput0, vInput1, vInput2;
    private String oldtag = "", trame = "2";
    private Double poid0 = 0.00, poid1 = 0.00, poid2 = 0.00;
    private int id_user, id_table = DATA_Balance.id_table;
    Thread b0, b1, b2;

    public Gestion() throws PhidgetException {

        gestion_bdd = new Gestion_BDD();

        gestion_rfid = new Gestion_RFID(this);
    b0 = new Thread(()->{
        try {
            vInput0 = new Vinput(this, 0);
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    });
    b0.start();
        b1 = new Thread(()->{
            try {
                vInput1 = new Vinput(this, 1);
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        });
        b1.start();
        b2 = new Thread(()->{
            try {
                vInput2 = new Vinput(this, 2);
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        });
        b2.start();
    }

    public void afficheNom(String tag) throws PhidgetException, InterruptedException, IOException {
        if (!tag.equals(oldtag)) {
            trame = gestion_bdd.verifCarte(tag);
            oldtag = tag;

            switch (trame.charAt(0)) {
                case '1':
                    String[] tab = trame.split("/");
                    id_user = Integer.valueOf(tab[3]);
                    DATA_Scene.controller2.nom = tab[2];
                    DATA_Scene.controller2.prenom = tab[1];
                    if (!DATA_Scene.position) {
                        DATA_Scene.controller.rfid(true);
                    } else {
                        DATA_Scene.controller2.rfid(true);
                    }

                    break;
                case '2':
                    if (DATA_Scene.position) {
                        DATA_Scene.controller2.rfid(false);
                    } else {
                        DATA_Scene.controller.rfid(false);
                    }
                    break;
            }
        } else if (tag.equals(oldtag)) {
            Platform.runLater(() -> {
                DATA_Scene.controller.labelScann.setText("Carte déjà passer");
            });
        }
    }

    public void ecrire(Double poid0, Double poid1, Double poid2) throws PhidgetException, InterruptedException {
        gestion_bdd.dechet(id_user, id_table, poid0,poid1,poid2);
    }

    public void close() throws PhidgetException {
        vInput0.close();
        vInput1.close();
        vInput2.close();
        gestion_rfid.close();
    }
}
