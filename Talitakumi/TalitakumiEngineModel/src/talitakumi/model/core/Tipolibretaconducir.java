/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "tipolibretaconducir")
@NamedQueries({
    @NamedQuery(name = "Tipolibretaconducir.findAll", query = "SELECT t FROM Tipolibretaconducir t"),
    @NamedQuery(name = "Tipolibretaconducir.findById", query = "SELECT t FROM Tipolibretaconducir t WHERE t.id = :id"),
    @NamedQuery(name = "Tipolibretaconducir.findByDescripcion", query = "SELECT t FROM Tipolibretaconducir t WHERE t.descripcion = :descripcion")})
public class Tipolibretaconducir implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private String id;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;

    public Tipolibretaconducir() {
    }

    public Tipolibretaconducir(String id) {
        this.id = id;
    }

    public Tipolibretaconducir(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipolibretaconducir)) {
            return false;
        }
        Tipolibretaconducir other = (Tipolibretaconducir) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Tipolibretaconducir[id=" + id + "]";
    }

}
