package com.table_de_tri_fx.Phidget;

import com.phidget22.PhidgetException;
import javafx.application.Platform;

public class Gestion_Balance {
    private Vinput vInput0;
    private Vinput vInput1;
    private Vinput vInput2;
    private Thread balance_1;
    private Thread balance_2;
    private Thread balance_3;
    public double poid1 = 0;
    public double poid2 = 0;
    public double poid3 = 0;

    public Gestion_Balance() {
        open();
    }

    public void open() {

        balance_1 = new Thread(() -> {
            try {
                vInput0 = new Vinput(this, 0);
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        });
        balance_1.start();
        balance_2 = new Thread(() -> {
            try {
                vInput1 = new Vinput(this, 1);
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        });
        balance_2.start();
        balance_3 = new Thread(() -> {
            try {
                vInput2 = new Vinput(this, 2);
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        });
        balance_3.start();

    }

    public void close() {
        vInput0.close();
        vInput1.close();
        vInput2.close();
        balance_1.stop();
        balance_2.stop();
        balance_3.stop();
    }

    public void poids(int i, double voltage) {
        double voltage1 = 0;
        double voltage2 = 0;
        double voltage3 = 0;
        switch (i) {
            case 0 -> voltage1 = voltage;
            case 1 -> voltage2 = voltage;
            case 2 -> voltage3 = voltage;
        }
        convertir(voltage1, voltage2, voltage3);
    }

    private void convertir(double voltage1, double voltage2, double voltage3) {
        poid1 = voltage1 / 1;
        poid2 = voltage2 / 1;
        poid3 = voltage3 / 1;
        //A faire convertire voltage en poids avec l'intervalle voltes de mesure
    }
}
