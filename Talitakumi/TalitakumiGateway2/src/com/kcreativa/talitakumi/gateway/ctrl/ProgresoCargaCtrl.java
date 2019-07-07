/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kcreativa.talitakumi.gateway.ctrl;

import com.kcreativa.talitakumi.gateway.ui.UIProgresoCarga;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rupertus
 */
public class ProgresoCargaCtrl implements Runnable {

    private Integer maximo, minimo, actual;
    UIProgresoCarga pc;
    private boolean corriendo;

    public ProgresoCargaCtrl(Integer maximo, Integer minimo) {

        pc = new UIProgresoCarga();

        this.maximo = maximo;
        this.minimo = minimo;

        actual=minimo;

        pc.setMinimo(minimo);
        pc.setMaximo(minimo);
        pc.setValorActual(actual);
        corriendo = false;

        pc.setVisible(true);

    }

    public void run() {
        try {

            while(corriendo) {
                pc.setValorActual(actual);
                Thread.sleep(200);
             }
        } catch (InterruptedException ex) {
            Logger.getLogger(ProgresoCargaCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the maximo
     */
    public Integer getMaximo() {
        return maximo;
    }

    /**
     * @param maximo the maximo to set
     */
    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    /**
     * @return the minimo
     */
    public Integer getMinimo() {
        return minimo;
    }

    /**
     * @param minimo the minimo to set
     */
    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    /**
     * @return the actual
     */
    public Integer getActual() {
        return actual;
    }

    /**
     * @param actual the actual to set
     */
    public void setActual(Integer actual) {
        this.actual = actual;
    }

    /**
     * @return the corriendo
     */
    public boolean isCorriendo() {
        return corriendo;
    }

    /**
     * @param corriendo the corriendo to set
     */
    public void setCorriendo(boolean corriendo) {
        this.corriendo = corriendo;
    }

    void cerrarVentana() {
        pc.setVisible(false);
    }

    void addTexto(String string) {
        pc.addTexto(string);
    }

    void addCerrarListener(ActionListener cerrarProgresoListener) {

        pc.addCerrarListener(cerrarProgresoListener);
    }

}
