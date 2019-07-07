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
@Table(name = "libretasdeconducir")
@NamedQueries({
    @NamedQuery(name = "Libretasdeconducir.findAll", query = "SELECT l FROM Libretasdeconducir l"),
    @NamedQuery(name = "Libretasdeconducir.findByEpisodio", query = "SELECT l FROM Libretasdeconducir l WHERE l.episodio = :episodio"),
    @NamedQuery(name = "Libretasdeconducir.findByVigenciaanterior", query = "SELECT l FROM Libretasdeconducir l WHERE l.vigenciaanterior = :vigenciaanterior"),
    @NamedQuery(name = "Libretasdeconducir.findByIndiclentescorrectores", query = "SELECT l FROM Libretasdeconducir l WHERE l.indiclentescorrectores = :indiclentescorrectores"),
    @NamedQuery(name = "Libretasdeconducir.findByIndiclentescontacto", query = "SELECT l FROM Libretasdeconducir l WHERE l.indiclentescontacto = :indiclentescontacto"),
    @NamedQuery(name = "Libretasdeconducir.findByIndiclentesintraoculares", query = "SELECT l FROM Libretasdeconducir l WHERE l.indiclentesintraoculares = :indiclentesintraoculares"),
    @NamedQuery(name = "Libretasdeconducir.findByIndicaudifonoizquierdo", query = "SELECT l FROM Libretasdeconducir l WHERE l.indicaudifonoizquierdo = :indicaudifonoizquierdo"),
    @NamedQuery(name = "Libretasdeconducir.findByIndicaudifonoderecho", query = "SELECT l FROM Libretasdeconducir l WHERE l.indicaudifonoderecho = :indicaudifonoderecho"),
    @NamedQuery(name = "Libretasdeconducir.findByIndicdobleretrovisor", query = "SELECT l FROM Libretasdeconducir l WHERE l.indicdobleretrovisor = :indicdobleretrovisor"),
    @NamedQuery(name = "Libretasdeconducir.findByVigencia", query = "SELECT l FROM Libretasdeconducir l WHERE l.vigencia = :vigencia"),
    @NamedQuery(name = "Libretasdeconducir.findByCodigo1", query = "SELECT l FROM Libretasdeconducir l WHERE l.codigo1 = :codigo1"),
    @NamedQuery(name = "Libretasdeconducir.findByCodigo2", query = "SELECT l FROM Libretasdeconducir l WHERE l.codigo2 = :codigo2"),
    @NamedQuery(name = "Libretasdeconducir.findByCodigo3", query = "SELECT l FROM Libretasdeconducir l WHERE l.codigo3 = :codigo3"),
    @NamedQuery(name = "Libretasdeconducir.findByCategoria", query = "SELECT l FROM Libretasdeconducir l WHERE l.categoria = :categoria"),
    @NamedQuery(name = "Libretasdeconducir.findByApto", query = "SELECT l FROM Libretasdeconducir l WHERE l.apto = :apto"),
    @NamedQuery(name = "Libretasdeconducir.findByTipo", query = "SELECT l FROM Libretasdeconducir l WHERE l.tipo = :tipo"),
    @NamedQuery(name = "Libretasdeconducir.findByProcedencia", query = "SELECT l FROM Libretasdeconducir l WHERE l.procedencia = :procedencia"),
    @NamedQuery(name = "Libretasdeconducir.findByDocumento", query = "SELECT l FROM Libretasdeconducir l WHERE l.documento = :documento")})
public class Libretasdeconducir implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @Column(name = "vigenciaanterior")
    @Temporal(TemporalType.DATE)
    private Date vigenciaanterior;
    @Column(name = "indiclentescorrectores")
    private Character indiclentescorrectores;
    @Column(name = "indiclentescontacto")
    private Character indiclentescontacto;
    @Basic(optional = false)
    @Column(name = "indiclentesintraoculares")
    private char indiclentesintraoculares;
    @Basic(optional = false)
    @Column(name = "indicaudifonoizquierdo")
    private char indicaudifonoizquierdo;
    @Basic(optional = false)
    @Column(name = "indicaudifonoderecho")
    private char indicaudifonoderecho;
    @Column(name = "indicdobleretrovisor")
    private Character indicdobleretrovisor;
    @Basic(optional = false)
    @Column(name = "vigencia")
    private int vigencia;
    @Column(name = "codigo1")
    private Integer codigo1;
    @Basic(optional = false)
    @Column(name = "codigo2")
    private int codigo2;
    @Basic(optional = false)
    @Column(name = "codigo3")
    private int codigo3;
    @Column(name = "codigo4")
    private Integer codigo4;
    @Basic(optional = false)
    @Column(name = "codigo5")
    private int codigo5;
    @Basic(optional = false)
    @Column(name = "codigo6")
    private int codigo6;
    @Column(name = "categoria")
    private String categoria;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "apto")
    private char apto;
    @Basic(optional = false)
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @Column(name = "procedencia")
    private int procedencia;
    @Basic(optional = false)
    @Column(name = "documento")
    private String documento;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;

    public Libretasdeconducir() {
    }

    public Libretasdeconducir(Integer episodio) {
        this.episodio = episodio;
    }

    public Libretasdeconducir(Integer episodio, char indiclentesintraoculares, char indicaudifonoizquierdo, char indicaudifonoderecho, int vigencia, int codigo2, int codigo3, char apto, int tipo, int procedencia, String documento) {
        this.episodio = episodio;
        this.indiclentesintraoculares = indiclentesintraoculares;
        this.indicaudifonoizquierdo = indicaudifonoizquierdo;
        this.indicaudifonoderecho = indicaudifonoderecho;
        this.vigencia = vigencia;
        this.codigo2 = codigo2;
        this.codigo3 = codigo3;
        this.apto = apto;
        this.tipo = tipo;
        this.procedencia = procedencia;
        this.documento = documento;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public Date getVigenciaanterior() {
        return vigenciaanterior;
    }

    public void setVigenciaanterior(Date vigenciaanterior) {
        this.vigenciaanterior = vigenciaanterior;
    }

    public Character getIndiclentescorrectores() {
        return indiclentescorrectores;
    }

    public void setIndiclentescorrectores(Character indiclentescorrectores) {
        this.indiclentescorrectores = indiclentescorrectores;
    }

    public Character getIndiclentescontacto() {
        return indiclentescontacto;
    }

    public void setIndiclentescontacto(Character indiclentescontacto) {
        this.indiclentescontacto = indiclentescontacto;
    }

    public char getIndiclentesintraoculares() {
        return indiclentesintraoculares;
    }

    public void setIndiclentesintraoculares(char indiclentesintraoculares) {
        this.indiclentesintraoculares = indiclentesintraoculares;
    }

    public char getIndicaudifonoizquierdo() {
        return indicaudifonoizquierdo;
    }

    public void setIndicaudifonoizquierdo(char indicaudifonoizquierdo) {
        this.indicaudifonoizquierdo = indicaudifonoizquierdo;
    }

    public char getIndicaudifonoderecho() {
        return indicaudifonoderecho;
    }

    public void setIndicaudifonoderecho(char indicaudifonoderecho) {
        this.indicaudifonoderecho = indicaudifonoderecho;
    }

    public Character getIndicdobleretrovisor() {
        return indicdobleretrovisor;
    }

    public void setIndicdobleretrovisor(Character indicdobleretrovisor) {
        this.indicdobleretrovisor = indicdobleretrovisor;
    }

    public int getVigencia() {
        return vigencia;
    }

    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }

    public Integer getCodigo1() {
        return codigo1;
    }

    public void setCodigo1(Integer codigo1) {
        this.codigo1 = codigo1;
    }

    public int getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(int codigo2) {
        this.codigo2 = codigo2;
    }

    public int getCodigo3() {
        return codigo3;
    }

    public void setCodigo3(int codigo3) {
        this.codigo3 = codigo3;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public char getApto() {
        return apto;
    }

    public void setApto(char apto) {
        this.apto = apto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(int procedencia) {
        this.procedencia = procedencia;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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
        if (!(object instanceof Libretasdeconducir)) {
            return false;
        }
        Libretasdeconducir other = (Libretasdeconducir) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Libretasdeconducir[episodio=" + episodio + "]";
    }

    /**
     * @return the codigo4
     */
    public Integer getCodigo4() {
        return codigo4;
    }

    /**
     * @param codigo4 the codigo4 to set
     */
    public void setCodigo4(Integer codigo4) {
        this.codigo4 = codigo4;
    }

    /**
     * @return the codigo5
     */
    public int getCodigo5() {
        return codigo5;
    }

    /**
     * @param codigo5 the codigo5 to set
     */
    public void setCodigo5(int codigo5) {
        this.codigo5 = codigo5;
    }

    /**
     * @return the codigo6
     */
    public int getCodigo6() {
        return codigo6;
    }

    /**
     * @param codigo6 the codigo6 to set
     */
    public void setCodigo6(int codigo6) {
        this.codigo6 = codigo6;
    }

}
