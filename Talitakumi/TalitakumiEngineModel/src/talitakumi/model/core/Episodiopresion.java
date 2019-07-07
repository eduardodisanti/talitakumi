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
@Table(name = "episodiopresion")
@NamedQueries({
    @NamedQuery(name = "Episodiopresion.findAll", query = "SELECT e FROM Episodiopresion e"),
    @NamedQuery(name = "Episodiopresion.findByEpisodio", query = "SELECT e FROM Episodiopresion e WHERE e.episodio = :episodio"),
    @NamedQuery(name = "Episodiopresion.findBySistolicaprimeratoma", query = "SELECT e FROM Episodiopresion e WHERE e.sistolicaprimeratoma = :sistolicaprimeratoma"),
    @NamedQuery(name = "Episodiopresion.findByDiastolicaprimeratoma", query = "SELECT e FROM Episodiopresion e WHERE e.diastolicaprimeratoma = :diastolicaprimeratoma"),
    @NamedQuery(name = "Episodiopresion.findByDiastolicasegundatoma", query = "SELECT e FROM Episodiopresion e WHERE e.diastolicasegundatoma = :diastolicasegundatoma"),
    @NamedQuery(name = "Episodiopresion.findBySistolicasegundatoma", query = "SELECT e FROM Episodiopresion e WHERE e.sistolicasegundatoma = :sistolicasegundatoma")})
public class Episodiopresion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @Basic(optional = false)
    @Column(name = "sistolicaprimeratoma")
    private int sistolicaprimeratoma;
    @Basic(optional = false)
    @Column(name = "diastolicaprimeratoma")
    private int diastolicaprimeratoma;
    @Basic(optional = false)
    @Column(name = "diastolicasegundatoma")
    private int diastolicasegundatoma;
    @Basic(optional = false)
    @Column(name = "sistolicasegundatoma")
    private int sistolicasegundatoma;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;

    public Episodiopresion() {
    }

    public Episodiopresion(Integer episodio) {
        this.episodio = episodio;
    }

    public Episodiopresion(Integer episodio, int sistolicaprimeratoma, int diastolicaprimeratoma, int diastolicasegundatoma, int sistolicasegundatoma) {
        this.episodio = episodio;
        this.sistolicaprimeratoma = sistolicaprimeratoma;
        this.diastolicaprimeratoma = diastolicaprimeratoma;
        this.diastolicasegundatoma = diastolicasegundatoma;
        this.sistolicasegundatoma = sistolicasegundatoma;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public int getSistolicaprimeratoma() {
        return sistolicaprimeratoma;
    }

    public void setSistolicaprimeratoma(int sistolicaprimeratoma) {
        this.sistolicaprimeratoma = sistolicaprimeratoma;
    }

    public int getDiastolicaprimeratoma() {
        return diastolicaprimeratoma;
    }

    public void setDiastolicaprimeratoma(int diastolicaprimeratoma) {
        this.diastolicaprimeratoma = diastolicaprimeratoma;
    }

    public int getDiastolicasegundatoma() {
        return diastolicasegundatoma;
    }

    public void setDiastolicasegundatoma(int diastolicasegundatoma) {
        this.diastolicasegundatoma = diastolicasegundatoma;
    }

    public int getSistolicasegundatoma() {
        return sistolicasegundatoma;
    }

    public void setSistolicasegundatoma(int sistolicasegundatoma) {
        this.sistolicasegundatoma = sistolicasegundatoma;
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
        if (!(object instanceof Episodiopresion)) {
            return false;
        }
        Episodiopresion other = (Episodiopresion) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodiopresion[episodio=" + episodio + "]";
    }

}
