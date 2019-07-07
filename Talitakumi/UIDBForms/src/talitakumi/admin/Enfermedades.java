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
@Table(name = "enfermedades", catalog = "talitakumi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Enfermedades.findAll", query = "SELECT e FROM Enfermedades e"),
    @NamedQuery(name = "Enfermedades.findById", query = "SELECT e FROM Enfermedades e WHERE e.id = :id"),
    @NamedQuery(name = "Enfermedades.findByFamilia", query = "SELECT e FROM Enfermedades e WHERE e.familia = :familia"),
    @NamedQuery(name = "Enfermedades.findByDescripcion", query = "SELECT e FROM Enfermedades e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Enfermedades.findByTagEnfermedad", query = "SELECT e FROM Enfermedades e WHERE e.tagEnfermedad = :tagEnfermedad")})
public class Enfermedades implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "familia")
    private int familia;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "tag_enfermedad")
    private String tagEnfermedad;

    public Enfermedades() {
    }

    public Enfermedades(Integer id) {
        this.id = id;
    }

    public Enfermedades(Integer id, int familia, String descripcion, String tagEnfermedad) {
        this.id = id;
        this.familia = familia;
        this.descripcion = descripcion;
        this.tagEnfermedad = tagEnfermedad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public int getFamilia() {
        return familia;
    }

    public void setFamilia(int familia) {
        int oldFamilia = this.familia;
        this.familia = familia;
        changeSupport.firePropertyChange("familia", oldFamilia, familia);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        String oldDescripcion = this.descripcion;
        this.descripcion = descripcion;
        changeSupport.firePropertyChange("descripcion", oldDescripcion, descripcion);
    }

    public String getTagEnfermedad() {
        return tagEnfermedad;
    }

    public void setTagEnfermedad(String tagEnfermedad) {
        String oldTagEnfermedad = this.tagEnfermedad;
        this.tagEnfermedad = tagEnfermedad;
        changeSupport.firePropertyChange("tagEnfermedad", oldTagEnfermedad, tagEnfermedad);
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
        if (!(object instanceof Enfermedades)) {
            return false;
        }
        Enfermedades other = (Enfermedades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.admin.Enfermedades[id=" + id + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
