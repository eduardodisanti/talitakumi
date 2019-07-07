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
public class EpisodiosviciosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "vicio")
    private int vicio;
    @Basic(optional = false)
    @Column(name = "episodio")
    private int episodio;

    public EpisodiosviciosPK() {
    }

    public EpisodiosviciosPK(int vicio, int episodio) {
        this.vicio = vicio;
        this.episodio = episodio;
    }

    public int getVicio() {
        return vicio;
    }

    public void setVicio(int vicio) {
        this.vicio = vicio;
    }

    public int getEpisodio() {
        return episodio;
    }

    public void setEpisodio(int episodio) {
        this.episodio = episodio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) vicio;
        hash += (int) episodio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EpisodiosviciosPK)) {
            return false;
        }
        EpisodiosviciosPK other = (EpisodiosviciosPK) object;
        if (this.vicio != other.vicio) {
            return false;
        }
        if (this.episodio != other.episodio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.EpisodiosviciosPK[vicio=" + vicio + ", episodio=" + episodio + "]";
    }

}
