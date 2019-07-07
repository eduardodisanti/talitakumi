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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "listaprecios")
@NamedQueries({
    @NamedQuery(name = "Listaprecios.findAll", query = "SELECT l FROM Listaprecios l"),
    @NamedQuery(name = "Listaprecios.findById", query = "SELECT l FROM Listaprecios l WHERE l.id = :id"),
    @NamedQuery(name = "Listaprecios.findByEmpresa", query = "SELECT l FROM Listaprecios l WHERE l.empresa = :empresa"),
    @NamedQuery(name = "Listaprecios.findByActividad", query = "SELECT l FROM Listaprecios l WHERE l.actividad = :actividad"),
    @NamedQuery(name = "Listaprecios.findByPrecio", query = "SELECT l FROM Listaprecios l WHERE l.precio = :precio")})
public class Listaprecios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "empresa")
    private Integer empresa;
    @Column(name = "estudio")
    private Integer estudio;
    @Column(name = "actividad")
    private Integer actividad;
    @Column(name = "precio")
    private Float precio;

    public Listaprecios() {
    }

    public Listaprecios(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }

    public Integer getActividad() {
        return actividad;
    }

    public void setActividad(Integer actividad) {
        this.actividad = actividad;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
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
        if (!(object instanceof Listaprecios)) {
            return false;
        }
        Listaprecios other = (Listaprecios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Listaprecios[id=" + id + "]";
    }

    /**
     * @return the estudio
     */
    public Integer getEstudio() {
        return estudio;
    }

    /**
     * @param estudio the estudio to set
     */
    public void setEstudio(Integer estudio) {
        this.estudio = estudio;
    }

}
