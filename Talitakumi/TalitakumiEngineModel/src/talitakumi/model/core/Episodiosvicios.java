/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "episodiosvicios")
@NamedQueries({
    @NamedQuery(name = "Episodiosvicios.findAll", query = "SELECT e FROM Episodiosvicios e"),
    @NamedQuery(name = "Episodiosvicios.findByVicio", query = "SELECT e FROM Episodiosvicios e WHERE e.episodiosviciosPK.vicio = :vicio"),
    @NamedQuery(name = "Episodiosvicios.findByEpisodio", query = "SELECT e FROM Episodiosvicios e WHERE e.episodiosviciosPK.episodio = :episodio"),
    @NamedQuery(name = "Episodiosvicios.findByDetalles", query = "SELECT e FROM Episodiosvicios e WHERE e.detalles = :detalles")})
public class Episodiosvicios implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EpisodiosviciosPK episodiosviciosPK;
    @Basic(optional = false)
    @Column(name = "detalles")
    private String detalles;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Episodios episodios;
    @JoinColumn(name = "vicio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Viciossociales viciossociales;

    public Episodiosvicios() {
    }

    public Episodiosvicios(EpisodiosviciosPK episodiosviciosPK) {
        this.episodiosviciosPK = episodiosviciosPK;
    }

    public Episodiosvicios(EpisodiosviciosPK episodiosviciosPK, String detalles) {
        this.episodiosviciosPK = episodiosviciosPK;
        this.detalles = detalles;
    }

    public Episodiosvicios(int vicio, int episodio) {
        this.episodiosviciosPK = new EpisodiosviciosPK(vicio, episodio);
    }

    public EpisodiosviciosPK getEpisodiosviciosPK() {
        return episodiosviciosPK;
    }

    public void setEpisodiosviciosPK(EpisodiosviciosPK episodiosviciosPK) {
        this.episodiosviciosPK = episodiosviciosPK;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Episodios getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Episodios episodios) {
        this.episodios = episodios;
    }

    public Viciossociales getViciossociales() {
        return viciossociales;
    }

    public void setViciossociales(Viciossociales viciossociales) {
        this.viciossociales = viciossociales;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (episodiosviciosPK != null ? episodiosviciosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodiosvicios)) {
            return false;
        }
        Episodiosvicios other = (Episodiosvicios) object;
        if ((this.episodiosviciosPK == null && other.episodiosviciosPK != null) || (this.episodiosviciosPK != null && !this.episodiosviciosPK.equals(other.episodiosviciosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodiosvicios[episodiosviciosPK=" + episodiosviciosPK + "]";
    }

}
