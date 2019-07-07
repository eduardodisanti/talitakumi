/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.admin;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author eduardodisanti
 */
@Entity
@Table(name = "consultoriosactivos", catalog = "talitakumi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Consultoriosactivos.findAll", query = "SELECT c FROM Consultoriosactivos c"),
    @NamedQuery(name = "Consultoriosactivos.findByConsultorio", query = "SELECT c FROM Consultoriosactivos c WHERE c.consultorio = :consultorio"),
    @NamedQuery(name = "Consultoriosactivos.findByActivo", query = "SELECT c FROM Consultoriosactivos c WHERE c.activo = :activo")})
public class Consultoriosactivos implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "consultorio")
    private Integer consultorio;
    @Basic(optional = false)
    @Column(name = "activo")
    private char activo;

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
        Integer oldConsultorio = this.consultorio;
        this.consultorio = consultorio;
        changeSupport.firePropertyChange("consultorio", oldConsultorio, consultorio);
    }

    public char getActivo() {
        return activo;
    }

    public void setActivo(char activo) {
        char oldActivo = this.activo;
        this.activo = activo;
        changeSupport.firePropertyChange("activo", oldActivo, activo);
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
        return "talitakumi.admin.Consultoriosactivos[consultorio=" + consultorio + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
