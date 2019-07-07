/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rupertus
 */
@Embeddable
public class PreciosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "aaaa")
    private int aaaa;
    @Basic(optional = false)
    @Column(name = "mm")
    private int mm;
    @Basic(optional = false)
    @Column(name = "cliente")
    private int cliente;
    @Basic(optional = false)
    @Column(name = "actividad")
    private int actividad;

    public PreciosPK() {
    }

    public PreciosPK(int aaaa, int mm, int cliente, int actividad) {
        this.aaaa = aaaa;
        this.mm = mm;
        this.cliente = cliente;
        this.actividad = actividad;
    }

    public int getAaaa() {
        return aaaa;
    }

    public void setAaaa(int aaaa) {
        this.aaaa = aaaa;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getActividad() {
        return actividad;
    }

    public void setActividad(int actividad) {
        this.actividad = actividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) aaaa;
        hash += (int) mm;
        hash += (int) cliente;
        hash += (int) actividad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreciosPK)) {
            return false;
        }
        PreciosPK other = (PreciosPK) object;
        if (this.aaaa != other.aaaa) {
            return false;
        }
        if (this.mm != other.mm) {
            return false;
        }
        if (this.cliente != other.cliente) {
            return false;
        }
        if (this.actividad != other.actividad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.PreciosPK[aaaa=" + aaaa + ", mm=" + mm + ", cliente=" + cliente + ", actividad=" + actividad + "]";
    }

}
