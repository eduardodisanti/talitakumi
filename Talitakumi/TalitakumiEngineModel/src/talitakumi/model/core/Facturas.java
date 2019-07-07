/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "facturas")
@NamedQueries({
    @NamedQuery(name = "Facturas.findAll", query = "SELECT f FROM Facturas f"),
    @NamedQuery(name = "Facturas.findById", query = "SELECT f FROM Facturas f WHERE f.id = :id"),
    @NamedQuery(name = "Facturas.findBySerie", query = "SELECT f FROM Facturas f WHERE f.serie = :serie"),
    @NamedQuery(name = "Facturas.findByNumero", query = "SELECT f FROM Facturas f WHERE f.numero = :numero"),
    @NamedQuery(name = "Facturas.findByCantidad", query = "SELECT f FROM Facturas f WHERE f.cantidad = :cantidad"),
    @NamedQuery(name = "Facturas.findByImporte", query = "SELECT f FROM Facturas f WHERE f.importe = :importe"),
    @NamedQuery(name = "Facturas.findByEntregado", query = "SELECT f FROM Facturas f WHERE f.entregado = :entregado"),
    @NamedQuery(name = "Facturas.findByFechaentregado", query = "SELECT f FROM Facturas f WHERE f.fechaentregado = :fechaentregado"),
    @NamedQuery(name = "Facturas.findByRollo", query = "SELECT f FROM Facturas f WHERE f.rollo = :rollo"),
    @NamedQuery(name = "Facturas.findByTipo", query = "SELECT f FROM Facturas f WHERE f.tipo = :tipo"),
    @NamedQuery(name = "Facturas.findByComentario", query = "SELECT f FROM Facturas f WHERE f.comentario = :comentario"),
    @NamedQuery(name = "Facturas.findByDescuentos", query = "SELECT f FROM Facturas f WHERE f.descuentos = :descuentos"),
    @NamedQuery(name = "Facturas.findByEmisor", query = "SELECT f FROM Facturas f WHERE f.emisor = :emisor")})
public class Facturas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "serie")
    private String serie;
    @Basic(optional = false)
    @Column(name = "numero")
    private int numero;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "importe")
    private float importe;
    @Basic(optional = false)
    @Column(name = "entregado")
    private String entregado;
    @Basic(optional = false)
    @Column(name = "fechaentregado")
    @Temporal(TemporalType.DATE)
    private Date fechaentregado;
    @Basic(optional = false)
    @Column(name = "rollo")
    private int rollo;
    @Basic(optional = false)
    @Column(name = "tipo")
    private char tipo;
    @Basic(optional = false)
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "descuentos")
    private float descuentos;
    @Basic(optional = false)
    @Column(name = "emisor")
    private int emisor;
    @JoinColumn(name = "episodio", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Episodios episodio;
    @JoinColumn(name = "funcionario", referencedColumnName = "funcionario")
    @ManyToOne(optional = false)
    private Usuarios funcionario;

    public Facturas() {
    }

    public Facturas(Integer id) {
        this.id = id;
    }

    public Facturas(Integer id, String serie, int numero, int cantidad, float importe, String entregado, Date fechaentregado, int rollo, char tipo, String comentario, float descuentos, int emisor) {
        this.id = id;
        this.serie = serie;
        this.numero = numero;
        this.cantidad = cantidad;
        this.importe = importe;
        this.entregado = entregado;
        this.fechaentregado = fechaentregado;
        this.rollo = rollo;
        this.tipo = tipo;
        this.comentario = comentario;
        this.descuentos = descuentos;
        this.emisor = emisor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public String getEntregado() {
        return entregado;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }

    public Date getFechaentregado() {
        return fechaentregado;
    }

    public void setFechaentregado(Date fechaentregado) {
        this.fechaentregado = fechaentregado;
    }

    public int getRollo() {
        return rollo;
    }

    public void setRollo(int rollo) {
        this.rollo = rollo;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(float descuentos) {
        this.descuentos = descuentos;
    }

    public int getEmisor() {
        return emisor;
    }

    public void setEmisor(int emisor) {
        this.emisor = emisor;
    }

    public Episodios getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Episodios episodio) {
        this.episodio = episodio;
    }

    public Usuarios getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuarios funcionario) {
        this.funcionario = funcionario;
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
        if (!(object instanceof Facturas)) {
            return false;
        }
        Facturas other = (Facturas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Facturas[id=" + id + "]";
    }

}
