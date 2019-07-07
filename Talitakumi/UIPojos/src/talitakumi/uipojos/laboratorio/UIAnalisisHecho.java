/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talitakumi.uipojos.laboratorio;

import java.io.Serializable;

/**
 *
 * @author eduardodisanti
 */
public class UIAnalisisHecho implements Serializable {
    
    private int id;
    private int idestudiohecho;
    private String analisis;
    private int idanalisis;
    private String valorhallado;
    private String validado;
    private String referencia;
    private String color;
    private boolean validadoOK;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idestudiohecho
     */
    public int getIdestudiohecho() {
        return idestudiohecho;
    }

    /**
     * @param idestudiohecho the idestudiohecho to set
     */
    public void setIdestudiohecho(int idestudiohecho) {
        this.idestudiohecho = idestudiohecho;
    }

    /**
     * @return the analisis
     */
    public String getColor() {
        return color;
    }

    /**
     * @param analisis the analisis to set
     */
    public void setColor(String color) {
        this.color = color;
    }
    
    /**
     * @return the analisis
     */
    public String getAnalisis() {
        return analisis;
    }

    /**
     * @param analisis the analisis to set
     */
    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

    /**
     * @return the idanalisis
     */
    public int getIdanalisis() {
        return idanalisis;
    }

    /**
     * @param idanalisis the idanalisis to set
     */
    public void setIdanalisis(int idanalisis) {
        this.idanalisis = idanalisis;
    }

    /**
     * @return the valorhallado
     */
    public String getValorhallado() {
        return valorhallado;
    }

    /**
     * @param valorhallado the valorhallado to set
     */
    public void setValorhallado(String valorhallado) {
        this.valorhallado = valorhallado;
    }

    /**
     * @return the validado
     */
    public String getValidado() {
        return validado;
    }

    /**
     * @param validado the validado to set
     */
    public void setValidado(String validado) {
        this.validado = validado;
    }

    /**
     * @return the validadoOK
     */
    public boolean isValidadoOK() {
        return validadoOK;
    }

    /**
     * @param validadoOK the validadoOK to set
     */
    public void setValidadoOK(boolean validadoOK) {
        this.validadoOK = validadoOK;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
