/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "procedencias")
@NamedQueries({
    @NamedQuery(name = "Procedencias.findAll", query = "SELECT p FROM Procedencias p"),
    @NamedQuery(name = "Procedencias.findById", query = "SELECT p FROM Procedencias p WHERE p.id = :id"),
    @NamedQuery(name = "Procedencias.findByDescripcion", query = "SELECT p FROM Procedencias p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Procedencias.findByHabilitado", query = "SELECT p FROM Procedencias p WHERE p.habilitado = :habilitado")})
public class Procedencias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "habilitado")
    private char habilitado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedencia")
    private Collection<Psicotecnicos> psicotecnicosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedencia")
    private Collection<Serviciovacunas> serviciovacunasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedencia")
    private Collection<Otrosservicios> otrosserviciosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedencia")
    private Collection<Libretaprofesional> libretaprofesionalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedencia")
    private Collection<Servicioexparaclin> servicioexparaclinCollection;

    public Procedencias() {
    }

    public Procedencias(Integer id) {
        this.id = id;
    }

    public Procedencias(Integer id, char habilitado) {
        this.id = id;
        this.habilitado = habilitado;
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

    public char getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(char habilitado) {
        this.habilitado = habilitado;
    }

    public Collection<Psicotecnicos> getPsicotecnicosCollection() {
        return psicotecnicosCollection;
    }

    public void setPsicotecnicosCollection(Collection<Psicotecnicos> psicotecnicosCollection) {
        this.psicotecnicosCollection = psicotecnicosCollection;
    }

    public Collection<Serviciovacunas> getServiciovacunasCollection() {
        return serviciovacunasCollection;
    }

    public void setServiciovacunasCollection(Collection<Serviciovacunas> serviciovacunasCollection) {
        this.serviciovacunasCollection = serviciovacunasCollection;
    }

    public Collection<Otrosservicios> getOtrosserviciosCollection() {
        return otrosserviciosCollection;
    }

    public void setOtrosserviciosCollection(Collection<Otrosservicios> otrosserviciosCollection) {
        this.otrosserviciosCollection = otrosserviciosCollection;
    }

    public Collection<Libretaprofesional> getLibretaprofesionalCollection() {
        return libretaprofesionalCollection;
    }

    public void setLibretaprofesionalCollection(Collection<Libretaprofesional> libretaprofesionalCollection) {
        this.libretaprofesionalCollection = libretaprofesionalCollection;
    }

    public Collection<Servicioexparaclin> getServicioexparaclinCollection() {
        return servicioexparaclinCollection;
    }

    public void setServicioexparaclinCollection(Collection<Servicioexparaclin> servicioexparaclinCollection) {
        this.servicioexparaclinCollection = servicioexparaclinCollection;
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
        if (!(object instanceof Procedencias)) {
            return false;
        }
        Procedencias other = (Procedencias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Procedencias[id=" + id + "]";
    }

}
