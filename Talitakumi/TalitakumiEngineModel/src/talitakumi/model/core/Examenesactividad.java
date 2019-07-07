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
@Table(name = "examenesactividad")
@NamedQueries({
    @NamedQuery(name = "Examenesactividad.findAll", query = "SELECT e FROM Examenesactividad e"),
    @NamedQuery(name = "Examenesactividad.findById", query = "SELECT e FROM Examenesactividad e WHERE e.id = :id")})
public class Examenesactividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "tipoactividad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tipodeactividad tipoactividad;
    @JoinColumn(name = "examen", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estudios examen;

    public Examenesactividad() {
    }

    public Examenesactividad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tipodeactividad getTipoactividad() {
        return tipoactividad;
    }

    public void setTipoactividad(Tipodeactividad tipoactividad) {
        this.tipoactividad = tipoactividad;
    }

    public Estudios getExamen() {
        return examen;
    }

    public void setExamen(Estudios examen) {
        this.examen = examen;
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
        if (!(object instanceof Examenesactividad)) {
            return false;
        }
        Examenesactividad other = (Examenesactividad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Examenesactividad[id=" + id + "]";
    }

}
