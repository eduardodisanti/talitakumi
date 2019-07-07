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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "medicamentos")
@NamedQueries({
    @NamedQuery(name = "Medicamentos.findAll", query = "SELECT m FROM Medicamentos m"),
    @NamedQuery(name = "Medicamentos.findById", query = "SELECT m FROM Medicamentos m WHERE m.id = :id"),
    @NamedQuery(name = "Medicamentos.findByDescripcion", query = "SELECT m FROM Medicamentos m WHERE m.descripcion = :descripcion")})
public class Medicamentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicamentos")
    private Collection<Episodiosmedicamentos> episodiosmedicamentosCollection;
    @JoinColumn(name = "familia", referencedColumnName = "id")
    @ManyToOne
    private Familiamedicamentos familia;

    public Medicamentos() {
    }

    public Medicamentos(Integer id) {
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

    public Collection<Episodiosmedicamentos> getEpisodiosmedicamentosCollection() {
        return episodiosmedicamentosCollection;
    }

    public void setEpisodiosmedicamentosCollection(Collection<Episodiosmedicamentos> episodiosmedicamentosCollection) {
        this.episodiosmedicamentosCollection = episodiosmedicamentosCollection;
    }

    public Familiamedicamentos getFamilia() {
        return familia;
    }

    public void setFamilia(Familiamedicamentos familia) {
        this.familia = familia;
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
        if (!(object instanceof Medicamentos)) {
            return false;
        }
        Medicamentos other = (Medicamentos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Medicamentos[id=" + id + "]";
    }

}
