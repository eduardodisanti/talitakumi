/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.admin;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author eduardodisanti
 */
@Entity
@Table(name = "personas", catalog = "talitakumi", schema = "")
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findById", query = "SELECT p FROM Personas p WHERE p.id = :id"),
    @NamedQuery(name = "Personas.findByNombres", query = "SELECT p FROM Personas p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Personas.findByApellidos", query = "SELECT p FROM Personas p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Personas.findByTelefono", query = "SELECT p FROM Personas p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Personas.findByEmail", query = "SELECT p FROM Personas p WHERE p.email = :email"),
    @NamedQuery(name = "Personas.findByFechanacimiento", query = "SELECT p FROM Personas p WHERE p.fechanacimiento = :fechanacimiento"),
    @NamedQuery(name = "Personas.findByDomicilio", query = "SELECT p FROM Personas p WHERE p.domicilio = :domicilio"),
    @NamedQuery(name = "Personas.findByDocumento", query = "SELECT p FROM Personas p WHERE p.documento = :documento"),
    @NamedQuery(name = "Personas.findByCiudad", query = "SELECT p FROM Personas p WHERE p.ciudad = :ciudad"),
    @NamedQuery(name = "Personas.findByMovil", query = "SELECT p FROM Personas p WHERE p.movil = :movil"),
    @NamedQuery(name = "Personas.findBySexo", query = "SELECT p FROM Personas p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "Personas.findByGeoX", query = "SELECT p FROM Personas p WHERE p.geoX = :geoX"),
    @NamedQuery(name = "Personas.findByGeoY", query = "SELECT p FROM Personas p WHERE p.geoY = :geoY")})
public class Personas implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Column(name = "domicilio")
    private String domicilio;
    @Basic(optional = false)
    @Column(name = "documento")
    private int documento;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "movil")
    private String movil;
    @Basic(optional = false)
    @Column(name = "sexo")
    private char sexo;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Column(name = "geoX")
    private Float geoX;
    @Column(name = "geoY")
    private Float geoY;

    public Personas() {
    }

    public Personas(Integer id) {
        this.id = id;
    }

    public Personas(Integer id, String nombres, String apellidos, Date fechanacimiento, int documento, char sexo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechanacimiento = fechanacimiento;
        this.documento = documento;
        this.sexo = sexo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        String oldNombres = this.nombres;
        this.nombres = nombres;
        changeSupport.firePropertyChange("nombres", oldNombres, nombres);
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        String oldApellidos = this.apellidos;
        this.apellidos = apellidos;
        changeSupport.firePropertyChange("apellidos", oldApellidos, apellidos);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        String oldTelefono = this.telefono;
        this.telefono = telefono;
        changeSupport.firePropertyChange("telefono", oldTelefono, telefono);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        changeSupport.firePropertyChange("email", oldEmail, email);
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        Date oldFechanacimiento = this.fechanacimiento;
        this.fechanacimiento = fechanacimiento;
        changeSupport.firePropertyChange("fechanacimiento", oldFechanacimiento, fechanacimiento);
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        String oldDomicilio = this.domicilio;
        this.domicilio = domicilio;
        changeSupport.firePropertyChange("domicilio", oldDomicilio, domicilio);
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        int oldDocumento = this.documento;
        this.documento = documento;
        changeSupport.firePropertyChange("documento", oldDocumento, documento);
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        String oldCiudad = this.ciudad;
        this.ciudad = ciudad;
        changeSupport.firePropertyChange("ciudad", oldCiudad, ciudad);
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        String oldMovil = this.movil;
        this.movil = movil;
        changeSupport.firePropertyChange("movil", oldMovil, movil);
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        char oldSexo = this.sexo;
        this.sexo = sexo;
        changeSupport.firePropertyChange("sexo", oldSexo, sexo);
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        byte[] oldFoto = this.foto;
        this.foto = foto;
        changeSupport.firePropertyChange("foto", oldFoto, foto);
    }

    public Float getGeoX() {
        return geoX;
    }

    public void setGeoX(Float geoX) {
        Float oldGeoX = this.geoX;
        this.geoX = geoX;
        changeSupport.firePropertyChange("geoX", oldGeoX, geoX);
    }

    public Float getGeoY() {
        return geoY;
    }

    public void setGeoY(Float geoY) {
        Float oldGeoY = this.geoY;
        this.geoY = geoY;
        changeSupport.firePropertyChange("geoY", oldGeoY, geoY);
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
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.admin.Personas[id=" + id + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
