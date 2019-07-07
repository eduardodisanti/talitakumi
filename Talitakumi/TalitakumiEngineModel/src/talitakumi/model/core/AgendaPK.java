/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rupertus
 */
@Embeddable
public class AgendaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "consultorio")
    private String consultorio;
    @Basic(optional = false)
    @Column(name = "turno")
    private int turno;
    @Basic(optional = false)
    @Column(name = "hora")
    private String hora;

    public AgendaPK() {
    }

    public AgendaPK(Date fecha, String consultorio, int turno, String hora) {
        this.fecha = fecha;
        this.consultorio = consultorio;
        this.turno = turno;
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (consultorio != null ? consultorio.hashCode() : 0);
        hash += (int) turno;
        hash += (hora != null ? hora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgendaPK)) {
            return false;
        }
        AgendaPK other = (AgendaPK) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if ((this.consultorio == null && other.consultorio != null) || (this.consultorio != null && !this.consultorio.equals(other.consultorio))) {
            return false;
        }
        if (this.turno != other.turno) {
            return false;
        }
        if ((this.hora == null && other.hora != null) || (this.hora != null && !this.hora.equals(other.hora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.AgendaPK[fecha=" + fecha + ", consultorio=" + consultorio + ", turno=" + turno + ", hora=" + hora + "]";
    }

}
