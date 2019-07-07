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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "rollos")
@NamedQueries({
    @NamedQuery(name = "Rollos.findAll", query = "SELECT r FROM Rollos r"),
    @NamedQuery(name = "Rollos.findById", query = "SELECT r FROM Rollos r WHERE r.id = :id"),
    @NamedQuery(name = "Rollos.findByCaja", query = "SELECT r FROM Rollos r WHERE r.caja = :caja"),
    @NamedQuery(name = "Rollos.findByFechainicio", query = "SELECT r FROM Rollos r WHERE r.fechainicio = :fechainicio")})
public class Rollos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Id
    @Basic(optional = false)
    @Column(name = "caja")
    private Integer caja;
    @Basic(optional = false)
    @Column(name = "fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;

    public Rollos() {
    }

    public Rollos(Integer caja) {
        this.caja = caja;
    }

    public Rollos(Integer caja, int id, Date fechainicio) {
        this.caja = caja;
        this.id = id;
        this.fechainicio = fechainicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCaja() {
        return caja;
    }

    public void setCaja(Integer caja) {
        this.caja = caja;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (caja != null ? caja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rollos)) {
            return false;
        }
        Rollos other = (Rollos) object;
        if ((this.caja == null && other.caja != null) || (this.caja != null && !this.caja.equals(other.caja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Rollos[caja=" + caja + "]";
    }

}
