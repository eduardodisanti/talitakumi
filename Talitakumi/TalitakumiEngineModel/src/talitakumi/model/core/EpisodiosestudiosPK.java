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
public class EpisodiosestudiosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "episodio")
    private int episodio;
    @Basic(optional = false)
    @Column(name = "estudio")
    private int estudio;

    public EpisodiosestudiosPK() {
    }

    public EpisodiosestudiosPK(int episodio, int estudio) {
        this.episodio = episodio;
        this.estudio = estudio;
    }

    public int getEpisodio() {
        return episodio;
    }

    public void setEpisodio(int episodio) {
        this.episodio = episodio;
    }

    public int getEstudio() {
        return estudio;
    }

    public void setEstudio(int estudio) {
        this.estudio = estudio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) episodio;
        hash += (int) estudio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EpisodiosestudiosPK)) {
            return false;
        }
        EpisodiosestudiosPK other = (EpisodiosestudiosPK) object;
        if (this.episodio != other.episodio) {
            return false;
        }
        if (this.estudio != other.estudio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.EpisodiosestudiosPK[episodio=" + episodio + ", estudio=" + estudio + "]";
    }

}
