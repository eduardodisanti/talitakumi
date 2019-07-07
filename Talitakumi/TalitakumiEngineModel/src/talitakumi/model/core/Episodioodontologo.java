/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "episodioodontologo")
@NamedQueries({
    @NamedQuery(name = "Episodioodontologo.findAll", query = "SELECT e FROM Episodioodontologo e"),
    @NamedQuery(name = "Episodioodontologo.findByEpisodio", query = "SELECT e FROM Episodioodontologo e WHERE e.episodioodontologoPK.episodio = :episodio"),
    @NamedQuery(name = "Episodioodontologo.findByPieza", query = "SELECT e FROM Episodioodontologo e WHERE e.episodioodontologoPK.pieza = :pieza"),
    @NamedQuery(name = "Episodioodontologo.findByEnfermedad", query = "SELECT e FROM Episodioodontologo e WHERE e.episodioodontologoPK.enfermedad = :enfermedad"),
    @NamedQuery(name = "Episodioodontologo.findByPatologiamaligna", query = "SELECT e FROM Episodioodontologo e WHERE e.patologiamaligna = :patologiamaligna"),
    @NamedQuery(name = "Episodioodontologo.findByLesionblanca", query = "SELECT e FROM Episodioodontologo e WHERE e.lesionblanca = :lesionblanca"),
    @NamedQuery(name = "Episodioodontologo.findByAportacertificado", query = "SELECT e FROM Episodioodontologo e WHERE e.aportacertificado = :aportacertificado")})
public class Episodioodontologo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EpisodioodontologoPK episodioodontologoPK;
    @Basic(optional = false)
    @Column(name = "patologiamaligna")
    private char patologiamaligna;
    @Basic(optional = false)
    @Column(name = "lesionblanca")
    private char lesionblanca;
    @Column(name = "aportacertificado")
    private Character aportacertificado;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "enfermedad", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Enfermedades enfermedades;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Episodios episodios;
    @JoinColumn(name = "medico", referencedColumnName = "codigo")
    @ManyToOne
    private Medicos medico;

    public Episodioodontologo() {
    }

    public Episodioodontologo(EpisodioodontologoPK episodioodontologoPK) {
        this.episodioodontologoPK = episodioodontologoPK;
    }

    public Episodioodontologo(EpisodioodontologoPK episodioodontologoPK, char patologiamaligna, char lesionblanca) {
        this.episodioodontologoPK = episodioodontologoPK;
        this.patologiamaligna = patologiamaligna;
        this.lesionblanca = lesionblanca;
    }

    public Episodioodontologo(int episodio, int pieza, int enfermedad) {
        this.episodioodontologoPK = new EpisodioodontologoPK(episodio, pieza, enfermedad);
    }

    public EpisodioodontologoPK getEpisodioodontologoPK() {
        return episodioodontologoPK;
    }

    public void setEpisodioodontologoPK(EpisodioodontologoPK episodioodontologoPK) {
        this.episodioodontologoPK = episodioodontologoPK;
    }

    public char getPatologiamaligna() {
        return patologiamaligna;
    }

    public void setPatologiamaligna(char patologiamaligna) {
        this.patologiamaligna = patologiamaligna;
    }

    public char getLesionblanca() {
        return lesionblanca;
    }

    public void setLesionblanca(char lesionblanca) {
        this.lesionblanca = lesionblanca;
    }

    public Character getAportacertificado() {
        return aportacertificado;
    }

    public void setAportacertificado(Character aportacertificado) {
        this.aportacertificado = aportacertificado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Enfermedades getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(Enfermedades enfermedades) {
        this.enfermedades = enfermedades;
    }

    public Episodios getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Episodios episodios) {
        this.episodios = episodios;
    }

    public Medicos getMedico() {
        return medico;
    }

    public void setMedico(Medicos medico) {
        this.medico = medico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (episodioodontologoPK != null ? episodioodontologoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodioodontologo)) {
            return false;
        }
        Episodioodontologo other = (Episodioodontologo) object;
        if ((this.episodioodontologoPK == null && other.episodioodontologoPK != null) || (this.episodioodontologoPK != null && !this.episodioodontologoPK.equals(other.episodioodontologoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodioodontologo[episodioodontologoPK=" + episodioodontologoPK + "]";
    }

}
