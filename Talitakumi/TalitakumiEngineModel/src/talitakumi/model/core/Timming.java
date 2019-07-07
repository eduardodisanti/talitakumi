/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "timming")
@NamedQueries({
    @NamedQuery(name = "Timming.findAll", query = "SELECT t FROM Timming t"),
    @NamedQuery(name = "Timming.findByEpisodio", query = "SELECT t FROM Timming t WHERE t.episodio = :episodio"),
    @NamedQuery(name = "Timming.findByFechaingreso", query = "SELECT t FROM Timming t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Timming.findByHoraingreso", query = "SELECT t FROM Timming t WHERE t.horaingreso = :horaingreso"),
    @NamedQuery(name = "Timming.findByFechaatencion", query = "SELECT t FROM Timming t WHERE t.fechaatencion = :fechaatencion"),
    @NamedQuery(name = "Timming.findByHoraatencion", query = "SELECT t FROM Timming t WHERE t.horaatencion = :horaatencion")})
public class Timming implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @Basic(optional = false)
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Basic(optional = false)
    @Column(name = "horaingreso")
    @Temporal(TemporalType.TIME)
    private Date horaingreso;
    @Column(name = "fechaatencion")
    @Temporal(TemporalType.DATE)
    private Date fechaatencion;
    @Column(name = "horaatencion")
    @Temporal(TemporalType.DATE)
    private Date horaatencion;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;

    public Timming() {
    }

    public Timming(Integer episodio) {
        this.episodio = episodio;
    }

    public Timming(Integer episodio, Date fechaingreso, Date horaingreso) {
        this.episodio = episodio;
        this.fechaingreso = fechaingreso;
        this.horaingreso = horaingreso;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public Date getHoraingreso() {
        return horaingreso;
    }

    public void setHoraingreso(Date horaingreso) {
        this.horaingreso = horaingreso;
    }

    public Date getFechaatencion() {
        return fechaatencion;
    }

    public void setFechaatencion(Date fechaatencion) {
        this.fechaatencion = fechaatencion;
    }

    public Date getHoraatencion() {
        return horaatencion;
    }

    public void setHoraatencion(Date horaatencion) {
        this.horaatencion = horaatencion;
    }

    public Episodios getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Episodios episodios) {
        this.episodios = episodios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (episodio != null ? episodio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Timming)) {
            return false;
        }
        Timming other = (Timming) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Timming[episodio=" + episodio + "]";
    }

}
