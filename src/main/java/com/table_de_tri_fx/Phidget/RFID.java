package com.table_de_tri_fx.Phidget;

import com.phidget22.*;
import com.table_de_tri_fx.Ihm.DATA_Scene;

import java.io.IOException;

public class RFID implements AttachListener, DetachListener, RFIDTagListener, RFIDTagLostListener {
    private com.phidget22.RFID lecteur;
    private Doutput dOutput0;
    private Doutput dOutput1;

    public RFID() throws PhidgetException {
        this.lecteur = new com.phidget22.RFID();
        Thread t = new Thread(() -> open());
        t.start();
    }

    private void open() {
        while (true) {
            try {
                if (!this.lecteur.getIsOpen()) {
                    lecteur.open(5000);
                    System.out.println("RFID OK");
                    DATA_Scene.controller_popUP.popup_rfid = false;
                    break;
                }
            } catch (PhidgetException e) {
                if (!DATA_Scene.controller_popUP.popup_rfid){
                    DATA_Scene.controller_popUP.popup_rfid = true;
                    DATA_Scene.controller_popUP.popUP();
                }
            }
        }
        DATA_Scene.controller_popUP.popup_rfid = false;
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
        DATA_Scene.controller_popUP.popup_rfid = false;
    }

    @Override
    public void onDetach(DetachEvent detachEvent) {
        System.err.println("Erreur déttachement lecteur RFID, regarder branchement");
        DATA_Scene.controller_popUP.popup_rfid = true;
        DATA_Scene.controller_popUP.popUP();
    }

    @Override
    public void onTag(RFIDTagEvent rfidTagEvent) {
        System.out.println(rfidTagEvent.getTag().toString());
        try {
            DATA_Scene.gestion.afficheNom(rfidTagEvent.getTag().toString());
        } catch (InterruptedException | PhidgetException | IOException e) {
            throw new RuntimeException(e);
        }
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
