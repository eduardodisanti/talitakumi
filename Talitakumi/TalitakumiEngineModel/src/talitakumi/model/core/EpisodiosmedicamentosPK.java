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
public class EpisodiosmedicamentosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "medicamento")
    private int medicamento;
    @Basic(optional = false)
    @Column(name = "episodio")
    private int episodio;

    public EpisodiosmedicamentosPK() {
    }

    public EpisodiosmedicamentosPK(int medicamento, int episodio) {
        this.medicamento = medicamento;
        this.episodio = episodio;
    }

    public int getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(int medicamento) {
        this.medicamento = medicamento;
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
        hash += (int) medicamento;
        hash += (int) episodio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EpisodiosmedicamentosPK)) {
            return false;
        }
        EpisodiosmedicamentosPK other = (EpisodiosmedicamentosPK) object;
        if (this.medicamento != other.medicamento) {
            return false;
        }
        if (this.episodio != other.episodio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.EpisodiosmedicamentosPK[medicamento=" + medicamento + ", episodio=" + episodio + "]";
    }

}
