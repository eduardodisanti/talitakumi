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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "mensajes")
@NamedQueries({
    @NamedQuery(name = "Mensajes.findAll", query = "SELECT m FROM Mensajes m"),
    @NamedQuery(name = "Mensajes.findById", query = "SELECT m FROM Mensajes m WHERE m.id = :id"),
    @NamedQuery(name = "Mensajes.findByDe", query = "SELECT m FROM Mensajes m WHERE m.de = :de"),
    @NamedQuery(name = "Mensajes.findByPara", query = "SELECT m FROM Mensajes m WHERE m.para = :para"),
    @NamedQuery(name = "Mensajes.findByLeido", query = "SELECT m FROM Mensajes m WHERE m.leido = :leido")})
public class Mensajes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "de")
    private String de;
    @Basic(optional = false)
    @Column(name = "para")
    private String para;
    @Basic(optional = false)
    @Lob
    @Column(name = "texto")
    private String texto;
    @Basic(optional = false)
    @Column(name = "leido")
    private char leido;

    public Mensajes() {
    }

    public Mensajes(Integer id) {
        this.id = id;
    }

    public Mensajes(Integer id, String de, String para, String texto, char leido) {
        this.id = id;
        this.de = de;
        this.para = para;
        this.texto = texto;
        this.leido = leido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public char getLeido() {
        return leido;
    }

    public void setLeido(char leido) {
        this.leido = leido;
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
        if (!(object instanceof Mensajes)) {
            return false;
        }
        Mensajes other = (Mensajes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Mensajes[id=" + id + "]";
    }

}
