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
public class EpisodioexamenfisicoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "episodio")
    private int episodio;
    @Basic(optional = false)
    @Column(name = "examen")
    private int examen;

    public EpisodioexamenfisicoPK() {
    }

    public EpisodioexamenfisicoPK(int episodio, int examen) {
        this.episodio = episodio;
        this.examen = examen;
    }

    public int getEpisodio() {
        return episodio;
    }

    public void setEpisodio(int episodio) {
        this.episodio = episodio;
    }

    public int getExamen() {
        return examen;
    }

    public void setExamen(int examen) {
        this.examen = examen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) episodio;
        hash += (int) examen;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EpisodioexamenfisicoPK)) {
            return false;
        }
        EpisodioexamenfisicoPK other = (EpisodioexamenfisicoPK) object;
        if (this.episodio != other.episodio) {
            return false;
        }
        if (this.examen != other.examen) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.EpisodioexamenfisicoPK[episodio=" + episodio + ", examen=" + examen + "]";
    }

}
