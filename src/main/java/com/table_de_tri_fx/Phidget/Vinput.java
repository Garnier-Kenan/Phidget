package com.table_de_tri_fx.Phidget;

import com.phidget22.*;
import com.table_de_tri_fx.Gestion;

import java.text.DecimalFormat;

public class Vinput implements AttachListener, DetachListener, VoltageRatioInputVoltageRatioChangeListener {
    private int numero_input;
    private VoltageRatioInput input;
    private Boolean calibrer = false;
    Gestion gestion;
    private DecimalFormat decimalFormat = new DecimalFormat("00.000");
    public Vinput(Gestion gestion, int numero_input) throws PhidgetException {
        this.gestion = gestion;
        this.numero_input = numero_input;
        this.input = new VoltageRatioInput();
        Thread t = new Thread(() -> {
            open();
            try {
                calibre();
            } catch (PhidgetException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.input.addVoltageRatioChangeListener(this);
        });
        t.start();
    }
public void open(){
    while (true) {
        try {
            if (!this.input.getIsOpen()) {
                try {
                    this.input.setChannel(numero_input);
                    this.input.open(5000);
                    System.out.println("Balance " + numero_input + " OK");
                    break;
                } catch (PhidgetException e) {
                    System.err.println("Erreur ouverture balance, regarder branchement");
                }
            }
        } catch (PhidgetException e) {
            throw new RuntimeException(e);
        }
    }
    this.input.addDetachListener(this);
    this.input.addAttachListener(this);
}
    public void close() throws PhidgetException {
        if(input.getIsOpen()){
            try {
                this.input.removeAttachListener(this);
                this.input.removeDetachListener(this);
                this.input.removeVoltageRatioChangeListener(this);
                this.input.close();
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @Override
    public void onAttach(AttachEvent attachEvent) {
        System.out.println("Balance " + numero_input + " attachée");
    }

    @Override
    public void onDetach(DetachEvent detachEvent) {
        System.out.println("Balance " + numero_input + " déttachée");
    }

    @Override
    public void onVoltageRatioChange(VoltageRatioInputVoltageRatioChangeEvent voltageRatioInputVoltageRatioChangeEvent) {
        if (calibrer){
            gestion.poids(numero_input,Double.parseDouble(decimalFormat.format(Math.abs(voltageRatioInputVoltageRatioChangeEvent.getVoltageRatio()- (DATA_Balance.offset[numero_input])) *DATA_Balance.GAIN[numero_input])));
        }else{
            System.err.println("Balance " + numero_input + " erreur calibrage");
        }
    }
    private void calibre() throws PhidgetException, InterruptedException {
        int numSamples = 10;
        System.out.println("Balance " + numero_input + " calibrage en cours ");
        for (int i = 0; i < numSamples; i++) {
            DATA_Balance.offset[numero_input] += input.getVoltageRatio();
            Thread.sleep(input.getDataInterval());
        }
        DATA_Balance.offset[numero_input] /= numSamples;
        System.out.println("Balance " + numero_input + " calibrage OK " + DATA_Balance.offset[numero_input]);
        calibrer = true;
    }
}
