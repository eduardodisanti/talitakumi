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
@Table(name = "medicos")
@NamedQueries({
    @NamedQuery(name = "Medicos.findAll", query = "SELECT m FROM Medicos m"),
    @NamedQuery(name = "Medicos.findByCodigo", query = "SELECT m FROM Medicos m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "Medicos.findByNombre", query = "SELECT m FROM Medicos m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Medicos.findByEspecialidad", query = "SELECT m FROM Medicos m WHERE m.especialidad = :especialidad"),
    @NamedQuery(name = "Medicos.findByActivo", query = "SELECT m FROM Medicos m WHERE m.activo = :activo"),
    @NamedQuery(name = "Medicos.findByExterno", query = "SELECT m FROM Medicos m WHERE m.externo = :externo"),
    @NamedQuery(name = "Medicos.findByCedula", query = "SELECT m FROM Medicos m WHERE m.cedula = :cedula")})
public class Medicos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "especialidad")
    private int especialidad;
    @Basic(optional = false)
    @Column(name = "activo")
    private char activo;
    @Column(name = "externo")
    private Character externo;
    @Basic(optional = false)
    @Column(name = "cedula")
    private int cedula;
    @OneToMany(mappedBy = "medico")
    private Collection<Episodioodontologo> episodioodontologoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medico")
    private Collection<Episodios> episodiosCollection;
    @OneToMany(mappedBy = "medico")
    private Collection<Agenda> agendaCollection;
    @OneToMany(mappedBy = "validado")
    private Collection<Analisishechos> analisishechosCollection;

    public Medicos() {
    }

    public Medicos(Integer codigo) {
        this.codigo = codigo;
    }

    public Medicos(Integer codigo, String nombre, int especialidad, char activo, int cedula) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.activo = activo;
        this.cedula = cedula;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(int especialidad) {
        this.especialidad = especialidad;
    }

    public char getActivo() {
        return activo;
    }

    public void setActivo(char activo) {
        this.activo = activo;
    }

    public Character getExterno() {
        return externo;
    }

    public void setExterno(Character externo) {
        this.externo = externo;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public Collection<Episodioodontologo> getEpisodioodontologoCollection() {
        return episodioodontologoCollection;
    }

    public void setEpisodioodontologoCollection(Collection<Episodioodontologo> episodioodontologoCollection) {
        this.episodioodontologoCollection = episodioodontologoCollection;
    }

    public Collection<Episodios> getEpisodiosCollection() {
        return episodiosCollection;
    }

    public void setEpisodiosCollection(Collection<Episodios> episodiosCollection) {
        this.episodiosCollection = episodiosCollection;
    }

    public Collection<Agenda> getAgendaCollection() {
        return agendaCollection;
    }

    public void setAgendaCollection(Collection<Agenda> agendaCollection) {
        this.agendaCollection = agendaCollection;
    }

    public Collection<Analisishechos> getAnalisishechosCollection() {
        return analisishechosCollection;
    }

    public void setAnalisishechosCollection(Collection<Analisishechos> analisishechosCollection) {
        this.analisishechosCollection = analisishechosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medicos)) {
            return false;
        }
        Medicos other = (Medicos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Medicos[codigo=" + codigo + "]";
    }

}
