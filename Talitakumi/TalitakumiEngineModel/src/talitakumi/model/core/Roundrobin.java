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
@Table(name = "roundrobin")
@NamedQueries({
    @NamedQuery(name = "Roundrobin.findAll", query = "SELECT r FROM Roundrobin r"),
    @NamedQuery(name = "Roundrobin.findByActividad", query = "SELECT r FROM Roundrobin r WHERE r.actividad = :actividad"),
    @NamedQuery(name = "Roundrobin.findByUltimo", query = "SELECT r FROM Roundrobin r WHERE r.ultimo = :ultimo")})
public class Roundrobin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "actividad")
    private Integer actividad;
    @Basic(optional = false)
    @Column(name = "ultimo")
    private int ultimo;

    public Roundrobin() {
    }

    public Roundrobin(Integer actividad) {
        this.actividad = actividad;
    }

    public Roundrobin(Integer actividad, int ultimo) {
        this.actividad = actividad;
        this.ultimo = ultimo;
    }

    public Integer getActividad() {
        return actividad;
    }

    public void setActividad(Integer actividad) {
        this.actividad = actividad;
    }

    public int getUltimo() {
        return ultimo;
    }

    public void setUltimo(int ultimo) {
        this.ultimo = ultimo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actividad != null ? actividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roundrobin)) {
            return false;
        }
        Roundrobin other = (Roundrobin) object;
        if ((this.actividad == null && other.actividad != null) || (this.actividad != null && !this.actividad.equals(other.actividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Roundrobin[actividad=" + actividad + "]";
    }

}
