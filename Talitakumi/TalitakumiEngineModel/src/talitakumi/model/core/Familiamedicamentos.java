/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "familiamedicamentos")
@NamedQueries({
    @NamedQuery(name = "Familiamedicamentos.findAll", query = "SELECT f FROM Familiamedicamentos f"),
    @NamedQuery(name = "Familiamedicamentos.findById", query = "SELECT f FROM Familiamedicamentos f WHERE f.id = :id"),
    @NamedQuery(name = "Familiamedicamentos.findByDescripcion", query = "SELECT f FROM Familiamedicamentos f WHERE f.descripcion = :descripcion")})
public class Familiamedicamentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "familia")
    private Collection<Medicamentos> medicamentosCollection;

    public Familiamedicamentos() {
    }

    public Familiamedicamentos(Integer id) {
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

    public Collection<Medicamentos> getMedicamentosCollection() {
        return medicamentosCollection;
    }

    public void setMedicamentosCollection(Collection<Medicamentos> medicamentosCollection) {
        this.medicamentosCollection = medicamentosCollection;
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
        if (!(object instanceof Familiamedicamentos)) {
            return false;
        }
        Familiamedicamentos other = (Familiamedicamentos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Familiamedicamentos[id=" + id + "]";
    }

}
