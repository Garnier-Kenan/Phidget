package com.table_de_tri_fx.Phidget;

import com.phidget22.*;
import com.table_de_tri_fx.DATA_Scene;
import com.table_de_tri_fx.Gestion;
import javafx.application.Platform;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Vinput implements AttachListener, DetachListener, VoltageRatioInputVoltageRatioChangeListener {
    private int numero_input;
    private VoltageRatioInput input;
    private Boolean calibrer = false;
    private Gestion gestion;
    private Double poid = 0.00;
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
            DecimalFormat decimalFormat = new DecimalFormat("00.000", DecimalFormatSymbols.getInstance(Locale.US));
            double voltage = Math.abs(voltageRatioInputVoltageRatioChangeEvent.getVoltageRatio());
            double offset = Math.abs(DATA_Balance.offset[numero_input]);
            double gain = DATA_Balance.GAIN[numero_input];
            double poid = (voltage - offset) * gain;
            String poids = decimalFormat.format(Math.abs(poid));
            if (comparaison_tolérance(this.poid, poid)) {
                this.poid = poid;
                Platform.runLater(() -> DATA_Scene.controller2.renvoiPoid(numero_input,poids));
                System.out.println("différence");
            }
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
    public boolean comparaison_tolérance (double oldpoid, double p) {
        double difference = Math.abs(p - oldpoid);
        double tolerance = 0.002;
        if(difference <= tolerance){
            return false;
        }
        return true;
    }
}
