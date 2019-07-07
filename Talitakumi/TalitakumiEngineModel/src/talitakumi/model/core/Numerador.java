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
@Table(name = "numerador")
@NamedQueries({
    @NamedQuery(name = "Numerador.findAll", query = "SELECT n FROM Numerador n"),
    @NamedQuery(name = "Numerador.findById", query = "SELECT n FROM Numerador n WHERE n.id = :id"),
    @NamedQuery(name = "Numerador.findByDescripcion", query = "SELECT n FROM Numerador n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "Numerador.findByValor", query = "SELECT n FROM Numerador n WHERE n.valor = :valor")})
public class Numerador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "valor")
    private int valor;

    public Numerador() {
    }

    public Numerador(Integer id) {
        this.id = id;
    }

    public Numerador(Integer id, String descripcion, int valor) {
        this.id = id;
        this.descripcion = descripcion;
        this.valor = valor;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
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
        if (!(object instanceof Numerador)) {
            return false;
        }
        Numerador other = (Numerador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Numerador[id=" + id + "]";
    }

}
