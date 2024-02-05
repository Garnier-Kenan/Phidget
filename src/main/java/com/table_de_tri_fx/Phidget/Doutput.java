package com.table_de_tri_fx.Phidget;

import com.phidget22.DigitalOutput;
import com.phidget22.PhidgetException;
public class Doutput{
    private int numero_output;
    private DigitalOutput output;
    public Doutput(int numero_output) throws PhidgetException {
        this.numero_output = numero_output;
        this.output = new DigitalOutput();
        open();
    }
public void open(){
    while (true) {
        try {
            if (!this.output.getIsOpen()) {
                this.output.setChannel(numero_output);
                this.output.open(5000);
                System.out.println("Led " + numero_output + " OK");
                break;
            }
        } catch (PhidgetException e) {
            System.err.println("Sortie" + numero_output + "Erreur ouverture, regarder branchement");
        }
    }
}
    public void close() throws PhidgetException {
        if(output.getIsOpen()){
            try {
                this.output.close();
            } catch (PhidgetException e) {
                System.err.println("Sortie" + numero_output + "Erreur fermeture, sortie déjà fermer");
                throw new RuntimeException(e);
            }
        }

    }
    public DigitalOutput getOutput() {
        return output;
    }
}
