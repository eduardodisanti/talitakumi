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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "auditoriacaja")
@NamedQueries({
    @NamedQuery(name = "Auditoriacaja.findAll", query = "SELECT a FROM Auditoriacaja a"),
    @NamedQuery(name = "Auditoriacaja.findByFecha", query = "SELECT a FROM Auditoriacaja a WHERE a.auditoriacajaPK.fecha = :fecha"),
    @NamedQuery(name = "Auditoriacaja.findByHora", query = "SELECT a FROM Auditoriacaja a WHERE a.auditoriacajaPK.hora = :hora"),
    @NamedQuery(name = "Auditoriacaja.findByTipo", query = "SELECT a FROM Auditoriacaja a WHERE a.tipo = :tipo"),
    @NamedQuery(name = "Auditoriacaja.findByFuncionario", query = "SELECT a FROM Auditoriacaja a WHERE a.funcionario = :funcionario")})
public class Auditoriacaja implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuditoriacajaPK auditoriacajaPK;
    @Basic(optional = false)
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @Column(name = "funcionario")
    private int funcionario;

    public Auditoriacaja() {
    }

    public Auditoriacaja(AuditoriacajaPK auditoriacajaPK) {
        this.auditoriacajaPK = auditoriacajaPK;
    }

    public Auditoriacaja(AuditoriacajaPK auditoriacajaPK, int tipo, int funcionario) {
        this.auditoriacajaPK = auditoriacajaPK;
        this.tipo = tipo;
        this.funcionario = funcionario;
    }

    public Auditoriacaja(Date fecha, Date hora) {
        this.auditoriacajaPK = new AuditoriacajaPK(fecha, hora);
    }

    public AuditoriacajaPK getAuditoriacajaPK() {
        return auditoriacajaPK;
    }

    public void setAuditoriacajaPK(AuditoriacajaPK auditoriacajaPK) {
        this.auditoriacajaPK = auditoriacajaPK;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(int funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (auditoriacajaPK != null ? auditoriacajaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auditoriacaja)) {
            return false;
        }
        Auditoriacaja other = (Auditoriacaja) object;
        if ((this.auditoriacajaPK == null && other.auditoriacajaPK != null) || (this.auditoriacajaPK != null && !this.auditoriacajaPK.equals(other.auditoriacajaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Auditoriacaja[auditoriacajaPK=" + auditoriacajaPK + "]";
    }

}
