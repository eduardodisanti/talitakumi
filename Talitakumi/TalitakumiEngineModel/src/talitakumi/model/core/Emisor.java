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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "emisor")
@NamedQueries({
    @NamedQuery(name = "Emisor.findAll", query = "SELECT e FROM Emisor e"),
    @NamedQuery(name = "Emisor.findById", query = "SELECT e FROM Emisor e WHERE e.id = :id"),
    @NamedQuery(name = "Emisor.findByRut", query = "SELECT e FROM Emisor e WHERE e.rut = :rut"),
    @NamedQuery(name = "Emisor.findByNombre", query = "SELECT e FROM Emisor e WHERE e.nombre = :nombre")})
public class Emisor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Rut")
    private float rut;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    public Emisor() {
    }

    public Emisor(Integer id) {
        this.id = id;
    }

    public Emisor(Integer id, float rut, String nombre) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getRut() {
        return rut;
    }

    public void setRut(float rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if (!(object instanceof Emisor)) {
            return false;
        }
        Emisor other = (Emisor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Emisor[id=" + id + "]";
    }

}
