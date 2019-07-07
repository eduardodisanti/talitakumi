/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "personas")
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
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "personas")
    private Pacientes pacientes;

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
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Float getGeoX() {
        return geoX;
    }

    public void setGeoX(Float geoX) {
        this.geoX = geoX;
    }

    public Float getGeoY() {
        return geoY;
    }

    public void setGeoY(Float geoY) {
        this.geoY = geoY;
    }

    public Pacientes getPacientes() {
        return pacientes;
    }

    public void setPacientes(Pacientes pacientes) {
        this.pacientes = pacientes;
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
        return "talitakumi.model.core.Personas[id=" + id + "]";
    }

}
