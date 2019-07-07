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
@Table(name = "servicioexparaclin")
@NamedQueries({
    @NamedQuery(name = "Servicioexparaclin.findAll", query = "SELECT s FROM Servicioexparaclin s"),
    @NamedQuery(name = "Servicioexparaclin.findByEpisodios", query = "SELECT s FROM Servicioexparaclin s WHERE s.episodios = :episodios")})
public class Servicioexparaclin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodios")
    private Integer episodios;
    @JoinColumn(name = "episodios", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios1;
    @JoinColumn(name = "procedencia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Procedencias procedencia;

    public Servicioexparaclin() {
    }

    public Servicioexparaclin(Integer episodios) {
        this.episodios = episodios;
    }

    public Integer getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Integer episodios) {
        this.episodios = episodios;
    }

    public Episodios getEpisodios1() {
        return episodios1;
    }

    public void setEpisodios1(Episodios episodios1) {
        this.episodios1 = episodios1;
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
        hash += (episodios != null ? episodios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicioexparaclin)) {
            return false;
        }
        Servicioexparaclin other = (Servicioexparaclin) object;
        if ((this.episodios == null && other.episodios != null) || (this.episodios != null && !this.episodios.equals(other.episodios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Servicioexparaclin[episodios=" + episodios + "]";
    }

}
