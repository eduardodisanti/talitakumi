/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "caja")
@NamedQueries({
    @NamedQuery(name = "Caja.findAll", query = "SELECT c FROM Caja c"),
    @NamedQuery(name = "Caja.findByFecha", query = "SELECT c FROM Caja c WHERE c.cajaPK.fecha = :fecha"),
    @NamedQuery(name = "Caja.findByHora", query = "SELECT c FROM Caja c WHERE c.cajaPK.hora = :hora"),
    @NamedQuery(name = "Caja.findByEpisodio", query = "SELECT c FROM Caja c WHERE c.episodio = :episodio"),
    @NamedQuery(name = "Caja.findBySigno", query = "SELECT c FROM Caja c WHERE c.signo = :signo"),
    @NamedQuery(name = "Caja.findByImporte", query = "SELECT c FROM Caja c WHERE c.importe = :importe"),
    @NamedQuery(name = "Caja.findByFuncionario", query = "SELECT c FROM Caja c WHERE c.funcionario = :funcionario"),
    @NamedQuery(name = "Caja.findByFactura", query = "SELECT c FROM Caja c WHERE c.factura = :factura"),
    @NamedQuery(name = "Caja.findBySeriefactura", query = "SELECT c FROM Caja c WHERE c.seriefactura = :seriefactura"),
    @NamedQuery(name = "Caja.findByCobrado", query = "SELECT c FROM Caja c WHERE c.cobrado = :cobrado")})
public class Caja implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CajaPK cajaPK;
    @Basic(optional = false)
    @Column(name = "episodio")
    private int episodio;
    @Basic(optional = false)
    @Column(name = "signo")
    private char signo;
    @Basic(optional = false)
    @Column(name = "importe")
    private float importe;
    @Basic(optional = false)
    @Column(name = "funcionario")
    private int funcionario;
    @Basic(optional = false)
    @Column(name = "factura")
    private int factura;
    @Basic(optional = false)
    @Column(name = "seriefactura")
    private char seriefactura;
    @Basic(optional = false)
    @Column(name = "cobrado")
    private char cobrado;

    public Caja() {
    }

    public Caja(CajaPK cajaPK) {
        this.cajaPK = cajaPK;
    }

    public Caja(CajaPK cajaPK, int episodio, char signo, float importe, int funcionario, int factura, char seriefactura, char cobrado) {
        this.cajaPK = cajaPK;
        this.episodio = episodio;
        this.signo = signo;
        this.importe = importe;
        this.funcionario = funcionario;
        this.factura = factura;
        this.seriefactura = seriefactura;
        this.cobrado = cobrado;
    }

    public Caja(Date fecha, Date hora) {
        this.cajaPK = new CajaPK(fecha, hora);
    }

    public CajaPK getCajaPK() {
        return cajaPK;
    }

    public void setCajaPK(CajaPK cajaPK) {
        this.cajaPK = cajaPK;
    }

    public int getEpisodio() {
        return episodio;
    }

    public void setEpisodio(int episodio) {
        this.episodio = episodio;
    }

    public char getSigno() {
        return signo;
    }

    public void setSigno(char signo) {
        this.signo = signo;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public int getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(int funcionario) {
        this.funcionario = funcionario;
    }

    public int getFactura() {
        return factura;
    }

    public void setFactura(int factura) {
        this.factura = factura;
    }

    public char getSeriefactura() {
        return seriefactura;
    }

    public void setSeriefactura(char seriefactura) {
        this.seriefactura = seriefactura;
    }

    public char getCobrado() {
        return cobrado;
    }

    public void setCobrado(char cobrado) {
        this.cobrado = cobrado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cajaPK != null ? cajaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caja)) {
            return false;
        }
        Caja other = (Caja) object;
        if ((this.cajaPK == null && other.cajaPK != null) || (this.cajaPK != null && !this.cajaPK.equals(other.cajaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Caja[cajaPK=" + cajaPK + "]";
    }

}
