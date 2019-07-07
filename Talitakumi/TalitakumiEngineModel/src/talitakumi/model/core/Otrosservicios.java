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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "otrosservicios")
@NamedQueries({
    @NamedQuery(name = "Otrosservicios.findAll", query = "SELECT o FROM Otrosservicios o"),
    @NamedQuery(name = "Otrosservicios.findByEpisodio", query = "SELECT o FROM Otrosservicios o WHERE o.episodio = :episodio")})
public class Otrosservicios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;
    @JoinColumn(name = "procedencia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Procedencias procedencia;

    public Otrosservicios() {
    }

    public Otrosservicios(Integer episodio) {
        this.episodio = episodio;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public Episodios getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Episodios episodios) {
        this.episodios = episodios;
    }

    public Procedencias getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(Procedencias procedencia) {
        this.procedencia = procedencia;
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
        if (!(object instanceof Otrosservicios)) {
            return false;
        }
        Otrosservicios other = (Otrosservicios) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Otrosservicios[episodio=" + episodio + "]";
    }

}
