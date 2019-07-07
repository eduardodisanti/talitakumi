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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "diariocaja")
@NamedQueries({
    @NamedQuery(name = "Diariocaja.findAll", query = "SELECT d FROM Diariocaja d"),
    @NamedQuery(name = "Diariocaja.findById", query = "SELECT d FROM Diariocaja d WHERE d.id = :id"),
    @NamedQuery(name = "Diariocaja.findByFecha", query = "SELECT d FROM Diariocaja d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "Diariocaja.findByHora", query = "SELECT d FROM Diariocaja d WHERE d.hora = :hora"),
    @NamedQuery(name = "Diariocaja.findByEpisodio", query = "SELECT d FROM Diariocaja d WHERE d.episodio = :episodio"),
    @NamedQuery(name = "Diariocaja.findByUsuario", query = "SELECT d FROM Diariocaja d WHERE d.usuario = :usuario"),
    @NamedQuery(name = "Diariocaja.findByTipo", query = "SELECT d FROM Diariocaja d WHERE d.tipo = :tipo"),
    @NamedQuery(name = "Diariocaja.findByDocumento", query = "SELECT d FROM Diariocaja d WHERE d.documento = :documento"),
    @NamedQuery(name = "Diariocaja.findBySerie", query = "SELECT d FROM Diariocaja d WHERE d.serie = :serie"),
    @NamedQuery(name = "Diariocaja.findByImporte", query = "SELECT d FROM Diariocaja d WHERE d.importe = :importe")})
public class Diariocaja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Basic(optional = false)
    @Column(name = "episodio")
    private int episodio;
    @Basic(optional = false)
    @Column(name = "usuario")
    private int usuario;
    @Basic(optional = false)
    @Column(name = "tipo")
    private char tipo;
    @Basic(optional = false)
    @Column(name = "documento")
    private int documento;
    @Basic(optional = false)
    @Column(name = "serie")
    private char serie;
    @Basic(optional = false)
    @Column(name = "importe")
    private float importe;

    public Diariocaja() {
    }

    public Diariocaja(Integer id) {
        this.id = id;
    }

    public Diariocaja(Integer id, Date fecha, Date hora, int episodio, int usuario, char tipo, int documento, char serie, float importe) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.episodio = episodio;
        this.usuario = usuario;
        this.tipo = tipo;
        this.documento = documento;
        this.serie = serie;
        this.importe = importe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public int getEpisodio() {
        return episodio;
    }

    public void setEpisodio(int episodio) {
        this.episodio = episodio;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public char getSerie() {
        return serie;
    }

    public void setSerie(char serie) {
        this.serie = serie;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
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
        if (!(object instanceof Diariocaja)) {
            return false;
        }
        Diariocaja other = (Diariocaja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Diariocaja[id=" + id + "]";
    }

}
