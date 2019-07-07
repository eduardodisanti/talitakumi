/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.admin;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author eduardodisanti
 */
@Entity
@Table(name = "actividades", catalog = "talitakumi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Actividades.findAll", query = "SELECT a FROM Actividades a"),
    @NamedQuery(name = "Actividades.findById", query = "SELECT a FROM Actividades a WHERE a.id = :id"),
    @NamedQuery(name = "Actividades.findByDescripcion", query = "SELECT a FROM Actividades a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Actividades.findByColor", query = "SELECT a FROM Actividades a WHERE a.color = :color"),
    @NamedQuery(name = "Actividades.findByIcono", query = "SELECT a FROM Actividades a WHERE a.icono = :icono"),
    @NamedQuery(name = "Actividades.findByPrecio", query = "SELECT a FROM Actividades a WHERE a.precio = :precio"),
    @NamedQuery(name = "Actividades.findByTipoactividad", query = "SELECT a FROM Actividades a WHERE a.tipoactividad = :tipoactividad"),
    @NamedQuery(name = "Actividades.findByEmisor", query = "SELECT a FROM Actividades a WHERE a.emisor = :emisor")})
public class Actividades implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
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
    private BigDecimal precio;
    @Basic(optional = false)
    @Column(name = "tipoactividad")
    private int tipoactividad;
    @Basic(optional = false)
    @Column(name = "emisor")
    private int emisor;

    public Actividades() {
    }

    public Actividades(Integer id) {
        this.id = id;
    }

    public Actividades(Integer id, String descripcion, int tipoactividad, int emisor) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipoactividad = tipoactividad;
        this.emisor = emisor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        String oldDescripcion = this.descripcion;
        this.descripcion = descripcion;
        changeSupport.firePropertyChange("descripcion", oldDescripcion, descripcion);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        String oldColor = this.color;
        this.color = color;
        changeSupport.firePropertyChange("color", oldColor, color);
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        String oldIcono = this.icono;
        this.icono = icono;
        changeSupport.firePropertyChange("icono", oldIcono, icono);
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        BigDecimal oldPrecio = this.precio;
        this.precio = precio;
        changeSupport.firePropertyChange("precio", oldPrecio, precio);
    }

    public int getTipoactividad() {
        return tipoactividad;
    }

    public void setTipoactividad(int tipoactividad) {
        int oldTipoactividad = this.tipoactividad;
        this.tipoactividad = tipoactividad;
        changeSupport.firePropertyChange("tipoactividad", oldTipoactividad, tipoactividad);
    }

    public int getEmisor() {
        return emisor;
    }

    public void setEmisor(int emisor) {
        int oldEmisor = this.emisor;
        this.emisor = emisor;
        changeSupport.firePropertyChange("emisor", oldEmisor, emisor);
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
        return "talitakumi.admin.Actividades[id=" + id + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
