/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "carnetdesalud")
@NamedQueries({
    @NamedQuery(name = "Carnetdesalud.findAll", query = "SELECT c FROM Carnetdesalud c"),
    @NamedQuery(name = "Carnetdesalud.findByEpisodio", query = "SELECT c FROM Carnetdesalud c WHERE c.episodio = :episodio"),
    @NamedQuery(name = "Carnetdesalud.findByCliente", query = "SELECT c FROM Carnetdesalud c WHERE c.cliente = :cliente"),
    @NamedQuery(name = "Carnetdesalud.findByVigencia", query = "SELECT c FROM Carnetdesalud c WHERE c.vigencia = :vigencia"),
    @NamedQuery(name = "Carnetdesalud.findByVigencialaboratorio", query = "SELECT c FROM Carnetdesalud c WHERE c.vigencialaboratorio = :vigencialaboratorio"),
    @NamedQuery(name = "Carnetdesalud.findByVigenciaodontologo", query = "SELECT c FROM Carnetdesalud c WHERE c.vigenciaodontologo = :vigenciaodontologo"),
    @NamedQuery(name = "Carnetdesalud.findByVigenciamedico", query = "SELECT c FROM Carnetdesalud c WHERE c.vigenciamedico = :vigenciamedico"),
    @NamedQuery(name = "Carnetdesalud.findByCartamedicotratante", query = "SELECT c FROM Carnetdesalud c WHERE c.cartamedicotratante = :cartamedicotratante"),
    @NamedQuery(name = "Carnetdesalud.findByCausamedica", query = "SELECT c FROM Carnetdesalud c WHERE c.causamedica = :causamedica"),
    @NamedQuery(name = "Carnetdesalud.findByFechaexpedido", query = "SELECT c FROM Carnetdesalud c WHERE c.fechaexpedido = :fechaexpedido"),
    @NamedQuery(name = "Carnetdesalud.findBySugerenciacm", query = "SELECT c FROM Carnetdesalud c WHERE c.sugerenciacm = :sugerenciacm"),
    @NamedQuery(name = "Carnetdesalud.findBySugerenciaexlab", query = "SELECT c FROM Carnetdesalud c WHERE c.sugerenciaexlab = :sugerenciaexlab"),
    @NamedQuery(name = "Carnetdesalud.findBySugerenciacontpeso", query = "SELECT c FROM Carnetdesalud c WHERE c.sugerenciacontpeso = :sugerenciacontpeso"),
    @NamedQuery(name = "Carnetdesalud.findBySugerenciaejercicios", query = "SELECT c FROM Carnetdesalud c WHERE c.sugerenciaejercicios = :sugerenciaejercicios")})
public class Carnetdesalud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @Basic(optional = false)
    @Column(name = "cliente")
    private int cliente;
    @Basic(optional = false)
    @Column(name = "vigencia")
    private int vigencia;
    @Basic(optional = false)
    @Column(name = "vigencialaboratorio")
    private int vigencialaboratorio;
    @Basic(optional = false)
    @Column(name = "vigenciaodontologo")
    private int vigenciaodontologo;
    @Basic(optional = false)
    @Column(name = "vigenciamedico")
    private int vigenciamedico;
    @Basic(optional = false)
    @Column(name = "cartamedicotratante")
    private char cartamedicotratante;
    @Basic(optional = false)
    @Column(name = "causamedica")
    private char causamedica;
    @Column(name = "fechaexpedido")
    @Temporal(TemporalType.DATE)
    private Date fechaexpedido;
    @Column(name = "sugerenciacm")
    private Character sugerenciacm;
    @Column(name = "sugerenciaexlab")
    private Character sugerenciaexlab;
    @Column(name = "sugerenciacontpeso")
    private Character sugerenciacontpeso;
    @Column(name = "sugerenciaejercicios")
    private Character sugerenciaejercicios;
    @Lob
    @Column(name = "impresiongeneral")
    private String impresiongeneral;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;
    @Column(name = "impreso")
    private Long impreso;

    public Carnetdesalud() {
    }

    public Carnetdesalud(Integer episodio) {
        this.episodio = episodio;
    }

    public Carnetdesalud(Integer episodio, int cliente, int vigencia, int vigencialaboratorio, int vigenciaodontologo, int vigenciamedico, char cartamedicotratante, char causamedica) {
        this.episodio = episodio;
        this.cliente = cliente;
        this.vigencia = vigencia;
        this.vigencialaboratorio = vigencialaboratorio;
        this.vigenciaodontologo = vigenciaodontologo;
        this.vigenciamedico = vigenciamedico;
        this.cartamedicotratante = cartamedicotratante;
        this.causamedica = causamedica;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getVigencia() {
        return vigencia;
    }

    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }

    public int getVigencialaboratorio() {
        return vigencialaboratorio;
    }

    public void setVigencialaboratorio(int vigencialaboratorio) {
        this.vigencialaboratorio = vigencialaboratorio;
    }

    public int getVigenciaodontologo() {
        return vigenciaodontologo;
    }

    public void setVigenciaodontologo(int vigenciaodontologo) {
        this.vigenciaodontologo = vigenciaodontologo;
    }

    public int getVigenciamedico() {
        return vigenciamedico;
    }

    public void setVigenciamedico(int vigenciamedico) {
        this.vigenciamedico = vigenciamedico;
    }

    public char getCartamedicotratante() {
        return cartamedicotratante;
    }

    public void setCartamedicotratante(char cartamedicotratante) {
        this.cartamedicotratante = cartamedicotratante;
    }

    public char getCausamedica() {
        return causamedica;
    }

    public void setCausamedica(char causamedica) {
        this.causamedica = causamedica;
    }

    public Date getFechaexpedido() {
        return fechaexpedido;
    }

    public void setFechaexpedido(Date fechaexpedido) {
        this.fechaexpedido = fechaexpedido;
    }

    public Character getSugerenciacm() {
        return sugerenciacm;
    }

    public void setSugerenciacm(Character sugerenciacm) {
        this.sugerenciacm = sugerenciacm;
    }

    public Character getSugerenciaexlab() {
        return sugerenciaexlab;
    }

    public void setSugerenciaexlab(Character sugerenciaexlab) {
        this.sugerenciaexlab = sugerenciaexlab;
    }

    public Character getSugerenciacontpeso() {
        return sugerenciacontpeso;
    }

    public void setSugerenciacontpeso(Character sugerenciacontpeso) {
        this.sugerenciacontpeso = sugerenciacontpeso;
    }

    public Character getSugerenciaejercicios() {
        return sugerenciaejercicios;
    }

    public void setSugerenciaejercicios(Character sugerenciaejercicios) {
        this.sugerenciaejercicios = sugerenciaejercicios;
    }

    public String getImpresiongeneral() {
        return impresiongeneral;
    }

    public void setImpresiongeneral(String impresiongeneral) {
        this.impresiongeneral = impresiongeneral;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Episodios getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Episodios episodios) {
        this.episodios = episodios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (episodio != null ? episodio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carnetdesalud)) {
            return false;
        }
        Carnetdesalud other = (Carnetdesalud) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }
    public Long getImpreso() {
        return impreso;
    }

    public void setImpreso(Long em) {
        this.impreso = em;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Carnetdesalud[episodio=" + episodio + "]";
    }

}
