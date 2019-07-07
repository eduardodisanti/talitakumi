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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "analisis")
@NamedQueries({
    @NamedQuery(name = "Analisis.findAll", query = "SELECT a FROM Analisis a"),
    @NamedQuery(name = "Analisis.findById", query = "SELECT a FROM Analisis a WHERE a.id = :id"),
    @NamedQuery(name = "Analisis.findByDescripcion", query = "SELECT a FROM Analisis a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Analisis.findByValorrefminimo", query = "SELECT a FROM Analisis a WHERE a.valorrefminimo = :valorrefminimo"),
    @NamedQuery(name = "Analisis.findByValorrefmaximo", query = "SELECT a FROM Analisis a WHERE a.valorrefmaximo = :valorrefmaximo"),
    @NamedQuery(name = "Analisis.findByTiporesultado", query = "SELECT a FROM Analisis a WHERE a.tiporesultado = :tiporesultado"),
    @NamedQuery(name = "Analisis.findByUnidades", query = "SELECT a FROM Analisis a WHERE a.unidades = :unidades"),
    @NamedQuery(name = "Analisis.findByComentrios", query = "SELECT a FROM Analisis a WHERE a.comentrios = :comentrios"),
    @NamedQuery(name = "Analisis.findByMascara", query = "SELECT a FROM Analisis a WHERE a.mascara = :mascara"),
    @NamedQuery(name = "Analisis.findByValoraceptablemax", query = "SELECT a FROM Analisis a WHERE a.valoraceptablemax = :valoraceptablemax"),
    @NamedQuery(name = "Analisis.findByValoraceptablemin", query = "SELECT a FROM Analisis a WHERE a.valoraceptablemin = :valoraceptablemin")})
public class Analisis implements Serializable {
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
    @Column(name = "valorrefminimo")
    private String valorrefminimo;
    @Basic(optional = false)
    @Column(name = "valorrefmaximo")
    private String valorrefmaximo;
    @Basic(optional = false)
    @Column(name = "tiporesultado")
    private char tiporesultado;
    @Basic(optional = false)
    @Column(name = "unidades")
    private String unidades;
    @Basic(optional = false)
    @Column(name = "comentrios")
    private String comentrios;
    @Basic(optional = false)
    @Column(name = "mascara")
    private String mascara;
    @Basic(optional = false)
    @Column(name = "valoraceptablemax")
    private String valoraceptablemax;
    @Basic(optional = false)
    @Column(name = "valoraceptablemin")
    private String valoraceptablemin;
    @JoinTable(name = "estudiosanalisis", joinColumns = {
        @JoinColumn(name = "analisis", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "estudio", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Estudios> estudiosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "analisis")
    private Collection<Analisishechos> analisishechosCollection;

    public Analisis() {
    }

    public Analisis(Integer id) {
        this.id = id;
    }

    public Analisis(Integer id, String descripcion, String valorrefminimo, String valorrefmaximo, char tiporesultado, String unidades, String comentrios, String mascara, String valoraceptablemax, String valoraceptablemin) {
        this.id = id;
        this.descripcion = descripcion;
        this.valorrefminimo = valorrefminimo;
        this.valorrefmaximo = valorrefmaximo;
        this.tiporesultado = tiporesultado;
        this.unidades = unidades;
        this.comentrios = comentrios;
        this.mascara = mascara;
        this.valoraceptablemax = valoraceptablemax;
        this.valoraceptablemin = valoraceptablemin;
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

    public String getValorrefminimo() {
        return valorrefminimo;
    }

    public void setValorrefminimo(String valorrefminimo) {
        this.valorrefminimo = valorrefminimo;
    }

    public String getValorrefmaximo() {
        return valorrefmaximo;
    }

    public void setValorrefmaximo(String valorrefmaximo) {
        this.valorrefmaximo = valorrefmaximo;
    }

    public char getTiporesultado() {
        return tiporesultado;
    }

    public void setTiporesultado(char tiporesultado) {
        this.tiporesultado = tiporesultado;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getComentrios() {
        return comentrios;
    }

    public void setComentrios(String comentrios) {
        this.comentrios = comentrios;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public String getValoraceptablemax() {
        return valoraceptablemax;
    }

    public void setValoraceptablemax(String valoraceptablemax) {
        this.valoraceptablemax = valoraceptablemax;
    }

    public String getValoraceptablemin() {
        return valoraceptablemin;
    }

    public void setValoraceptablemin(String valoraceptablemin) {
        this.valoraceptablemin = valoraceptablemin;
    }

    public Collection<Estudios> getEstudiosCollection() {
        return estudiosCollection;
    }

    public void setEstudiosCollection(Collection<Estudios> estudiosCollection) {
        this.estudiosCollection = estudiosCollection;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Analisis)) {
            return false;
        }
        Analisis other = (Analisis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Analisis[id=" + id + "]";
    }

}
