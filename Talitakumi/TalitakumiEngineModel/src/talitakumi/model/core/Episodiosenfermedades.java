/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "episodiosenfermedades")
@NamedQueries({
    @NamedQuery(name = "Episodiosenfermedades.findAll", query = "SELECT e FROM Episodiosenfermedades e"),
    @NamedQuery(name = "Episodiosenfermedades.findByEnfermedad", query = "SELECT e FROM Episodiosenfermedades e WHERE e.episodiosenfermedadesPK.enfermedad = :enfermedad"),
    @NamedQuery(name = "Episodiosenfermedades.findByEpisodio", query = "SELECT e FROM Episodiosenfermedades e WHERE e.episodiosenfermedadesPK.episodio = :episodio"),
    @NamedQuery(name = "Episodiosenfermedades.findByDetalles", query = "SELECT e FROM Episodiosenfermedades e WHERE e.detalles = :detalles")})
public class Episodiosenfermedades implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EpisodiosenfermedadesPK episodiosenfermedadesPK;
    @Column(name = "detalles")
    private String detalles;
    @JoinColumn(name = "enfermedad", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Enfermedades enfermedades;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Episodios episodios;

    public Episodiosenfermedades() {
    }

    public Episodiosenfermedades(EpisodiosenfermedadesPK episodiosenfermedadesPK) {
        this.episodiosenfermedadesPK = episodiosenfermedadesPK;
    }

    public Episodiosenfermedades(int enfermedad, int episodio) {
        this.episodiosenfermedadesPK = new EpisodiosenfermedadesPK(enfermedad, episodio);
    }

    public EpisodiosenfermedadesPK getEpisodiosenfermedadesPK() {
        return episodiosenfermedadesPK;
    }

    public void setEpisodiosenfermedadesPK(EpisodiosenfermedadesPK episodiosenfermedadesPK) {
        this.episodiosenfermedadesPK = episodiosenfermedadesPK;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Enfermedades getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(Enfermedades enfermedades) {
        this.enfermedades = enfermedades;
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
        hash += (episodiosenfermedadesPK != null ? episodiosenfermedadesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodiosenfermedades)) {
            return false;
        }
        Episodiosenfermedades other = (Episodiosenfermedades) object;
        if ((this.episodiosenfermedadesPK == null && other.episodiosenfermedadesPK != null) || (this.episodiosenfermedadesPK != null && !this.episodiosenfermedadesPK.equals(other.episodiosenfermedadesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodiosenfermedades[episodiosenfermedadesPK=" + episodiosenfermedadesPK + "]";
    }

}
