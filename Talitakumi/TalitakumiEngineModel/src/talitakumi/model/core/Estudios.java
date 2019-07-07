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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "estudios")
@NamedQueries({
    @NamedQuery(name = "Estudios.findAll", query = "SELECT e FROM Estudios e"),
    @NamedQuery(name = "Estudios.findById", query = "SELECT e FROM Estudios e WHERE e.id = :id"),
    @NamedQuery(name = "Estudios.findByDescripcion", query = "SELECT e FROM Estudios e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Estudios.findByTagEstudio", query = "SELECT e FROM Estudios e WHERE e.tagEstudio = :tagEstudio"),
    @NamedQuery(name = "Estudios.findByPreciobase", query = "SELECT e FROM Estudios e WHERE e.preciobase = :preciobase")})
public class Estudios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "tag_estudio")
    private String tagEstudio;
    @Basic(optional = false)
    @Column(name = "preciobase")
    private float preciobase;
    @Basic(optional = false)
    @Column(name = "laboratorio")
    private String laboratorio;
    @Basic(optional = false)
    @Column(name = "medico")
    private String medico;

    @ManyToMany(mappedBy = "estudiosCollection")
    private Collection<Analisis> analisisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudio")
    private Collection<Estudioshechos> estudioshechosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudio")
    private Collection<EpisodiosExAdicionales> episodiosExAdicionalesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudios")
    private Collection<Episodiosestudios> episodiosestudiosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examen")
    private Collection<Examenesactividad> examenesactividadCollection;

    public Estudios() {
    }

    public Estudios(Integer id) {
        this.id = id;
    }

    public Estudios(Integer id, String descripcion, String tagEstudio, float preciobase) {
        this.id = id;
        this.descripcion = descripcion;
        this.tagEstudio = tagEstudio;
        this.preciobase = preciobase;
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

    public String getTagEstudio() {
        return tagEstudio;
    }

    public void setTagEstudio(String tagEstudio) {
        this.tagEstudio = tagEstudio;
    }

    public float getPreciobase() {
        return preciobase;
    }

    public void setPreciobase(float preciobase) {
        this.preciobase = preciobase;
    }

    public Collection<Analisis> getAnalisisCollection() {
        return analisisCollection;
    }

    public void setAnalisisCollection(Collection<Analisis> analisisCollection) {
        this.analisisCollection = analisisCollection;
    }

    public Collection<Estudioshechos> getEstudioshechosCollection() {
        return estudioshechosCollection;
    }

    public void setEstudioshechosCollection(Collection<Estudioshechos> estudioshechosCollection) {
        this.estudioshechosCollection = estudioshechosCollection;
    }

    public Collection<EpisodiosExAdicionales> getEpisodiosExAdicionalesCollection() {
        return episodiosExAdicionalesCollection;
    }

    public void setEpisodiosExAdicionalesCollection(Collection<EpisodiosExAdicionales> episodiosExAdicionalesCollection) {
        this.episodiosExAdicionalesCollection = episodiosExAdicionalesCollection;
    }

    public Collection<Episodiosestudios> getEpisodiosestudiosCollection() {
        return episodiosestudiosCollection;
    }

    public void setEpisodiosestudiosCollection(Collection<Episodiosestudios> episodiosestudiosCollection) {
        this.episodiosestudiosCollection = episodiosestudiosCollection;
    }

    public Collection<Examenesactividad> getExamenesactividadCollection() {
        return examenesactividadCollection;
    }

    public void setExamenesactividadCollection(Collection<Examenesactividad> examenesactividadCollection) {
        this.examenesactividadCollection = examenesactividadCollection;
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
        if (!(object instanceof Estudios)) {
            return false;
        }
        Estudios other = (Estudios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Estudios[id=" + id + "]";
    }

    /**
     * @return the laboratorio
     */
    public String getLaboratorio() {
        return laboratorio;
    }

    /**
     * @param laboratorio the laboratorio to set
     */
    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * @return the medico
     */
    public String getMedico() {
        return medico;
    }

    /**
     * @param medico the medico to set
     */
    public void setMedico(String medico) {
        this.medico = medico;
    }

}
