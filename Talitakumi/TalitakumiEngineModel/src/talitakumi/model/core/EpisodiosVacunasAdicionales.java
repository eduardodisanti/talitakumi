/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "episodios_vacunas_adicionales")
@NamedQueries({
    @NamedQuery(name = "EpisodiosVacunasAdicionales.findAll", query = "SELECT e FROM EpisodiosVacunasAdicionales e"),
    @NamedQuery(name = "EpisodiosVacunasAdicionales.findById", query = "SELECT e FROM EpisodiosVacunasAdicionales e WHERE e.id = :id")})
public class EpisodiosVacunasAdicionales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "episodio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Episodios episodio;
    @JoinColumn(name = "vacuna", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vacunas vacuna;

    public EpisodiosVacunasAdicionales() {
    }

    public EpisodiosVacunasAdicionales(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Episodios getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Episodios episodio) {
        this.episodio = episodio;
    }

    public Vacunas getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacunas vacuna) {
        this.vacuna = vacuna;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EpisodiosVacunasAdicionales)) {
            return false;
        }
        EpisodiosVacunasAdicionales other = (EpisodiosVacunasAdicionales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.EpisodiosVacunasAdicionales[id=" + id + "]";
    }

}
