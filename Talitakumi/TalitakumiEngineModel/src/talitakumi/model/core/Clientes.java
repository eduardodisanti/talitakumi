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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "clientes")
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findById", query = "SELECT c FROM Clientes c WHERE c.id = :id"),
    @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Clientes.findByRut", query = "SELECT c FROM Clientes c WHERE c.rut = :rut"),
    @NamedQuery(name = "Clientes.findByDomicilio", query = "SELECT c FROM Clientes c WHERE c.domicilio = :domicilio"),
    @NamedQuery(name = "Clientes.findByTelefonos", query = "SELECT c FROM Clientes c WHERE c.telefonos = :telefonos"),
    @NamedQuery(name = "Clientes.findByCiudad", query = "SELECT c FROM Clientes c WHERE c.ciudad = :ciudad"),
    @NamedQuery(name = "Clientes.findByDepartamento", query = "SELECT c FROM Clientes c WHERE c.departamento = :departamento"),
    @NamedQuery(name = "Clientes.findByWeb", query = "SELECT c FROM Clientes c WHERE c.web = :web"),
    @NamedQuery(name = "Clientes.findByEmail", query = "SELECT c FROM Clientes c WHERE c.email = :email"),
    @NamedQuery(name = "Clientes.findByHabilitado", query = "SELECT c FROM Clientes c WHERE c.habilitado = :habilitado")})
public class Clientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "rut")
    private String rut;
    @Column(name = "domicilio")
    private String domicilio;
    @Basic(optional = false)
    @Column(name = "telefonos")
    private String telefonos;
    @Basic(optional = false)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @Column(name = "departamento")
    private String departamento;
    @Column(name = "web")
    private String web;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "habilitado")
    private char habilitado;

    @Basic(optional = false)
    @Column(name = "razon_social")
    private String razonSocial;

    @Basic(optional = false)
    @Column(name = "domicilio_fiscal")
    private String domicilioFiscal;

    @Basic(optional = false)
    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "facturar")
    private char facturar;
    @Column(name = "importe_contrato")
    private Float importeContrato;
    @Column(name = "facturar_mes_corriente")
    private char facturarMismoMes;

    @Column(name = "emisor")
    private Integer emisor;

    public Clientes() {
    }

    public Clientes(Integer id) {
        this.id = id;
    }

    public Clientes(Integer id, String rut, String telefonos, String ciudad, String departamento, char habilitado) {
        this.id = id;
        this.rut = rut;
        this.telefonos = telefonos;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.habilitado = habilitado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(char habilitado) {
        this.habilitado = habilitado;
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
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Clientes[id=" + id + "]";
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the domicilioFiscal
     */
    public String getDomicilioFiscal() {
        return domicilioFiscal;
    }

    /**
     * @param domicilioFiscal the domicilioFiscal to set
     */
    public void setDomicilioFiscal(String domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the facturar
     */
    public char getFacturar() {
        return facturar;
    }

    /**
     * @param facturar the facturar to set
     */
    public void setFacturar(char facturar) {
        this.facturar = facturar;
    }

    /**
     * @return the importeContrato
     */
    public Float getImporteContrato() {
        return importeContrato;
    }

    /**
     * @param importeContrato the importeContrato to set
     */
    public void setImporteContrato(Float importeContrato) {
        this.importeContrato = importeContrato;
    }

    /**
     * @return the facturarMismoMes
     */
    public char getFacturarMismoMes() {
        return facturarMismoMes;
    }

    /**
     * @param facturarMismoMes the facturarMismoMes to set
     */
    public void setFacturarMismoMes(char facturarMismoMes) {
        this.facturarMismoMes = facturarMismoMes;
    }

    /**
     * @return the emisor
     */
    public Integer getEmisor() {
        return emisor;
    }

    /**
     * @param emisor the emisor to set
     */
    public void setEmisor(Integer emisor) {
        this.emisor = emisor;
    }

}
