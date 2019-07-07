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
@Table(name = "procedencias", catalog = "talitakumi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Procedencias.findAll", query = "SELECT p FROM Procedencias p"),
    @NamedQuery(name = "Procedencias.findById", query = "SELECT p FROM Procedencias p WHERE p.id = :id"),
    @NamedQuery(name = "Procedencias.findByDescripcion", query = "SELECT p FROM Procedencias p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Procedencias.findByHabilitado", query = "SELECT p FROM Procedencias p WHERE p.habilitado = :habilitado")})
public class Procedencias implements Serializable {
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
    @Column(name = "habilitado")
    private char habilitado;

    public Procedencias() {
    }

    public Procedencias(Integer id) {
        this.id = id;
    }

    public Procedencias(Integer id, char habilitado) {
        this.id = id;
        this.habilitado = habilitado;
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

    public char getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(char habilitado) {
        char oldHabilitado = this.habilitado;
        this.habilitado = habilitado;
        changeSupport.firePropertyChange("habilitado", oldHabilitado, habilitado);
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
        if (!(object instanceof Procedencias)) {
            return false;
        }
        Procedencias other = (Procedencias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.admin.Procedencias[id=" + id + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
