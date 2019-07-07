/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objetosreporte;

import java.util.Date;

/**
 *
 * @author rupertus
 */
public class PendientesCarnet {

    private String cedula;
    private String apellidos;
    private String nombres;
    private Date   venceVacAtt;
    private Date   fechaPAP;
    private String clinicaPAP;
    private String medicoPAP;
    private Date   fechaMamo;
    private String medicoMAMO;
    private String clinicaMAMO;
    private String cliente;

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the venceVacAtt
     */
    public Date getVenceVacAtt() {
        return venceVacAtt;
    }

    /**
     * @param venceVacAtt the venceVacAtt to set
     */
    public void setVenceVacAtt(Date venceVacAtt) {
        this.venceVacAtt = venceVacAtt;
    }

    /**
     * @return the fechaPAP
     */
    public Date getFechaPAP() {
        return fechaPAP;
    }

    /**
     * @param fechaPAP the fechaPAP to set
     */
    public void setFechaPAP(Date fechaPAP) {
        this.fechaPAP = fechaPAP;
    }

    /**
     * @return the clinicaPAP
     */
    public String getClinicaPAP() {
        return clinicaPAP;
    }

    /**
     * @param clinicaPAP the clinicaPAP to set
     */
    public void setClinicaPAP(String clinicaPAP) {
        this.clinicaPAP = clinicaPAP;
    }

    /**
     * @return the medicoPAP
     */
    public String getMedicoPAP() {
        return medicoPAP;
    }

    /**
     * @param medicoPAP the medicoPAP to set
     */
    public void setMedicoPAP(String medicoPAP) {
        this.medicoPAP = medicoPAP;
    }

    /**
     * @return the fechaMamo
     */
    public Date getFechaMamo() {
        return fechaMamo;
    }

    /**
     * @param fechaMamo the fechaMamo to set
     */
    public void setFechaMamo(Date fechaMamo) {
        this.fechaMamo = fechaMamo;
    }

    /**
     * @return the medicoMAMO
     */
    public String getMedicoMAMO() {
        return medicoMAMO;
    }

    /**
     * @param medicoMAMO the medicoMAMO to set
     */
    public void setMedicoMAMO(String medicoMAMO) {
        this.medicoMAMO = medicoMAMO;
    }

    /**
     * @return the clinicaMAMO
     */
    public String getClinicaMAMO() {
        return clinicaMAMO;
    }

    /**
     * @param clinicaMAMO the clinicaMAMO to set
     */
    public void setClinicaMAMO(String clinicaMAMO) {
        this.clinicaMAMO = clinicaMAMO;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
}
