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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "consultoriosactivos")
@NamedQueries({
    @NamedQuery(name = "Consultoriosactivos.findAll", query = "SELECT c FROM Consultoriosactivos c"),
    @NamedQuery(name = "Consultoriosactivos.findByConsultorio", query = "SELECT c FROM Consultoriosactivos c WHERE c.consultorio = :consultorio"),
    @NamedQuery(name = "Consultoriosactivos.findByActivo", query = "SELECT c FROM Consultoriosactivos c WHERE c.activo = :activo")})
public class Consultoriosactivos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "consultorio")
    private Integer consultorio;
    @Basic(optional = false)
    @Column(name = "activo")
    private char activo;
    @JoinColumn(name = "consultorio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Consultorios consultorios;

    public Consultoriosactivos() {
    }

    public Consultoriosactivos(Integer consultorio) {
        this.consultorio = consultorio;
    }

    public Consultoriosactivos(Integer consultorio, char activo) {
        this.consultorio = consultorio;
        this.activo = activo;
    }

    public Integer getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Integer consultorio) {
        this.consultorio = consultorio;
    }

    public char getActivo() {
        return activo;
    }

    public void setActivo(char activo) {
        this.activo = activo;
    }

    public Consultorios getConsultorios() {
        return consultorios;
    }

    public void setConsultorios(Consultorios consultorios) {
        this.consultorios = consultorios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultorio != null ? consultorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consultoriosactivos)) {
            return false;
        }
        Consultoriosactivos other = (Consultoriosactivos) object;
        if ((this.consultorio == null && other.consultorio != null) || (this.consultorio != null && !this.consultorio.equals(other.consultorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Consultoriosactivos[consultorio=" + consultorio + "]";
    }

}
