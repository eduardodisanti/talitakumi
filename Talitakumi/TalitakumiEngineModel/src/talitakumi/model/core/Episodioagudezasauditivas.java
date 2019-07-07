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
@Table(name = "episodioagudezasauditivas")
@NamedQueries({
    @NamedQuery(name = "Episodioagudezasauditivas.findAll", query = "SELECT e FROM Episodioagudezasauditivas e"),
    @NamedQuery(name = "Episodioagudezasauditivas.findByEpisodio", query = "SELECT e FROM Episodioagudezasauditivas e WHERE e.episodio = :episodio"),
    @NamedQuery(name = "Episodioagudezasauditivas.findByGrado", query = "SELECT e FROM Episodioagudezasauditivas e WHERE e.grado = :grado"),
    @NamedQuery(name = "Episodioagudezasauditivas.findByAudifono", query = "SELECT e FROM Episodioagudezasauditivas e WHERE e.audifono = :audifono")})
public class Episodioagudezasauditivas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @Column(name = "grado")
    private Integer grado;
    @Column(name = "audifono")
    private Character audifono;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;

    public Episodioagudezasauditivas() {
    }

    public Episodioagudezasauditivas(Integer episodio) {
        this.episodio = episodio;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public Integer getGrado() {
        return grado;
    }

    public void setGrado(Integer grado) {
        this.grado = grado;
    }

    public Character getAudifono() {
        return audifono;
    }

    public void setAudifono(Character audifono) {
        this.audifono = audifono;
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
        if (!(object instanceof Episodioagudezasauditivas)) {
            return false;
        }
        Episodioagudezasauditivas other = (Episodioagudezasauditivas) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodioagudezasauditivas[episodio=" + episodio + "]";
    }

}
