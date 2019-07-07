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
@Table(name = "consultorios", catalog = "talitakumi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Consultorios.findAll", query = "SELECT c FROM Consultorios c"),
    @NamedQuery(name = "Consultorios.findById", query = "SELECT c FROM Consultorios c WHERE c.id = :id"),
    @NamedQuery(name = "Consultorios.findByDescripcion", query = "SELECT c FROM Consultorios c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Consultorios.findBySucursal", query = "SELECT c FROM Consultorios c WHERE c.sucursal = :sucursal"),
    @NamedQuery(name = "Consultorios.findByHabilitadourgente", query = "SELECT c FROM Consultorios c WHERE c.habilitadourgente = :habilitadourgente")})
public class Consultorios implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "sucursal")
    private char sucursal;
    @Basic(optional = false)
    @Column(name = "habilitadourgente")
    private String habilitadourgente;

    public Consultorios() {
    }

    public Consultorios(Integer id) {
        this.id = id;
    }

    public Consultorios(Integer id, char sucursal, String habilitadourgente) {
        this.id = id;
        this.sucursal = sucursal;
        this.habilitadourgente = habilitadourgente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        String oldDescripcion = this.descripcion;
        this.descripcion = descripcion;
        changeSupport.firePropertyChange("descripcion", oldDescripcion, descripcion);
    }

    public char getSucursal() {
        return sucursal;
    }

    public void setSucursal(char sucursal) {
        char oldSucursal = this.sucursal;
        this.sucursal = sucursal;
        changeSupport.firePropertyChange("sucursal", oldSucursal, sucursal);
    }

    public String getHabilitadourgente() {
        return habilitadourgente;
    }

    public void setHabilitadourgente(String habilitadourgente) {
        String oldHabilitadourgente = this.habilitadourgente;
        this.habilitadourgente = habilitadourgente;
        changeSupport.firePropertyChange("habilitadourgente", oldHabilitadourgente, habilitadourgente);
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
        if (!(object instanceof Consultorios)) {
            return false;
        }
        Consultorios other = (Consultorios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.admin.Consultorios[id=" + id + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
