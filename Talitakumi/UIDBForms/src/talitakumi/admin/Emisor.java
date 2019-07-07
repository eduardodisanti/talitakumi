/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.admin;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author eduardodisanti
 */
@Entity
@Table(name = "emisor", catalog = "talitakumi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Emisor.findAll", query = "SELECT e FROM Emisor e"),
    @NamedQuery(name = "Emisor.findById", query = "SELECT e FROM Emisor e WHERE e.id = :id"),
    @NamedQuery(name = "Emisor.findByRut", query = "SELECT e FROM Emisor e WHERE e.rut = :rut"),
    @NamedQuery(name = "Emisor.findByNombre", query = "SELECT e FROM Emisor e WHERE e.nombre = :nombre")})
public class Emisor implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Rut")
    private float rut;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    public Emisor() {
    }

    public Emisor(Integer id) {
        this.id = id;
    }

    public Emisor(Integer id, float rut, String nombre) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public float getRut() {
        return rut;
    }

    public void setRut(float rut) {
        float oldRut = this.rut;
        this.rut = rut;
        changeSupport.firePropertyChange("rut", oldRut, rut);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        String oldNombre = this.nombre;
        this.nombre = nombre;
        changeSupport.firePropertyChange("nombre", oldNombre, nombre);
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
        if (!(object instanceof Emisor)) {
            return false;
        }
        Emisor other = (Emisor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.admin.Emisor[id=" + id + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
