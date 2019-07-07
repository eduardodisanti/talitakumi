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
@Table(name = "tipodeactividad")
@NamedQueries({
    @NamedQuery(name = "Tipodeactividad.findAll", query = "SELECT t FROM Tipodeactividad t"),
    @NamedQuery(name = "Tipodeactividad.findById", query = "SELECT t FROM Tipodeactividad t WHERE t.id = :id"),
    @NamedQuery(name = "Tipodeactividad.findByDescripcion", query = "SELECT t FROM Tipodeactividad t WHERE t.descripcion = :descripcion")})
public class Tipodeactividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoactividad")
    private Collection<Actividades> actividadesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoactividad")
    private Collection<Examenesactividad> examenesactividadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicio")
    private Collection<Moldeagenda> moldeagendaCollection;

    public Tipodeactividad() {
    }

    public Tipodeactividad(Integer id) {
        this.id = id;
    }

    public Tipodeactividad(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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

    public Collection<Actividades> getActividadesCollection() {
        return actividadesCollection;
    }

    public void setActividadesCollection(Collection<Actividades> actividadesCollection) {
        this.actividadesCollection = actividadesCollection;
    }

    public Collection<Examenesactividad> getExamenesactividadCollection() {
        return examenesactividadCollection;
    }

    public void setExamenesactividadCollection(Collection<Examenesactividad> examenesactividadCollection) {
        this.examenesactividadCollection = examenesactividadCollection;
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
        if (!(object instanceof Tipodeactividad)) {
            return false;
        }
        Tipodeactividad other = (Tipodeactividad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Tipodeactividad[id=" + id + "]";
    }

    /**
     * @return the moldeagendaCollection
     */
    public Collection<Moldeagenda> getMoldeagendaCollection() {
        return moldeagendaCollection;
    }

    /**
     * @param moldeagendaCollection the moldeagendaCollection to set
     */
    public void setMoldeagendaCollection(Collection<Moldeagenda> moldeagendaCollection) {
        this.moldeagendaCollection = moldeagendaCollection;
    }

}
