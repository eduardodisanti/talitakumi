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
@Table(name = "usuarios", catalog = "talitakumi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByUsuario", query = "SELECT u FROM Usuarios u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuarios.findByClave", query = "SELECT u FROM Usuarios u WHERE u.clave = :clave"),
    @NamedQuery(name = "Usuarios.findByEmail", query = "SELECT u FROM Usuarios u WHERE u.email = :email"),
    @NamedQuery(name = "Usuarios.findByFuncionario", query = "SELECT u FROM Usuarios u WHERE u.funcionario = :funcionario"),
    @NamedQuery(name = "Usuarios.findByCargo", query = "SELECT u FROM Usuarios u WHERE u.cargo = :cargo"),
    @NamedQuery(name = "Usuarios.findByNivel", query = "SELECT u FROM Usuarios u WHERE u.nivel = :nivel"),
    @NamedQuery(name = "Usuarios.findByMedico", query = "SELECT u FROM Usuarios u WHERE u.medico = :medico"),
    @NamedQuery(name = "Usuarios.findByRol", query = "SELECT u FROM Usuarios u WHERE u.rol = :rol")})
public class Usuarios implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "clave")
    private String clave;
    @Column(name = "email")
    private String email;
    @Id
    @Basic(optional = false)
    @Column(name = "funcionario")
    private Integer funcionario;
    @Column(name = "cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "nivel")
    private int nivel;
    @Basic(optional = false)
    @Column(name = "medico")
    private int medico;
    @Column(name = "rol")
    private String rol;

    public Usuarios() {
    }

    public Usuarios(Integer funcionario) {
        this.funcionario = funcionario;
    }

    public Usuarios(Integer funcionario, String usuario, String clave, int nivel, int medico) {
        this.funcionario = funcionario;
        this.usuario = usuario;
        this.clave = clave;
        this.nivel = nivel;
        this.medico = medico;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        String oldUsuario = this.usuario;
        this.usuario = usuario;
        changeSupport.firePropertyChange("usuario", oldUsuario, usuario);
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        String oldClave = this.clave;
        this.clave = clave;
        changeSupport.firePropertyChange("clave", oldClave, clave);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        changeSupport.firePropertyChange("email", oldEmail, email);
    }

    public Integer getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Integer funcionario) {
        Integer oldFuncionario = this.funcionario;
        this.funcionario = funcionario;
        changeSupport.firePropertyChange("funcionario", oldFuncionario, funcionario);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        String oldCargo = this.cargo;
        this.cargo = cargo;
        changeSupport.firePropertyChange("cargo", oldCargo, cargo);
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        int oldNivel = this.nivel;
        this.nivel = nivel;
        changeSupport.firePropertyChange("nivel", oldNivel, nivel);
    }

    public int getMedico() {
        return medico;
    }

    public void setMedico(int medico) {
        int oldMedico = this.medico;
        this.medico = medico;
        changeSupport.firePropertyChange("medico", oldMedico, medico);
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        String oldRol = this.rol;
        this.rol = rol;
        changeSupport.firePropertyChange("rol", oldRol, rol);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funcionario != null ? funcionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.funcionario == null && other.funcionario != null) || (this.funcionario != null && !this.funcionario.equals(other.funcionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.admin.Usuarios[funcionario=" + funcionario + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
