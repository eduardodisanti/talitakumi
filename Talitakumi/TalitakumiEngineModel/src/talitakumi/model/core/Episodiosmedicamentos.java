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
@Table(name = "episodiosmedicamentos")
@NamedQueries({
    @NamedQuery(name = "Episodiosmedicamentos.findAll", query = "SELECT e FROM Episodiosmedicamentos e"),
    @NamedQuery(name = "Episodiosmedicamentos.findByMedicamento", query = "SELECT e FROM Episodiosmedicamentos e WHERE e.episodiosmedicamentosPK.medicamento = :medicamento"),
    @NamedQuery(name = "Episodiosmedicamentos.findByDosis", query = "SELECT e FROM Episodiosmedicamentos e WHERE e.dosis = :dosis"),
    @NamedQuery(name = "Episodiosmedicamentos.findByEpisodio", query = "SELECT e FROM Episodiosmedicamentos e WHERE e.episodiosmedicamentosPK.episodio = :episodio")})
public class Episodiosmedicamentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EpisodiosmedicamentosPK episodiosmedicamentosPK;
    @Column(name = "dosis")
    private String dosis;
    @JoinColumn(name = "medicamento", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Medicamentos medicamentos;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Episodios episodios;

    public Episodiosmedicamentos() {
    }

    public Episodiosmedicamentos(EpisodiosmedicamentosPK episodiosmedicamentosPK) {
        this.episodiosmedicamentosPK = episodiosmedicamentosPK;
    }

    public Episodiosmedicamentos(int medicamento, int episodio) {
        this.episodiosmedicamentosPK = new EpisodiosmedicamentosPK(medicamento, episodio);
    }

    public EpisodiosmedicamentosPK getEpisodiosmedicamentosPK() {
        return episodiosmedicamentosPK;
    }

    public void setEpisodiosmedicamentosPK(EpisodiosmedicamentosPK episodiosmedicamentosPK) {
        this.episodiosmedicamentosPK = episodiosmedicamentosPK;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public Medicamentos getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(Medicamentos medicamentos) {
        this.medicamentos = medicamentos;
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
        hash += (episodiosmedicamentosPK != null ? episodiosmedicamentosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodiosmedicamentos)) {
            return false;
        }
        Episodiosmedicamentos other = (Episodiosmedicamentos) object;
        if ((this.episodiosmedicamentosPK == null && other.episodiosmedicamentosPK != null) || (this.episodiosmedicamentosPK != null && !this.episodiosmedicamentosPK.equals(other.episodiosmedicamentosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodiosmedicamentos[episodiosmedicamentosPK=" + episodiosmedicamentosPK + "]";
    }

}
