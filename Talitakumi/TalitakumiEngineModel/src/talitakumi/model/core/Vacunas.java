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
@Table(name = "vacunas")
@NamedQueries({
    @NamedQuery(name = "Vacunas.findAll", query = "SELECT v FROM Vacunas v"),
    @NamedQuery(name = "Vacunas.findById", query = "SELECT v FROM Vacunas v WHERE v.id = :id"),
    @NamedQuery(name = "Vacunas.findByDescripcion", query = "SELECT v FROM Vacunas v WHERE v.descripcion = :descripcion"),
    @NamedQuery(name = "Vacunas.findByTagVacuna", query = "SELECT v FROM Vacunas v WHERE v.tagVacuna = :tagVacuna"),
    @NamedQuery(name = "Vacunas.findByPreciobase", query = "SELECT v FROM Vacunas v WHERE v.preciobase = :preciobase")})
public class Vacunas implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vacunas")
    private Collection<Episodiosvacunas> episodiosvacunasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vacuna")
    private Collection<EpisodiosVacunasAdicionales> episodiosVacunasAdicionalesCollection;

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
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTagVacuna() {
        return tagVacuna;
    }

    public void setTagVacuna(String tagVacuna) {
        this.tagVacuna = tagVacuna;
    }

    public float getPreciobase() {
        return preciobase;
    }

    public void setPreciobase(float preciobase) {
        this.preciobase = preciobase;
    }

    public Collection<Episodiosvacunas> getEpisodiosvacunasCollection() {
        return episodiosvacunasCollection;
    }

    public void setEpisodiosvacunasCollection(Collection<Episodiosvacunas> episodiosvacunasCollection) {
        this.episodiosvacunasCollection = episodiosvacunasCollection;
    }

    public Collection<EpisodiosVacunasAdicionales> getEpisodiosVacunasAdicionalesCollection() {
        return episodiosVacunasAdicionalesCollection;
    }

    public void setEpisodiosVacunasAdicionalesCollection(Collection<EpisodiosVacunasAdicionales> episodiosVacunasAdicionalesCollection) {
        this.episodiosVacunasAdicionalesCollection = episodiosVacunasAdicionalesCollection;
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
        return "talitakumi.model.core.Vacunas[id=" + id + "]";
    }

}
