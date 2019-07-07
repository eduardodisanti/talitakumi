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
@Table(name = "viciossociales")
@NamedQueries({
    @NamedQuery(name = "Viciossociales.findAll", query = "SELECT v FROM Viciossociales v"),
    @NamedQuery(name = "Viciossociales.findById", query = "SELECT v FROM Viciossociales v WHERE v.id = :id"),
    @NamedQuery(name = "Viciossociales.findByDescripcion", query = "SELECT v FROM Viciossociales v WHERE v.descripcion = :descripcion")})
public class Viciossociales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "viciossociales")
    private Collection<Episodiosvicios> episodiosviciosCollection;

    public Viciossociales() {
    }

    public Viciossociales(Integer id) {
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

    public Collection<Episodiosvicios> getEpisodiosviciosCollection() {
        return episodiosviciosCollection;
    }

    public void setEpisodiosviciosCollection(Collection<Episodiosvicios> episodiosviciosCollection) {
        this.episodiosviciosCollection = episodiosviciosCollection;
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
        if (!(object instanceof Viciossociales)) {
            return false;
        }
        Viciossociales other = (Viciossociales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Viciossociales[id=" + id + "]";
    }

}
