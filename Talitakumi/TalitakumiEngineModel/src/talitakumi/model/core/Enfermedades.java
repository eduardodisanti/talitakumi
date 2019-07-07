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
@Table(name = "enfermedades")
@NamedQueries({
    @NamedQuery(name = "Enfermedades.findAll", query = "SELECT e FROM Enfermedades e"),
    @NamedQuery(name = "Enfermedades.findById", query = "SELECT e FROM Enfermedades e WHERE e.id = :id"),
    @NamedQuery(name = "Enfermedades.findByDescripcion", query = "SELECT e FROM Enfermedades e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Enfermedades.findByTagEnfermedad", query = "SELECT e FROM Enfermedades e WHERE e.tagEnfermedad = :tagEnfermedad")})
public class Enfermedades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "tag_enfermedad")
    private String tagEnfermedad;
    @JoinColumn(name = "familia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Familiaenfermedades familia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enfermedades")
    private Collection<Episodioodontologo> episodioodontologoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enfermedades")
    private Collection<Episodiosantecedentes> episodiosantecedentesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enfermedades")
    private Collection<Episodiosenfermedades> episodiosenfermedadesCollection;

    public Enfermedades() {
    }

    public Enfermedades(Integer id) {
        this.id = id;
    }

    public Enfermedades(Integer id, String descripcion, String tagEnfermedad) {
        this.id = id;
        this.descripcion = descripcion;
        this.tagEnfermedad = tagEnfermedad;
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

    public String getTagEnfermedad() {
        return tagEnfermedad;
    }

    public void setTagEnfermedad(String tagEnfermedad) {
        this.tagEnfermedad = tagEnfermedad;
    }

    public Familiaenfermedades getFamilia() {
        return familia;
    }

    public void setFamilia(Familiaenfermedades familia) {
        this.familia = familia;
    }

    public Collection<Episodioodontologo> getEpisodioodontologoCollection() {
        return episodioodontologoCollection;
    }

    public void setEpisodioodontologoCollection(Collection<Episodioodontologo> episodioodontologoCollection) {
        this.episodioodontologoCollection = episodioodontologoCollection;
    }

    public Collection<Episodiosantecedentes> getEpisodiosantecedentesCollection() {
        return episodiosantecedentesCollection;
    }

    public void setEpisodiosantecedentesCollection(Collection<Episodiosantecedentes> episodiosantecedentesCollection) {
        this.episodiosantecedentesCollection = episodiosantecedentesCollection;
    }

    public Collection<Episodiosenfermedades> getEpisodiosenfermedadesCollection() {
        return episodiosenfermedadesCollection;
    }

    public void setEpisodiosenfermedadesCollection(Collection<Episodiosenfermedades> episodiosenfermedadesCollection) {
        this.episodiosenfermedadesCollection = episodiosenfermedadesCollection;
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
        return "talitakumi.model.core.Enfermedades[id=" + id + "]";
    }

}
