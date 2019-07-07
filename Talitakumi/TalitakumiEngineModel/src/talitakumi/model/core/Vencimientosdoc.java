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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "vencimientosdoc")
@NamedQueries({
    @NamedQuery(name = "Vencimientosdoc.findAll", query = "SELECT v FROM Vencimientosdoc v"),
    @NamedQuery(name = "Vencimientosdoc.findById", query = "SELECT v FROM Vencimientosdoc v WHERE v.id = :id"),
    @NamedQuery(name = "Vencimientosdoc.findByDocumento", query = "SELECT v FROM Vencimientosdoc v WHERE v.documento = :documento"),
    @NamedQuery(name = "Vencimientosdoc.findByEmitido", query = "SELECT v FROM Vencimientosdoc v WHERE v.emitido = :emitido"),
    @NamedQuery(name = "Vencimientosdoc.findByVence", query = "SELECT v FROM Vencimientosdoc v WHERE v.vence = :vence")})
public class Vencimientosdoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "documento")
    private int documento;
    @Column(name = "emitido")
    @Temporal(TemporalType.DATE)
    private Date emitido;
    @Column(name = "vence")
    @Temporal(TemporalType.DATE)
    private Date vence;
    @JoinColumn(name = "paciente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pacientes paciente;

    public Vencimientosdoc() {
    }

    public Vencimientosdoc(Integer id) {
        this.id = id;
    }

    public Vencimientosdoc(Integer id, int documento) {
        this.id = id;
        this.documento = documento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public Date getEmitido() {
        return emitido;
    }

    public void setEmitido(Date emitido) {
        this.emitido = emitido;
    }

    public Date getVence() {
        return vence;
    }

    public void setVence(Date vence) {
        this.vence = vence;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
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
        if (!(object instanceof Vencimientosdoc)) {
            return false;
        }
        Vencimientosdoc other = (Vencimientosdoc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Vencimientosdoc[id=" + id + "]";
    }

}
