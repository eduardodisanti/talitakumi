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
public class EpisodiosenfermedadesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "enfermedad")
    private int enfermedad;
    @Basic(optional = false)
    @Column(name = "episodio")
    private int episodio;

    public EpisodiosenfermedadesPK() {
    }

    public EpisodiosenfermedadesPK(int enfermedad, int episodio) {
        this.enfermedad = enfermedad;
        this.episodio = episodio;
    }

    public int getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(int enfermedad) {
        this.enfermedad = enfermedad;
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
        hash += (int) enfermedad;
        hash += (int) episodio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EpisodiosenfermedadesPK)) {
            return false;
        }
        EpisodiosenfermedadesPK other = (EpisodiosenfermedadesPK) object;
        if (this.enfermedad != other.enfermedad) {
            return false;
        }
        if (this.episodio != other.episodio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.EpisodiosenfermedadesPK[enfermedad=" + enfermedad + ", episodio=" + episodio + "]";
    }

}
