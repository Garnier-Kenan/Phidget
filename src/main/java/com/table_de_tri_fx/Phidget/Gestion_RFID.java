package com.table_de_tri_fx.Phidget;

import com.phidget22.*;
import javafx.application.Platform;

public class Gestion_RFID implements AttachListener, DetachListener, RFIDTagListener, RFIDTagLostListener {
    private RFID lecteur;
    private Doutput dOutput0;
    private Doutput dOutput1;
    public Boolean flag = false;

    public Gestion_RFID() throws PhidgetException {
        this.lecteur = new RFID();
        open();
    }

    private void open() throws PhidgetException {
        while (!this.lecteur.getIsOpen()) {
            try {
                dOutput0 = new Doutput(this,0);
                dOutput1 = new Doutput(this,1);
                lecteur.addAttachListener(this);
                lecteur.addDetachListener(this);
                lecteur.addTagListener(this);
                lecteur.addTagLostListener(this);
                lecteur.open(500);
            } catch (PhidgetException e) {
                System.err.println("Entrer Détachée");
            }
        }

    }

    private void close() {
        try {
            lecteur.removeAttachListener(this);
            lecteur.removeDetachListener(this);
            lecteur.removeTagListener(this);
            lecteur.removeTagLostListener(this);
            lecteur.close();
            flag = false;
        } catch (PhidgetException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void onAttach(AttachEvent attachEvent) {
        System.out.println("RFID Attaché");
        try {
            System.out.println(lecteur.getDeviceName());
            flag = true;
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDetach(DetachEvent detachEvent) {
        System.out.println("RFID Détaché");
        flag = false;
    }

    @Override
    public void onTag(RFIDTagEvent rfidTagEvent) {
        System.out.println(rfidTagEvent.getTag().toString());
        try {
            dOutput0.getOutput().setState(true);
            dOutput1.getOutput().setState(false);
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTagLost(RFIDTagLostEvent rfidTagLostEvent) {
        System.out.println("tag lost");
        try {
            dOutput0.getOutput().setState(false);
            dOutput1.getOutput().setState(true);
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }
}
