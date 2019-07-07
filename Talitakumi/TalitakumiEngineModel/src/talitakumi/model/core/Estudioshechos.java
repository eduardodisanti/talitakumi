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
@Table(name = "estudioshechos")
@NamedQueries({
    @NamedQuery(name = "Estudioshechos.findAll", query = "SELECT e FROM Estudioshechos e"),
    @NamedQuery(name = "Estudioshechos.findById", query = "SELECT e FROM Estudioshechos e WHERE e.id = :id")})
public class Estudioshechos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "supervisor")
    private Integer supervisor;
    @JoinColumn(name = "episodio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Episodios episodio;
    @JoinColumn(name = "estudio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estudios estudio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiohecho")
    private Collection<Analisishechos> analisishechosCollection;

    public Estudioshechos() {
    }

    public Estudioshechos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Episodios getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Episodios episodio) {
        this.episodio = episodio;
    }

    public Estudios getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudios estudio) {
        this.estudio = estudio;
    }

    public Collection<Analisishechos> getAnalisishechosCollection() {
        return analisishechosCollection;
    }

    public void setAnalisishechosCollection(Collection<Analisishechos> analisishechosCollection) {
        this.analisishechosCollection = analisishechosCollection;
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
        if (!(object instanceof Estudioshechos)) {
            return false;
        }
        Estudioshechos other = (Estudioshechos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Estudioshechos[id=" + id + "]";
    }

    /**
     * @return the supervisor
     */
    public Integer getSupervisor() {
        return supervisor;
    }

    /**
     * @param supervisor the supervisor to set
     */
    public void setSupervisor(Integer supervisor) {
        this.supervisor = supervisor;
    }

}
