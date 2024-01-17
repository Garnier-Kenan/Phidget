package com.table_de_tri_fx.Phidget;

import com.phidget22.*;

public class Vinput implements AttachListener, DetachListener, VoltageRatioInputVoltageRatioChangeListener {
    private int numero_input;
    private VoltageRatioInput input;
    double stateintput;
    Gestion_Balance gestionBalance;
    public Vinput(Gestion_Balance gestionBalance, int numero_input) throws PhidgetException {
        this.gestionBalance = gestionBalance;
        this.numero_input = numero_input;
        this.input = new VoltageRatioInput();
        open();
    }
public void open(){
    try {
        this.input.setChannel(numero_input);
        this.input.addDetachListener(this);
        this.input.addAttachListener(this);
        this.input.addVoltageRatioChangeListener(this);
        this.input.open(500);
    } catch (PhidgetException e) {
        System.err.println("Entrer Détachée");
        throw new RuntimeException(e);
    }
}
    public void close() {
        try {
            this.input.removeAttachListener(this);
            this.input.removeDetachListener(this);
            this.input.close();
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onAttach(AttachEvent attachEvent) {
        System.out.println("Entrée " + numero_input + " attachée");
        try {
            System.out.println(input.getDeviceName());
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDetach(DetachEvent detachEvent) {
        System.out.println("Entrée " + numero_input + " déttachée");
    }

    @Override
    public void onVoltageRatioChange(VoltageRatioInputVoltageRatioChangeEvent voltageRatioInputVoltageRatioChangeEvent) {
        gestionBalance.poids(numero_input,voltageRatioInputVoltageRatioChangeEvent.getVoltageRatio());
    }
}
