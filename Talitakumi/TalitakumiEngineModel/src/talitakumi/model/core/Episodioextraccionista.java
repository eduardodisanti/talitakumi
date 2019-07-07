/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "episodioextraccionista")
@NamedQueries({
    @NamedQuery(name = "Episodioextraccionista.findAll", query = "SELECT e FROM Episodioextraccionista e"),
    @NamedQuery(name = "Episodioextraccionista.findByEpisodio", query = "SELECT e FROM Episodioextraccionista e WHERE e.episodio = :episodio"),
    @NamedQuery(name = "Episodioextraccionista.findByDiabetes", query = "SELECT e FROM Episodioextraccionista e WHERE e.diabetes = :diabetes"),
    @NamedQuery(name = "Episodioextraccionista.findByAyuno", query = "SELECT e FROM Episodioextraccionista e WHERE e.ayuno = :ayuno"),
    @NamedQuery(name = "Episodioextraccionista.findByTraeOrina", query = "SELECT e FROM Episodioextraccionista e WHERE e.traeOrina = :traeOrina"),
    @NamedQuery(name = "Episodioextraccionista.findByApResSangre", query = "SELECT e FROM Episodioextraccionista e WHERE e.apResSangre = :apResSangre"),
    @NamedQuery(name = "Episodioextraccionista.findByApResOrina", query = "SELECT e FROM Episodioextraccionista e WHERE e.apResOrina = :apResOrina"),
    @NamedQuery(name = "Episodioextraccionista.findByApResCompletos", query = "SELECT e FROM Episodioextraccionista e WHERE e.apResCompletos = :apResCompletos")})
public class Episodioextraccionista implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @Column(name = "diabetes")
    private Character diabetes;
    @Column(name = "ayuno")
    private Character ayuno;
    @Column(name = "trae_orina")
    private Character traeOrina;
    @Column(name = "ap_res_sangre")
    private Character apResSangre;
    @Column(name = "ap_res_orina")
    private Character apResOrina;
    @Column(name = "ap_res_completos")
    private Character apResCompletos;
    @Basic(optional = false)
    @Column(name = "tubo")
    private Integer tubo;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;

    public Episodioextraccionista() {
    }

    public Episodioextraccionista(Integer episodio) {
        this.episodio = episodio;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public Character getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(Character diabetes) {
        this.diabetes = diabetes;
    }

    public Character getAyuno() {
        return ayuno;
    }

    public void setAyuno(Character ayuno) {
        this.ayuno = ayuno;
    }

    public Character getTraeOrina() {
        return traeOrina;
    }

    public void setTraeOrina(Character traeOrina) {
        this.traeOrina = traeOrina;
    }

    public Character getApResSangre() {
        return apResSangre;
    }

    public void setApResSangre(Character apResSangre) {
        this.apResSangre = apResSangre;
    }

    public Character getApResOrina() {
        return apResOrina;
    }

    public void setApResOrina(Character apResOrina) {
        this.apResOrina = apResOrina;
    }

    public Character getApResCompletos() {
        return apResCompletos;
    }

    public void setApResCompletos(Character apResCompletos) {
        this.apResCompletos = apResCompletos;
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
        hash += (episodio != null ? episodio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodioextraccionista)) {
            return false;
        }
        Episodioextraccionista other = (Episodioextraccionista) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodioextraccionista[episodio=" + episodio + "]";
    }

    /**
     * @return the tubo
     */
    public Integer getTubo() {
        return tubo;
    }

    /**
     * @param tubo the tubo to set
     */
    public void setTubo(Integer tubo) {
        this.tubo = tubo;
    }

}
