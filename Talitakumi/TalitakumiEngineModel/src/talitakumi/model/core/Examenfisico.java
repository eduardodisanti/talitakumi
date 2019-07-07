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
@Table(name = "examenfisico")
@NamedQueries({
    @NamedQuery(name = "Examenfisico.findAll", query = "SELECT e FROM Examenfisico e"),
    @NamedQuery(name = "Examenfisico.findById", query = "SELECT e FROM Examenfisico e WHERE e.id = :id"),
    @NamedQuery(name = "Examenfisico.findByDescripcion", query = "SELECT e FROM Examenfisico e WHERE e.descripcion = :descripcion")})
public class Examenfisico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examenfisico")
    private Collection<Episodioexamenfisico> episodioexamenfisicoCollection;

    public Examenfisico() {
    }

    public Examenfisico(Integer id) {
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

    public Collection<Episodioexamenfisico> getEpisodioexamenfisicoCollection() {
        return episodioexamenfisicoCollection;
    }

    public void setEpisodioexamenfisicoCollection(Collection<Episodioexamenfisico> episodioexamenfisicoCollection) {
        this.episodioexamenfisicoCollection = episodioexamenfisicoCollection;
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
        if (!(object instanceof Examenfisico)) {
            return false;
        }
        Examenfisico other = (Examenfisico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Examenfisico[id=" + id + "]";
    }

}
