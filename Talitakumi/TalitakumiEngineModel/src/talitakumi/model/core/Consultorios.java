/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "consultorios")
@NamedQueries({
    @NamedQuery(name = "Consultorios.findAll", query = "SELECT c FROM Consultorios c"),
    @NamedQuery(name = "Consultorios.findById", query = "SELECT c FROM Consultorios c WHERE c.id = :id"),
    @NamedQuery(name = "Consultorios.findByDescripcion", query = "SELECT c FROM Consultorios c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Consultorios.findBySucursal", query = "SELECT c FROM Consultorios c WHERE c.sucursal = :sucursal"),
    @NamedQuery(name = "Consultorios.findByHabilitadourgente", query = "SELECT c FROM Consultorios c WHERE c.habilitadourgente = :habilitadourgente")})
public class Consultorios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "sucursal")
    private char sucursal;
    @Column(name = "habilitadourgente")
    private String habilitadourgente;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consultorios")
    private Consultoriosactivos consultoriosactivos;

    public Consultorios() {
    }

    public Consultorios(Integer id) {
        this.id = id;
    }

    public Consultorios(Integer id, char sucursal) {
        this.id = id;
        this.sucursal = sucursal;
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

    public char getSucursal() {
        return sucursal;
    }

    public void setSucursal(char sucursal) {
        this.sucursal = sucursal;
    }

    public String getHabilitadourgente() {
        return habilitadourgente;
    }

    public void setHabilitadourgente(String habilitadourgente) {
        this.habilitadourgente = habilitadourgente;
    }

    public Consultoriosactivos getConsultoriosactivos() {
        return consultoriosactivos;
    }

    public void setConsultoriosactivos(Consultoriosactivos consultoriosactivos) {
        this.consultoriosactivos = consultoriosactivos;
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
        if (!(object instanceof Consultorios)) {
            return false;
        }
        Consultorios other = (Consultorios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Consultorios[id=" + id + "]";
    }

}
