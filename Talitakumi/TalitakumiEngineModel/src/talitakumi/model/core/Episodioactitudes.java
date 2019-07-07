/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "episodioactitudes")
@NamedQueries({
    @NamedQuery(name = "Episodioactitudes.findAll", query = "SELECT e FROM Episodioactitudes e"),
    @NamedQuery(name = "Episodioactitudes.findByEpisodio", query = "SELECT e FROM Episodioactitudes e WHERE e.episodio = :episodio")})
public class Episodioactitudes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;

    public Episodioactitudes() {
    }

    public Episodioactitudes(Integer episodio) {
        this.episodio = episodio;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Episodios getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Episodios episodios) {
        this.episodios = episodios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (episodio != null ? episodio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodioactitudes)) {
            return false;
        }
        Episodioactitudes other = (Episodioactitudes) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodioactitudes[episodio=" + episodio + "]";
    }

}
