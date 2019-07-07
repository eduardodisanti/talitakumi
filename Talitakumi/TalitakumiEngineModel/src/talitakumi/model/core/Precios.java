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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "precios")
@NamedQueries({
    @NamedQuery(name = "Precios.findAll", query = "SELECT p FROM Precios p"),
    @NamedQuery(name = "Precios.findByAaaa", query = "SELECT p FROM Precios p WHERE p.preciosPK.aaaa = :aaaa"),
    @NamedQuery(name = "Precios.findByMm", query = "SELECT p FROM Precios p WHERE p.preciosPK.mm = :mm"),
    @NamedQuery(name = "Precios.findByCliente", query = "SELECT p FROM Precios p WHERE p.preciosPK.cliente = :cliente"),
    @NamedQuery(name = "Precios.findByActividad", query = "SELECT p FROM Precios p WHERE p.preciosPK.actividad = :actividad"),
    @NamedQuery(name = "Precios.findByDescuento", query = "SELECT p FROM Precios p WHERE p.descuento = :descuento"),
    @NamedQuery(name = "Precios.findBySigno", query = "SELECT p FROM Precios p WHERE p.signo = :signo")})
public class Precios implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreciosPK preciosPK;
    @Basic(optional = false)
    @Column(name = "descuento")
    private float descuento;
    @Basic(optional = false)
    @Column(name = "signo")
    private char signo;

    public Precios() {
    }

    public Precios(PreciosPK preciosPK) {
        this.preciosPK = preciosPK;
    }

    public Precios(PreciosPK preciosPK, float descuento, char signo) {
        this.preciosPK = preciosPK;
        this.descuento = descuento;
        this.signo = signo;
    }

    public Precios(int aaaa, int mm, int cliente, int actividad) {
        this.preciosPK = new PreciosPK(aaaa, mm, cliente, actividad);
    }

    public PreciosPK getPreciosPK() {
        return preciosPK;
    }

    public void setPreciosPK(PreciosPK preciosPK) {
        this.preciosPK = preciosPK;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public char getSigno() {
        return signo;
    }

    public void setSigno(char signo) {
        this.signo = signo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preciosPK != null ? preciosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Precios)) {
            return false;
        }
        Precios other = (Precios) object;
        if ((this.preciosPK == null && other.preciosPK != null) || (this.preciosPK != null && !this.preciosPK.equals(other.preciosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Precios[preciosPK=" + preciosPK + "]";
    }

}
