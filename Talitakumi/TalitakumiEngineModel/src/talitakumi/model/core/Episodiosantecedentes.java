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
@Table(name = "episodiosantecedentes")
@NamedQueries({
    @NamedQuery(name = "Episodiosantecedentes.findAll", query = "SELECT e FROM Episodiosantecedentes e"),
    @NamedQuery(name = "Episodiosantecedentes.findByEpisodio", query = "SELECT e FROM Episodiosantecedentes e WHERE e.episodiosantecedentesPK.episodio = :episodio"),
    @NamedQuery(name = "Episodiosantecedentes.findByEnfermedad", query = "SELECT e FROM Episodiosantecedentes e WHERE e.episodiosantecedentesPK.enfermedad = :enfermedad"),
    @NamedQuery(name = "Episodiosantecedentes.findByDetalles", query = "SELECT e FROM Episodiosantecedentes e WHERE e.detalles = :detalles")})
public class Episodiosantecedentes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EpisodiosantecedentesPK episodiosantecedentesPK;
    @Column(name = "detalles")
    private String detalles;
    @JoinColumn(name = "enfermedad", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Enfermedades enfermedades;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Episodios episodios;

    public Episodiosantecedentes() {
    }

    public Episodiosantecedentes(EpisodiosantecedentesPK episodiosantecedentesPK) {
        this.episodiosantecedentesPK = episodiosantecedentesPK;
    }

    public Episodiosantecedentes(int episodio, int enfermedad) {
        this.episodiosantecedentesPK = new EpisodiosantecedentesPK(episodio, enfermedad);
    }

    public EpisodiosantecedentesPK getEpisodiosantecedentesPK() {
        return episodiosantecedentesPK;
    }

    public void setEpisodiosantecedentesPK(EpisodiosantecedentesPK episodiosantecedentesPK) {
        this.episodiosantecedentesPK = episodiosantecedentesPK;
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
        hash += (episodiosantecedentesPK != null ? episodiosantecedentesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodiosantecedentes)) {
            return false;
        }
        Episodiosantecedentes other = (Episodiosantecedentes) object;
        if ((this.episodiosantecedentesPK == null && other.episodiosantecedentesPK != null) || (this.episodiosantecedentesPK != null && !this.episodiosantecedentesPK.equals(other.episodiosantecedentesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodiosantecedentes[episodiosantecedentesPK=" + episodiosantecedentesPK + "]";
    }

}
