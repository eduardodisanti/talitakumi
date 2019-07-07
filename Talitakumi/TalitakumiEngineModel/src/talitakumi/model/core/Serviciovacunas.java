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
@Table(name = "serviciovacunas")
@NamedQueries({
    @NamedQuery(name = "Serviciovacunas.findAll", query = "SELECT s FROM Serviciovacunas s"),
    @NamedQuery(name = "Serviciovacunas.findByEpisodio", query = "SELECT s FROM Serviciovacunas s WHERE s.episodio = :episodio")})
public class Serviciovacunas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @JoinColumn(name = "procedencia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Procedencias procedencia;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;

    public Serviciovacunas() {
    }

    public Serviciovacunas(Integer episodio) {
        this.episodio = episodio;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public Procedencias getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(Procedencias procedencia) {
        this.procedencia = procedencia;
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
        if (!(object instanceof Serviciovacunas)) {
            return false;
        }
        Serviciovacunas other = (Serviciovacunas) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Serviciovacunas[episodio=" + episodio + "]";
    }

}
