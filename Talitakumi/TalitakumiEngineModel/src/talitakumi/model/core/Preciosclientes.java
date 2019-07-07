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
@Table(name = "preciosclientes")
@NamedQueries({
    @NamedQuery(name = "Preciosclientes.findAll", query = "SELECT p FROM Preciosclientes p"),
    @NamedQuery(name = "Preciosclientes.findById", query = "SELECT p FROM Preciosclientes p WHERE p.id = :id"),
    @NamedQuery(name = "Preciosclientes.findByCliente", query = "SELECT p FROM Preciosclientes p WHERE p.cliente = :cliente"),
    @NamedQuery(name = "Preciosclientes.findByDescuento", query = "SELECT p FROM Preciosclientes p WHERE p.descuento = :descuento"),
    @NamedQuery(name = "Preciosclientes.findByCredito", query = "SELECT p FROM Preciosclientes p WHERE p.credito = :credito")})
public class Preciosclientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cliente")
    private int cliente;
    @Basic(optional = false)
    @Column(name = "descuento")
    private float descuento;
    @Basic(optional = false)
    @Column(name = "credito")
    private char credito;
    @JoinColumn(name = "actividad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Actividades actividad;

    public Preciosclientes() {
    }

    public Preciosclientes(Integer id) {
        this.id = id;
    }

    public Preciosclientes(Integer id, int cliente, float descuento, char credito) {
        this.id = id;
        this.cliente = cliente;
        this.descuento = descuento;
        this.credito = credito;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public char getCredito() {
        return credito;
    }

    public void setCredito(char credito) {
        this.credito = credito;
    }

    public Actividades getActividad() {
        return actividad;
    }

    public void setActividad(Actividades actividad) {
        this.actividad = actividad;
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
        if (!(object instanceof Preciosclientes)) {
            return false;
        }
        Preciosclientes other = (Preciosclientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Preciosclientes[id=" + id + "]";
    }

}
