/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rupertus
 */
@Embeddable
public class EpisodiosvacunasPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "episodio")
    private int episodio;
    @Basic(optional = false)
    @Column(name = "vacuna")
    private int vacuna;

    public EpisodiosvacunasPK() {
    }

    public EpisodiosvacunasPK(int episodio, int vacuna) {
        this.episodio = episodio;
        this.vacuna = vacuna;
    }

    public int getEpisodio() {
        return episodio;
    }

    public void setEpisodio(int episodio) {
        this.episodio = episodio;
    }

    public int getVacuna() {
        return vacuna;
    }

    public void setVacuna(int vacuna) {
        this.vacuna = vacuna;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) episodio;
        hash += (int) vacuna;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EpisodiosvacunasPK)) {
            return false;
        }
        EpisodiosvacunasPK other = (EpisodiosvacunasPK) object;
        if (this.episodio != other.episodio) {
            return false;
        }
        if (this.vacuna != other.vacuna) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.EpisodiosvacunasPK[episodio=" + episodio + ", vacuna=" + vacuna + "]";
    }

}
