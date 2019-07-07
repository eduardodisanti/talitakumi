/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talitakumi.uipojos.laboratorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author eduardodisanti
 */
public class UIEstudioHecho implements Serializable {
    
    private int id;
    private int idestudio;
    private int idepisodio;
    private String estudio;
    private String supervisor;
    private String paciente;
    private String servicio;
    private Date fecha;
    private String observaciones;
    private boolean mostrar;
    private List<UIAnalisisHecho> analisishechos;

    public UIEstudioHecho() {
    
        analisishechos = new ArrayList<UIAnalisisHecho>();
    }
    
    public void addAnalisis(UIAnalisisHecho a) {
        
        getAnalisishechos().add(a);
    }
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
     * @return the idestudio
     */
    public int getIdestudio() {
        return idestudio;
    }

    /**
     * @param idestudio the idestudio to set
     */
    public void setIdestudio(int idestudio) {
        this.idestudio = idestudio;
    }

    /**
     * @return the idepisodio
     */
    public int getIdepisodio() {
        return idepisodio;
    }

    /**
     * @param idepisodio the idepisodio to set
     */
    public void setIdepisodio(int idepisodio) {
        this.idepisodio = idepisodio;
    }

    /**
     * @return the estudio
     */
    public String getEstudio() {
        return estudio;
    }

    /**
     * @param estudio the estudio to set
     */
    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    /**
     * @return the supervisor
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * @param supervisor the supervisor to set
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * @return the paciente
     */
    public String getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the analisishechos
     */
    public List<UIAnalisisHecho> getAnalisishechos() {
        return analisishechos;
    }

    /**
     * @param analisishechos the analisishechos to set
     */
    public void setAnalisishechos(List<UIAnalisisHecho> analisishechos) {
        this.analisishechos = analisishechos;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the servicio
     */
    public String getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public boolean getMostrar() {
        
        return(mostrar);
    }
    
    public void setMostrar(boolean m) {
        
        mostrar = m;
    }
}
