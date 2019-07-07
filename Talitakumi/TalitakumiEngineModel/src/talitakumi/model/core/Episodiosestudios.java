/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "episodiosestudios")
@NamedQueries({
    @NamedQuery(name = "Episodiosestudios.findAll", query = "SELECT e FROM Episodiosestudios e"),
    @NamedQuery(name = "Episodiosestudios.findByEpisodio", query = "SELECT e FROM Episodiosestudios e WHERE e.episodiosestudiosPK.episodio = :episodio"),
    @NamedQuery(name = "Episodiosestudios.findByEstudio", query = "SELECT e FROM Episodiosestudios e WHERE e.episodiosestudiosPK.estudio = :estudio"),
    @NamedQuery(name = "Episodiosestudios.findByVencimiento", query = "SELECT e FROM Episodiosestudios e WHERE e.vencimiento = :vencimiento"),
    @NamedQuery(name = "Episodiosestudios.findByMedico", query = "SELECT e FROM Episodiosestudios e WHERE e.medico = :medico"),
    @NamedQuery(name = "Episodiosestudios.findByClinica", query = "SELECT e FROM Episodiosestudios e WHERE e.clinica = :clinica")})
public class Episodiosestudios implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EpisodiosestudiosPK episodiosestudiosPK;
    @Column(name = "vencimiento")
    @Temporal(TemporalType.DATE)
    private Date vencimiento;
    @Column(name = "medico")
    private String medico;
    @Column(name = "clinica")
    private String clinica;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Episodios episodios;
    @JoinColumn(name = "estudio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estudios estudios;

    public Episodiosestudios() {
    }

    public Episodiosestudios(EpisodiosestudiosPK episodiosestudiosPK) {
        this.episodiosestudiosPK = episodiosestudiosPK;
    }

    public Episodiosestudios(int episodio, int estudio) {
        this.episodiosestudiosPK = new EpisodiosestudiosPK(episodio, estudio);
    }

    public EpisodiosestudiosPK getEpisodiosestudiosPK() {
        return episodiosestudiosPK;
    }

    public void setEpisodiosestudiosPK(EpisodiosestudiosPK episodiosestudiosPK) {
        this.episodiosestudiosPK = episodiosestudiosPK;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getClinica() {
        return clinica;
    }

    public void setClinica(String clinica) {
        this.clinica = clinica;
    }

    public Episodios getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Episodios episodios) {
        this.episodios = episodios;
    }

    public Estudios getEstudios() {
        return estudios;
    }

    public void setEstudios(Estudios estudios) {
        this.estudios = estudios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (episodiosestudiosPK != null ? episodiosestudiosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodiosestudios)) {
            return false;
        }
        Episodiosestudios other = (Episodiosestudios) object;
        if ((this.episodiosestudiosPK == null && other.episodiosestudiosPK != null) || (this.episodiosestudiosPK != null && !this.episodiosestudiosPK.equals(other.episodiosestudiosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodiosestudios[episodiosestudiosPK=" + episodiosestudiosPK + "]";
    }

}
