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
@Table(name = "episodioexamenfisico")
@NamedQueries({
    @NamedQuery(name = "Episodioexamenfisico.findAll", query = "SELECT e FROM Episodioexamenfisico e"),
    @NamedQuery(name = "Episodioexamenfisico.findByEpisodio", query = "SELECT e FROM Episodioexamenfisico e WHERE e.episodioexamenfisicoPK.episodio = :episodio"),
    @NamedQuery(name = "Episodioexamenfisico.findByExamen", query = "SELECT e FROM Episodioexamenfisico e WHERE e.episodioexamenfisicoPK.examen = :examen"),
    @NamedQuery(name = "Episodioexamenfisico.findByValor", query = "SELECT e FROM Episodioexamenfisico e WHERE e.valor = :valor")})
public class Episodioexamenfisico implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EpisodioexamenfisicoPK episodioexamenfisicoPK;
    @Column(name = "valor")
    private Integer valor;
    @JoinColumn(name = "examen", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Examenfisico examenfisico;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Episodios episodios;

    public Episodioexamenfisico() {
    }

    public Episodioexamenfisico(EpisodioexamenfisicoPK episodioexamenfisicoPK) {
        this.episodioexamenfisicoPK = episodioexamenfisicoPK;
    }

    public Episodioexamenfisico(int episodio, int examen) {
        this.episodioexamenfisicoPK = new EpisodioexamenfisicoPK(episodio, examen);
    }

    public EpisodioexamenfisicoPK getEpisodioexamenfisicoPK() {
        return episodioexamenfisicoPK;
    }

    public void setEpisodioexamenfisicoPK(EpisodioexamenfisicoPK episodioexamenfisicoPK) {
        this.episodioexamenfisicoPK = episodioexamenfisicoPK;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Examenfisico getExamenfisico() {
        return examenfisico;
    }

    public void setExamenfisico(Examenfisico examenfisico) {
        this.examenfisico = examenfisico;
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
        hash += (episodioexamenfisicoPK != null ? episodioexamenfisicoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodioexamenfisico)) {
            return false;
        }
        Episodioexamenfisico other = (Episodioexamenfisico) object;
        if ((this.episodioexamenfisicoPK == null && other.episodioexamenfisicoPK != null) || (this.episodioexamenfisicoPK != null && !this.episodioexamenfisicoPK.equals(other.episodioexamenfisicoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodioexamenfisico[episodioexamenfisicoPK=" + episodioexamenfisicoPK + "]";
    }

}
