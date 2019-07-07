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
@Table(name = "vacunas", catalog = "talitakumi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Vacunas.findAll", query = "SELECT v FROM Vacunas v"),
    @NamedQuery(name = "Vacunas.findById", query = "SELECT v FROM Vacunas v WHERE v.id = :id"),
    @NamedQuery(name = "Vacunas.findByDescripcion", query = "SELECT v FROM Vacunas v WHERE v.descripcion = :descripcion"),
    @NamedQuery(name = "Vacunas.findByTagVacuna", query = "SELECT v FROM Vacunas v WHERE v.tagVacuna = :tagVacuna"),
    @NamedQuery(name = "Vacunas.findByPreciobase", query = "SELECT v FROM Vacunas v WHERE v.preciobase = :preciobase")})
public class Vacunas implements Serializable {
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
    @Column(name = "tag_vacuna")
    private String tagVacuna;
    @Basic(optional = false)
    @Column(name = "preciobase")
    private float preciobase;

    public Vacunas() {
    }

    public Vacunas(Integer id) {
        this.id = id;
    }

    public Vacunas(Integer id, String tagVacuna, float preciobase) {
        this.id = id;
        this.tagVacuna = tagVacuna;
        this.preciobase = preciobase;
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

    public String getTagVacuna() {
        return tagVacuna;
    }

    public void setTagVacuna(String tagVacuna) {
        String oldTagVacuna = this.tagVacuna;
        this.tagVacuna = tagVacuna;
        changeSupport.firePropertyChange("tagVacuna", oldTagVacuna, tagVacuna);
    }

    public float getPreciobase() {
        return preciobase;
    }

    public void setPreciobase(float preciobase) {
        float oldPreciobase = this.preciobase;
        this.preciobase = preciobase;
        changeSupport.firePropertyChange("preciobase", oldPreciobase, preciobase);
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
        if (!(object instanceof Vacunas)) {
            return false;
        }
        Vacunas other = (Vacunas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.admin.Vacunas[id=" + id + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
