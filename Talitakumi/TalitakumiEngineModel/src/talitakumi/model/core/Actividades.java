/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "actividades")
@NamedQueries({
    @NamedQuery(name = "Actividades.findAll", query = "SELECT a FROM Actividades a"),
    @NamedQuery(name = "Actividades.findById", query = "SELECT a FROM Actividades a WHERE a.id = :id"),
    @NamedQuery(name = "Actividades.findByDescripcion", query = "SELECT a FROM Actividades a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Actividades.findByColor", query = "SELECT a FROM Actividades a WHERE a.color = :color"),
    @NamedQuery(name = "Actividades.findByIcono", query = "SELECT a FROM Actividades a WHERE a.icono = :icono"),
    @NamedQuery(name = "Actividades.findByPrecio", query = "SELECT a FROM Actividades a WHERE a.precio = :precio")})
public class Actividades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "color")
    private String color;
    @Column(name = "icono")
    private String icono;
    @Column(name = "precio")
    private Float precio;
    @Column(name = "emisor")
    private Integer emisor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad")
    private Collection<Preciosclientes> preciosclientesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoepisodio")
    private Collection<Episodios> episodiosCollection;
    @JoinColumn(name = "tipoactividad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tipodeactividad tipoactividad;

    public Actividades() {
    }

    public Actividades(Integer id) {
        this.id = id;
    }

    public Actividades(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Collection<Preciosclientes> getPreciosclientesCollection() {
        return preciosclientesCollection;
    }

    public void setPreciosclientesCollection(Collection<Preciosclientes> preciosclientesCollection) {
        this.preciosclientesCollection = preciosclientesCollection;
    }

    public Collection<Episodios> getEpisodiosCollection() {
        return episodiosCollection;
    }

    public void setEpisodiosCollection(Collection<Episodios> episodiosCollection) {
        this.episodiosCollection = episodiosCollection;
    }

    public Tipodeactividad getTipoactividad() {
        return tipoactividad;
    }

    public void setTipoactividad(Tipodeactividad tipoactividad) {
        this.tipoactividad = tipoactividad;
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
        if (!(object instanceof Actividades)) {
            return false;
        }
        Actividades other = (Actividades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Actividades[id=" + id + "]";
    }

    /**
     * @return the emisor
     */
    public Integer getEmisor() {
        return emisor;
    }

    /**
     * @param emisor the emisor to set
     */
    public void setEmisor(Integer emisor) {
        this.emisor = emisor;
    }

}
