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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "seguros")
@NamedQueries({
    @NamedQuery(name = "Seguros.findAll", query = "SELECT s FROM Seguros s"),
    @NamedQuery(name = "Seguros.findById", query = "SELECT s FROM Seguros s WHERE s.id = :id"),
    @NamedQuery(name = "Seguros.findByDescripcion", query = "SELECT s FROM Seguros s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Seguros.findByCliente", query = "SELECT s FROM Seguros s WHERE s.cliente = :cliente")})
public class Seguros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "cliente")
    private Integer cliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seguro")
    private Collection<Pacientes> pacientesCollection;

    public Seguros() {
    }

    public Seguros(Integer id) {
        this.id = id;
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

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Collection<Pacientes> getPacientesCollection() {
        return pacientesCollection;
    }

    public void setPacientesCollection(Collection<Pacientes> pacientesCollection) {
        this.pacientesCollection = pacientesCollection;
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
        if (!(object instanceof Seguros)) {
            return false;
        }
        Seguros other = (Seguros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Seguros[id=" + id + "]";
    }

}
