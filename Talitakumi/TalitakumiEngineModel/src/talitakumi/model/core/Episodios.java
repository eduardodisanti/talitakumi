/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "episodios")
@NamedQueries({
    @NamedQuery(name = "Episodios.findAll", query = "SELECT e FROM Episodios e"),
    @NamedQuery(name = "Episodios.findById", query = "SELECT e FROM Episodios e WHERE e.id = :id"),
    @NamedQuery(name = "Episodios.findByFecha", query = "SELECT e FROM Episodios e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "Episodios.findByTerminado", query = "SELECT e FROM Episodios e WHERE e.terminado = :terminado"),
    @NamedQuery(name = "Episodios.findByCliente", query = "SELECT e FROM Episodios e WHERE e.cliente = :cliente"),
    @NamedQuery(name = "Episodios.findByGeoX", query = "SELECT e FROM Episodios e WHERE e.geoX = :geoX"),
    @NamedQuery(name = "Episodios.findByGeoY", query = "SELECT e FROM Episodios e WHERE e.geoY = :geoY")})
public class Episodios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)

    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "terminado")
    private char terminado;
    @Basic(optional = false)
    @Column(name = "cliente")
    private int cliente;
    @Lob
    @Column(name = "imghistoria")
    private byte[] imghistoria;
    @Column(name = "geoX")
    private Float geoX;
    @Column(name = "geoY")
    private Float geoY;
    @Lob
    @Column(name = "comentarios")
    private String comentarios;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Episodiointegrideadme episodiointegrideadme;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodio")
    private Collection<Estudioshechos> estudioshechosCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Episodioagudezasauditivas episodioagudezasauditivas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Collection<Episodioodontologo> episodioodontologoCollection;
    @JoinColumn(name = "tipoepisodio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Actividades tipoepisodio;
    @JoinColumn(name = "medico", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Medicos medico;
    @JoinColumn(name = "paciente", referencedColumnName = "id")
    @ManyToOne
    private Pacientes paciente;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Episodioextraccionista episodioextraccionista;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Carnetdesalud carnetdesalud;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Episodioagudezasvisuales episodioagudezasvisuales;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodio")
    private Chequeos chequeos;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Libretasdeconducir libretasdeconducir;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Psicotecnicos psicotecnicos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Collection<Episodiosvacunas> episodiosvacunasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Collection<Episodioexamenfisico> episodioexamenfisicoCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Timming timming;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Episodioexameneneuro episodioexameneneuro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Collection<Episodiosvicios> episodiosviciosCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Episodiopresion episodiopresion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodio")
    private Collection<EpisodiosExAdicionales> episodiosExAdicionalesCollection;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodio")
//    private Collection<Facturas> facturasCollection;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodio")
    private Facturas facturas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Collection<Episodiosestudios> episodiosestudiosCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Episodioactitudes episodioactitudes;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Serviciovacunas serviciovacunas;
    @OneToMany(mappedBy = "episodio")
    private Collection<Agenda> agendaCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Otrosservicios otrosservicios;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Libretaprofesional libretaprofesional;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Collection<Episodiosmedicamentos> episodiosmedicamentosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Collection<Episodiosantecedentes> episodiosantecedentesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodios")
    private Collection<Episodiosenfermedades> episodiosenfermedadesCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "episodios1")
    private Servicioexparaclin servicioexparaclin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episodio")
    private Collection<EpisodiosVacunasAdicionales> episodiosVacunasAdicionalesCollection;

    public Episodios() {
    }

    public Episodios(Integer id) {
        this.id = id;
    }

    public Episodios(Integer id, Date fecha, char terminado, int cliente) {
        this.id = id;
        this.fecha = fecha;
        this.terminado = terminado;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public char getTerminado() {
        return terminado;
    }

    public void setTerminado(char terminado) {
        this.terminado = terminado;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public byte[] getImghistoria() {
        return imghistoria;
    }

    public void setImghistoria(byte[] imghistoria) {
        this.imghistoria = imghistoria;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Episodiointegrideadme getEpisodiointegrideadme() {
        return episodiointegrideadme;
    }

    public void setEpisodiointegrideadme(Episodiointegrideadme episodiointegrideadme) {
        this.episodiointegrideadme = episodiointegrideadme;
    }

    public Collection<Estudioshechos> getEstudioshechosCollection() {
        return estudioshechosCollection;
    }

    public void setEstudioshechosCollection(Collection<Estudioshechos> estudioshechosCollection) {
        this.estudioshechosCollection = estudioshechosCollection;
    }

    public Episodioagudezasauditivas getEpisodioagudezasauditivas() {
        return episodioagudezasauditivas;
    }

    public void setEpisodioagudezasauditivas(Episodioagudezasauditivas episodioagudezasauditivas) {
        this.episodioagudezasauditivas = episodioagudezasauditivas;
    }

    public Collection<Episodioodontologo> getEpisodioodontologoCollection() {
        return episodioodontologoCollection;
    }

    public void setEpisodioodontologoCollection(Collection<Episodioodontologo> episodioodontologoCollection) {
        this.episodioodontologoCollection = episodioodontologoCollection;
    }
    
    public Actividades getTipoepisodio() {
        return tipoepisodio;
    }

    public void setTipoepisodio(Actividades tipoepisodio) {
        this.tipoepisodio = tipoepisodio;
    }

    public Medicos getMedico() {
        return medico;
    }

    public void setMedico(Medicos medico) {
        this.medico = medico;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public Episodioextraccionista getEpisodioextraccionista() {
        return episodioextraccionista;
    }

    public void setEpisodioextraccionista(Episodioextraccionista episodioextraccionista) {
        this.episodioextraccionista = episodioextraccionista;
    }

    public Carnetdesalud getCarnetdesalud() {
        return carnetdesalud;
    }

    public void setCarnetdesalud(Carnetdesalud carnetdesalud) {
        this.carnetdesalud = carnetdesalud;
    }

    public Episodioagudezasvisuales getEpisodioagudezasvisuales() {
        return episodioagudezasvisuales;
    }

    public void setEpisodioagudezasvisuales(Episodioagudezasvisuales episodioagudezasvisuales) {
        this.episodioagudezasvisuales = episodioagudezasvisuales;
    }

    public Chequeos getChequeos() {
        return chequeos;
    }

    public void setChequeos(Chequeos chequeos) {
        this.chequeos = chequeos;
    }

    public Libretasdeconducir getLibretasdeconducir() {
        return libretasdeconducir;
    }

    public void setLibretasdeconducir(Libretasdeconducir libretasdeconducir) {
        this.libretasdeconducir = libretasdeconducir;
    }

    public Psicotecnicos getPsicotecnicos() {
        return psicotecnicos;
    }

    public void setPsicotecnicos(Psicotecnicos psicotecnicos) {
        this.psicotecnicos = psicotecnicos;
    }

    public Collection<Episodiosvacunas> getEpisodiosvacunasCollection() {
        return episodiosvacunasCollection;
    }

    public void setEpisodiosvacunasCollection(Collection<Episodiosvacunas> episodiosvacunasCollection) {
        this.episodiosvacunasCollection = episodiosvacunasCollection;
    }

    public Collection<Episodioexamenfisico> getEpisodioexamenfisicoCollection() {
        return episodioexamenfisicoCollection;
    }

    public void setEpisodioexamenfisicoCollection(Collection<Episodioexamenfisico> episodioexamenfisicoCollection) {
        this.episodioexamenfisicoCollection = episodioexamenfisicoCollection;
    }

    public Timming getTimming() {
        return timming;
    }

    public void setTimming(Timming timming) {
        this.timming = timming;
    }

    public Episodioexameneneuro getEpisodioexameneneuro() {
        return episodioexameneneuro;
    }

    public void setEpisodioexameneneuro(Episodioexameneneuro episodioexameneneuro) {
        this.episodioexameneneuro = episodioexameneneuro;
    }

    public Collection<Episodiosvicios> getEpisodiosviciosCollection() {
        return episodiosviciosCollection;
    }

    public void setEpisodiosviciosCollection(Collection<Episodiosvicios> episodiosviciosCollection) {
        this.episodiosviciosCollection = episodiosviciosCollection;
    }

    public Episodiopresion getEpisodiopresion() {
        return episodiopresion;
    }

    public void setEpisodiopresion(Episodiopresion episodiopresion) {
        this.episodiopresion = episodiopresion;
    }

    public Collection<EpisodiosExAdicionales> getEpisodiosExAdicionalesCollection() {
        return episodiosExAdicionalesCollection;
    }

    public void setEpisodiosExAdicionalesCollection(Collection<EpisodiosExAdicionales> episodiosExAdicionalesCollection) {
        this.episodiosExAdicionalesCollection = episodiosExAdicionalesCollection;
    }

//    public Collection<Facturas> getFacturasCollection() {
//        return facturasCollection;
//    }
//
//    public void setFacturasCollection(Collection<Facturas> facturasCollection) {
//        this.facturasCollection = facturasCollection;
//    }

    public Collection<Episodiosestudios> getEpisodiosestudiosCollection() {
        return episodiosestudiosCollection;
    }

    public void setEpisodiosestudiosCollection(Collection<Episodiosestudios> episodiosestudiosCollection) {
        this.episodiosestudiosCollection = episodiosestudiosCollection;
    }

    public Episodioactitudes getEpisodioactitudes() {
        return episodioactitudes;
    }

    public void setEpisodioactitudes(Episodioactitudes episodioactitudes) {
        this.episodioactitudes = episodioactitudes;
    }

    public Serviciovacunas getServiciovacunas() {
        return serviciovacunas;
    }

    public void setServiciovacunas(Serviciovacunas serviciovacunas) {
        this.serviciovacunas = serviciovacunas;
    }

    public Collection<Agenda> getAgendaCollection() {
        return agendaCollection;
    }

    public void setAgendaCollection(Collection<Agenda> agendaCollection) {
        this.agendaCollection = agendaCollection;
    }

    public Otrosservicios getOtrosservicios() {
        return otrosservicios;
    }

    public void setOtrosservicios(Otrosservicios otrosservicios) {
        this.otrosservicios = otrosservicios;
    }

    public Libretaprofesional getLibretaprofesional() {
        return libretaprofesional;
    }

    public void setLibretaprofesional(Libretaprofesional libretaprofesional) {
        this.libretaprofesional = libretaprofesional;
    }

    public Collection<Episodiosmedicamentos> getEpisodiosmedicamentosCollection() {
        return episodiosmedicamentosCollection;
    }

    public void setEpisodiosmedicamentosCollection(Collection<Episodiosmedicamentos> episodiosmedicamentosCollection) {
        this.episodiosmedicamentosCollection = episodiosmedicamentosCollection;
    }

    public Collection<Episodiosantecedentes> getEpisodiosantecedentesCollection() {
        return episodiosantecedentesCollection;
    }

    public void setEpisodiosantecedentesCollection(Collection<Episodiosantecedentes> episodiosantecedentesCollection) {
        this.episodiosantecedentesCollection = episodiosantecedentesCollection;
    }

    public Collection<Episodiosenfermedades> getEpisodiosenfermedadesCollection() {
        return episodiosenfermedadesCollection;
    }

    public void setEpisodiosenfermedadesCollection(Collection<Episodiosenfermedades> episodiosenfermedadesCollection) {
        this.episodiosenfermedadesCollection = episodiosenfermedadesCollection;
    }

    public Servicioexparaclin getServicioexparaclin() {
        return servicioexparaclin;
    }

    public void setServicioexparaclin(Servicioexparaclin servicioexparaclin) {
        this.servicioexparaclin = servicioexparaclin;
    }

    public Collection<EpisodiosVacunasAdicionales> getEpisodiosVacunasAdicionalesCollection() {
        return episodiosVacunasAdicionalesCollection;
    }

    public void setEpisodiosVacunasAdicionalesCollection(Collection<EpisodiosVacunasAdicionales> episodiosVacunasAdicionalesCollection) {
        this.episodiosVacunasAdicionalesCollection = episodiosVacunasAdicionalesCollection;
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
        if (!(object instanceof Episodios)) {
            return false;
        }
        Episodios other = (Episodios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodios[id=" + id + "]";
    }

    /**
     * @return the facturas
     */
    public Facturas getFacturas() {
        return facturas;
    }

    /**
     * @param facturas the facturas to set
     */
    public void setFacturas(Facturas facturas) {
        this.facturas = facturas;
    }


}
