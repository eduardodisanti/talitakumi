/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "episodiosvacunas")
@NamedQueries({
    @NamedQuery(name = "Episodiosvacunas.findAll", query = "SELECT e FROM Episodiosvacunas e"),
    @NamedQuery(name = "Episodiosvacunas.findByEpisodio", query = "SELECT e FROM Episodiosvacunas e WHERE e.episodiosvacunasPK.episodio = :episodio"),
    @NamedQuery(name = "Episodiosvacunas.findByVacuna", query = "SELECT e FROM Episodiosvacunas e WHERE e.episodiosvacunasPK.vacuna = :vacuna"),
    @NamedQuery(name = "Episodiosvacunas.findByVence", query = "SELECT e FROM Episodiosvacunas e WHERE e.vence = :vence")})
public class Episodiosvacunas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EpisodiosvacunasPK episodiosvacunasPK;
    @Column(name = "vence")
    @Temporal(TemporalType.DATE)
    private Date vence;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Episodios episodios;
    @JoinColumn(name = "vacuna", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Vacunas vacunas;

    public Episodiosvacunas() {
    }

    public Episodiosvacunas(EpisodiosvacunasPK episodiosvacunasPK) {
        this.episodiosvacunasPK = episodiosvacunasPK;
    }

    public Episodiosvacunas(int episodio, int vacuna) {
        this.episodiosvacunasPK = new EpisodiosvacunasPK(episodio, vacuna);
    }

    public EpisodiosvacunasPK getEpisodiosvacunasPK() {
        return episodiosvacunasPK;
    }

    public void setEpisodiosvacunasPK(EpisodiosvacunasPK episodiosvacunasPK) {
        this.episodiosvacunasPK = episodiosvacunasPK;
    }

    public Date getVence() {
        return vence;
    }

    public void setVence(Date vence) {
        this.vence = vence;
    }

    public Episodios getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Episodios episodios) {
        this.episodios = episodios;
    }

    public Vacunas getVacunas() {
        return vacunas;
    }

    public void setVacunas(Vacunas vacunas) {
        this.vacunas = vacunas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (episodiosvacunasPK != null ? episodiosvacunasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodiosvacunas)) {
            return false;
        }
        Episodiosvacunas other = (Episodiosvacunas) object;
        if ((this.episodiosvacunasPK == null && other.episodiosvacunasPK != null) || (this.episodiosvacunasPK != null && !this.episodiosvacunasPK.equals(other.episodiosvacunasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodiosvacunas[episodiosvacunasPK=" + episodiosvacunasPK + "]";
    }

}
