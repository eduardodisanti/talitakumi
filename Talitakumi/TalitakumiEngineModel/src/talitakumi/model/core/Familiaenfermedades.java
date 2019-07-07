/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "familiaenfermedades")
@NamedQueries({
    @NamedQuery(name = "Familiaenfermedades.findAll", query = "SELECT f FROM Familiaenfermedades f"),
    @NamedQuery(name = "Familiaenfermedades.findById", query = "SELECT f FROM Familiaenfermedades f WHERE f.id = :id"),
    @NamedQuery(name = "Familiaenfermedades.findByDescripcion", query = "SELECT f FROM Familiaenfermedades f WHERE f.descripcion = :descripcion")})
public class Familiaenfermedades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "familia")
    private Collection<Enfermedades> enfermedadesCollection;

    public Familiaenfermedades() {
    }

    public Familiaenfermedades(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<Enfermedades> getEnfermedadesCollection() {
        return enfermedadesCollection;
    }

    public void setEnfermedadesCollection(Collection<Enfermedades> enfermedadesCollection) {
        this.enfermedadesCollection = enfermedadesCollection;
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
        if (!(object instanceof Familiaenfermedades)) {
            return false;
        }
        Familiaenfermedades other = (Familiaenfermedades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Familiaenfermedades[id=" + id + "]";
    }

}
