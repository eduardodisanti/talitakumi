/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "agenda")
@NamedQueries({
    @NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a"),
    @NamedQuery(name = "Agenda.findByFecha", query = "SELECT a FROM Agenda a WHERE a.agendaPK.fecha = :fecha"),
    @NamedQuery(name = "Agenda.findByConsultorio", query = "SELECT a FROM Agenda a WHERE a.agendaPK.consultorio = :consultorio"),
    @NamedQuery(name = "Agenda.findByTurno", query = "SELECT a FROM Agenda a WHERE a.agendaPK.turno = :turno"),
    @NamedQuery(name = "Agenda.findByHora", query = "SELECT a FROM Agenda a WHERE a.agendaPK.hora = :hora"),
    @NamedQuery(name = "Agenda.findByPaciente", query = "SELECT a FROM Agenda a WHERE a.paciente = :paciente"),
    @NamedQuery(name = "Agenda.findByActividad", query = "SELECT a FROM Agenda a WHERE a.actividad = :actividad"),
    @NamedQuery(name = "Agenda.findByVino", query = "SELECT a FROM Agenda a WHERE a.vino = :vino"),
    @NamedQuery(name = "Agenda.findByHabilitado", query = "SELECT a FROM Agenda a WHERE a.habilitado = :habilitado"),
    @NamedQuery(name = "Agenda.findBySesid", query = "SELECT a FROM Agenda a WHERE a.sesid = :sesid")})
public class Agenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgendaPK agendaPK;
    @Basic(optional = false)
    @Column(name = "paciente")
    private int paciente;
    @Basic(optional = false)
    @Column(name = "actividad")
    private int actividad;
    @Basic(optional = false)
    @Column(name = "vino")
    private char vino;
    @Basic(optional = false)
    @Column(name = "habilitado")
    private char habilitado;
    @Basic(optional = false)
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "sesid")
    private long sesid;
    @JoinColumn(name = "episodio", referencedColumnName = "id")
    @ManyToOne
    private Episodios episodio;
    @JoinColumn(name = "medico", referencedColumnName = "codigo")
    @ManyToOne
    private Medicos medico;

    @JoinColumn(name = "cliente", referencedColumnName = "id")
    @ManyToOne
    private Clientes cliente;

    public Agenda() {
    }

    public Agenda(AgendaPK agendaPK) {
        this.agendaPK = agendaPK;
    }

    public Agenda(AgendaPK agendaPK, int paciente, int actividad, char vino, char habilitado, long sesid) {
        this.agendaPK = agendaPK;
        this.paciente = paciente;
        this.actividad = actividad;
        this.vino = vino;
        this.habilitado = habilitado;
        this.sesid = sesid;
    }

    public Agenda(Date fecha, String consultorio, int turno, String hora) {
        this.agendaPK = new AgendaPK(fecha, consultorio, turno, hora);
    }

    public AgendaPK getAgendaPK() {
        return agendaPK;
    }

    public void setAgendaPK(AgendaPK agendaPK) {
        this.agendaPK = agendaPK;
    }

    public int getPaciente() {
        return paciente;
    }

    public void setPaciente(int paciente) {
        this.paciente = paciente;
    }

    public int getActividad() {
        return actividad;
    }

    public void setActividad(int actividad) {
        this.actividad = actividad;
    }

    public char getVino() {
        return vino;
    }

    public void setVino(char vino) {
        this.vino = vino;
    }

    public char getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(char habilitado) {
        this.habilitado = habilitado;
    }

    public long getSesid() {
        return sesid;
    }

    public void setSesid(long sesid) {
        this.sesid = sesid;
    }

    public Episodios getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Episodios episodio) {
        this.episodio = episodio;
    }

    public Medicos getMedico() {
        return medico;
    }

    public void setMedico(Medicos medico) {
        this.medico = medico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agendaPK != null ? agendaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agenda)) {
            return false;
        }
        Agenda other = (Agenda) object;
        if ((this.agendaPK == null && other.agendaPK != null) || (this.agendaPK != null && !this.agendaPK.equals(other.agendaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Agenda[agendaPK=" + agendaPK + "]";
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

    /**
     * @return the cliente
     */
    public Clientes getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

}
