package com.table_de_tri_fx.Phidget;

import com.phidget22.*;
import com.table_de_tri_fx.Gestion;

public class Gestion_RFID implements AttachListener, DetachListener, RFIDTagListener, RFIDTagLostListener {
    private RFID lecteur;
    private Doutput dOutput0;
    private Doutput dOutput1;
    private Gestion gestion;

    public Gestion_RFID(Gestion gestion) throws PhidgetException {
        this.gestion = gestion;
        this.lecteur = new RFID();
        Thread t = new Thread(() -> open());
        t.start();
    }

    private void open() {
        while (true) {
            try {
                if (!this.lecteur.getIsOpen()) {
                    lecteur.open(5000);
                    System.out.println("RFID OK");
                    break;
                }
            } catch (PhidgetException e) {
                System.err.println("Erreur ouverture RFID, regarder branchement");
            }
        }
        try {
            dOutput0 = new Doutput(1);
            dOutput1 = new Doutput(2);
            dOutput0.getOutput().setState(true);
            dOutput1.getOutput().setState(false);
        } catch (PhidgetException e) {
            System.err.println("Erreur ouverture LED, regarder branchement");
            throw new RuntimeException();
        }

        lecteur.addAttachListener(this);
        lecteur.addDetachListener(this);
        lecteur.addTagListener(this);
        lecteur.addTagLostListener(this);
    }

    public void close() throws PhidgetException {
        if (this.lecteur.getIsOpen()) {
            lecteur.removeAttachListener(this);
            lecteur.removeDetachListener(this);
            lecteur.removeTagListener(this);
            lecteur.removeTagLostListener(this);
            lecteur.close();
            dOutput0.close();
            dOutput1.close();
        }
    }

    @Override
    public void onAttach(AttachEvent attachEvent) {
        System.out.println("RFID attaché");
    }

    @Override
    public void onDetach(DetachEvent detachEvent) {
        System.err.println("Erreur déttachement lecteur RFID, regarder branchement");
    }

    @Override
    public void onTag(RFIDTagEvent rfidTagEvent) {
        System.out.println(rfidTagEvent.getTag().toString());
        gestion.afficheNom(rfidTagEvent.getTag().toString());
        try {
            dOutput0.getOutput().setState(false);
            dOutput1.getOutput().setState(true);
        } catch (PhidgetException e) {
            System.err.println("Erreur lecture TAG RFID, verifier lecteur");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTagLost(RFIDTagLostEvent rfidTagLostEvent) {
        try {
            dOutput0.getOutput().setState(true);
            dOutput1.getOutput().setState(false);
        } catch (PhidgetException e) {
            System.err.println("Erreur perte TAG RFID, verifier lecteur");
            throw new RuntimeException(e);
        }
    }
}
