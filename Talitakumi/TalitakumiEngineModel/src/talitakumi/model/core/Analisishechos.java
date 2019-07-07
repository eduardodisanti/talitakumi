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
@Table(name = "analisishechos")
@NamedQueries({
    @NamedQuery(name = "Analisishechos.findAll", query = "SELECT a FROM Analisishechos a"),
    @NamedQuery(name = "Analisishechos.findById", query = "SELECT a FROM Analisishechos a WHERE a.id = :id"),
    @NamedQuery(name = "Analisishechos.findByValorhallado", query = "SELECT a FROM Analisishechos a WHERE a.valorhallado = :valorhallado"),
    @NamedQuery(name = "Analisishechos.findByEnsuma", query = "SELECT a FROM Analisishechos a WHERE a.ensuma = :ensuma")})
public class Analisishechos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "valorhallado")
    private String valorhallado;
    @Column(name = "ensuma")
    private String ensuma;
    @JoinColumn(name = "validado", referencedColumnName = "codigo")
    @ManyToOne
    private Medicos validado;
    @JoinColumn(name = "estudiohecho", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estudioshechos estudiohecho;
    @JoinColumn(name = "analisis", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Analisis analisis;

    public Analisishechos() {
    }

    public Analisishechos(Integer id) {
        this.id = id;
    }

    public Analisishechos(Integer id, String valorhallado) {
        this.id = id;
        this.valorhallado = valorhallado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValorhallado() {
        return valorhallado;
    }

    public void setValorhallado(String valorhallado) {
        this.valorhallado = valorhallado;
    }

    public String getEnsuma() {
        return ensuma;
    }

    public void setEnsuma(String ensuma) {
        this.ensuma = ensuma;
    }

    public Medicos getValidado() {
        return validado;
    }

    public void setValidado(Medicos validado) {
        this.validado = validado;
    }

    public Estudioshechos getEstudiohecho() {
        return estudiohecho;
    }

    public void setEstudiohecho(Estudioshechos estudiohecho) {
        this.estudiohecho = estudiohecho;
    }

    public Analisis getAnalisis() {
        return analisis;
    }

    public void setAnalisis(Analisis analisis) {
        this.analisis = analisis;
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
        if (!(object instanceof Analisishechos)) {
            return false;
        }
        Analisishechos other = (Analisishechos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Analisishechos[id=" + id + "]";
    }

}
