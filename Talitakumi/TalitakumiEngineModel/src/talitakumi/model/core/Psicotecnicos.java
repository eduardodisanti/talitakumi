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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "psicotecnicos")
@NamedQueries({
    @NamedQuery(name = "Psicotecnicos.findAll", query = "SELECT p FROM Psicotecnicos p"),
    @NamedQuery(name = "Psicotecnicos.findByEpisodio", query = "SELECT p FROM Psicotecnicos p WHERE p.episodio = :episodio"),
    @NamedQuery(name = "Psicotecnicos.findByPuesto", query = "SELECT p FROM Psicotecnicos p WHERE p.puesto = :puesto"),
    @NamedQuery(name = "Psicotecnicos.findByTipodeconsulta", query = "SELECT p FROM Psicotecnicos p WHERE p.tipodeconsulta = :tipodeconsulta")
})
public class Psicotecnicos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @Basic(optional = false)
    @Column(name = "puesto")
    private String puesto;
    @Basic(optional = false)
    @Column(name = "tipodeconsulta")
    private String tipodeconsulta;


    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;
    @JoinColumn(name = "procedencia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Procedencias procedencia;


    public Psicotecnicos() {
    }

    public Psicotecnicos(Integer episodio) {
        this.episodio = episodio;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getTipodeconsulta() {
        return tipodeconsulta;
    }

    public void setTipodeconsulta(String tipodeconsulta) {
        this.tipodeconsulta = tipodeconsulta;
    }
    public Episodios getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Episodios episodios) {
        this.episodios = episodios;
    }

    public Procedencias getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(Procedencias procedencia) {
        this.procedencia = procedencia;
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
        if (!(object instanceof Psicotecnicos)) {
            return false;
        }
        Psicotecnicos other = (Psicotecnicos) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Psicotecnicos[episodio=" + episodio + "]";
    }

}
