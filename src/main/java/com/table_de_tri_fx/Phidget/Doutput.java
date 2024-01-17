package com.table_de_tri_fx.Phidget;

import com.phidget22.*;
public class Doutput implements AttachListener, DetachListener{
    private Gestion_RFID gestionRef;
    private int numero_output;
    private DigitalOutput output;
    private boolean statedoutput;
    public Doutput(Gestion_RFID gestionRef, int numero_output) throws PhidgetException {
        this.gestionRef = gestionRef;
        this.numero_output = numero_output;
        this.output = new DigitalOutput();
        open();

    }
public void open(){
    try {
        this.output.setChannel(numero_output);
        this.output.addDetachListener(this);
        this.output.addAttachListener(this);

        this.output.open(500);
    } catch (PhidgetException e) {
        throw new RuntimeException(e);
    }
}
    public void close() {
        try {
            this.output.removeAttachListener(this);
            this.output.removeDetachListener(this);
            this.output.close();
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onAttach(AttachEvent attachEvent) {
        System.out.println("Sortie " + numero_output + " attachée");
        try {
            System.out.println(output.getDeviceName());
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDetach(DetachEvent detachEvent) {
        System.out.println("Sortie " + numero_output + " déttachée");
    }

    public DigitalOutput getOutput() {
        return output;
    }
}
