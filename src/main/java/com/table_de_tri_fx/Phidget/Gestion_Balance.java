package com.table_de_tri_fx.Phidget;

import com.phidget22.PhidgetException;

public class Gestion_Balance {
    private Vinput vInput0;
    private Vinput vInput1;
    private Vinput vInput2;
    public double state0 = 0;
    public double state1 = 0;
    public double state2 = 0;

    public Gestion_Balance() {
        open();
    }

    public void open() {

        Thread v1 = new Thread(() -> {
            try {
                vInput0 = new Vinput(this, 0);
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        });
        v1.start();
        Thread v2 = new Thread(() -> {
            try {
                vInput1 = new Vinput(this, 1);
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        });
        v2.start();
        Thread v3 = new Thread(() -> {
            try {
                vInput2 = new Vinput(this, 2);
            } catch (PhidgetException e) {
                throw new RuntimeException(e);
            }
        });
        v3.start();

    }

    public void close() {
        vInput0.close();
        vInput1.close();
        vInput2.close();
    }

    public void poids(int i, double voltage) {
        switch (i) {
            case 0 -> state0 = voltage;
            case 1 -> state1 = voltage;
            case 2 -> state2 = voltage;
        }
    }
}
