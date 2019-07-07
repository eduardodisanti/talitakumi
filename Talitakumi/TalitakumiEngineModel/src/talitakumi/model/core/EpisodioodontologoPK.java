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
public class EpisodioodontologoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "episodio")
    private int episodio;
    @Basic(optional = false)
    @Column(name = "pieza")
    private int pieza;
    @Basic(optional = false)
    @Column(name = "enfermedad")
    private int enfermedad;

    public EpisodioodontologoPK() {
    }

    public EpisodioodontologoPK(int episodio, int pieza, int enfermedad) {
        this.episodio = episodio;
        this.pieza = pieza;
        this.enfermedad = enfermedad;
    }

    public int getEpisodio() {
        return episodio;
    }

    public void setEpisodio(int episodio) {
        this.episodio = episodio;
    }

    public int getPieza() {
        return pieza;
    }

    public void setPieza(int pieza) {
        this.pieza = pieza;
    }

    public int getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(int enfermedad) {
        this.enfermedad = enfermedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) episodio;
        hash += (int) pieza;
        hash += (int) enfermedad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EpisodioodontologoPK)) {
            return false;
        }
        EpisodioodontologoPK other = (EpisodioodontologoPK) object;
        if (this.episodio != other.episodio) {
            return false;
        }
        if (this.pieza != other.pieza) {
            return false;
        }
        if (this.enfermedad != other.enfermedad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.EpisodioodontologoPK[episodio=" + episodio + ", pieza=" + pieza + ", enfermedad=" + enfermedad + "]";
    }

}
