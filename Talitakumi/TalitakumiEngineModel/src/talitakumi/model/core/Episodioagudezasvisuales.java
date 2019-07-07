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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "episodioagudezasvisuales")
@NamedQueries({
    @NamedQuery(name = "Episodioagudezasvisuales.findAll", query = "SELECT e FROM Episodioagudezasvisuales e"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByEpisodio", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.episodio = :episodio"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByScod", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.scod = :scod"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByScoi", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.scoi = :scoi"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByCcod", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.ccod = :ccod"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByCcoi", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.ccoi = :ccoi"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByClentescontactood", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.clentescontactood = :clentescontactood"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByClentescontactooi", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.clentescontactooi = :clentescontactooi"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByClentesintraocularesod", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.clentesintraocularesod = :clentesintraocularesod"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByClentesintraocularesoi", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.clentesintraocularesoi = :clentesintraocularesoi"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByEstrabismo", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.estrabismo = :estrabismo"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByNistagmus", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.nistagmus = :nistagmus"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByPtosispalpebralsobreborde", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.ptosispalpebralsobreborde = :ptosispalpebralsobreborde"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByPtosispalpebraldebajoborde", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.ptosispalpebraldebajoborde = :ptosispalpebraldebajoborde"),
    @NamedQuery(name = "Episodioagudezasvisuales.findByVisionbinocular", query = "SELECT e FROM Episodioagudezasvisuales e WHERE e.visionbinocular = :visionbinocular")})
public class Episodioagudezasvisuales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "episodio")
    private Integer episodio;
    @Basic(optional = false)
    @Column(name = "scod")
    private float scod;
    @Basic(optional = false)
    @Column(name = "scoi")
    private float scoi;
    @Basic(optional = false)
    @Column(name = "ccod")
    private float ccod;
    @Basic(optional = false)
    @Column(name = "ccoi")
    private float ccoi;
    @Basic(optional = false)
    @Column(name = "clentescontactood")
    private float clentescontactood;
    @Basic(optional = false)
    @Column(name = "clentescontactooi")
    private float clentescontactooi;
    @Basic(optional = false)
    @Column(name = "clentesintraocularesod")
    private float clentesintraocularesod;
    @Basic(optional = false)
    @Column(name = "clentesintraocularesoi")
    private float clentesintraocularesoi;
    @Basic(optional = false)
    @Column(name = "estrabismo")
    private char estrabismo;
    @Basic(optional = false)
    @Column(name = "nistagmus")
    private char nistagmus;
    @Basic(optional = false)
    @Column(name = "ptosispalpebralsobreborde")
    private int ptosispalpebralsobreborde;
    @Basic(optional = false)
    @Column(name = "ptosispalpebraldebajoborde")
    private int ptosispalpebraldebajoborde;
    @Basic(optional = false)
    @Column(name = "visionbinocular")
    private char visionbinocular;
    @JoinColumn(name = "episodio", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Episodios episodios;

    public Episodioagudezasvisuales() {
    }

    public Episodioagudezasvisuales(Integer episodio) {
        this.episodio = episodio;
    }

    public Episodioagudezasvisuales(Integer episodio, float scod, float scoi, float ccod, float ccoi, float clentescontactood, float clentescontactooi, float clentesintraocularesod, float clentesintraocularesoi, char estrabismo, char nistagmus, int ptosispalpebralsobreborde, int ptosispalpebraldebajoborde, char visionbinocular) {
        this.episodio = episodio;
        this.scod = scod;
        this.scoi = scoi;
        this.ccod = ccod;
        this.ccoi = ccoi;
        this.clentescontactood = clentescontactood;
        this.clentescontactooi = clentescontactooi;
        this.clentesintraocularesod = clentesintraocularesod;
        this.clentesintraocularesoi = clentesintraocularesoi;
        this.estrabismo = estrabismo;
        this.nistagmus = nistagmus;
        this.ptosispalpebralsobreborde = ptosispalpebralsobreborde;
        this.ptosispalpebraldebajoborde = ptosispalpebraldebajoborde;
        this.visionbinocular = visionbinocular;
    }

    public Integer getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }

    public float getScod() {
        return scod;
    }

    public void setScod(float scod) {
        this.scod = scod;
    }

    public float getScoi() {
        return scoi;
    }

    public void setScoi(float scoi) {
        this.scoi = scoi;
    }

    public float getCcod() {
        return ccod;
    }

    public void setCcod(float ccod) {
        this.ccod = ccod;
    }

    public float getCcoi() {
        return ccoi;
    }

    public void setCcoi(float ccoi) {
        this.ccoi = ccoi;
    }

    public float getClentescontactood() {
        return clentescontactood;
    }

    public void setClentescontactood(float clentescontactood) {
        this.clentescontactood = clentescontactood;
    }

    public float getClentescontactooi() {
        return clentescontactooi;
    }

    public void setClentescontactooi(float clentescontactooi) {
        this.clentescontactooi = clentescontactooi;
    }

    public float getClentesintraocularesod() {
        return clentesintraocularesod;
    }

    public void setClentesintraocularesod(float clentesintraocularesod) {
        this.clentesintraocularesod = clentesintraocularesod;
    }

    public float getClentesintraocularesoi() {
        return clentesintraocularesoi;
    }

    public void setClentesintraocularesoi(float clentesintraocularesoi) {
        this.clentesintraocularesoi = clentesintraocularesoi;
    }

    public char getEstrabismo() {
        return estrabismo;
    }

    public void setEstrabismo(char estrabismo) {
        this.estrabismo = estrabismo;
    }

    public char getNistagmus() {
        return nistagmus;
    }

    public void setNistagmus(char nistagmus) {
        this.nistagmus = nistagmus;
    }

    public int getPtosispalpebralsobreborde() {
        return ptosispalpebralsobreborde;
    }

    public void setPtosispalpebralsobreborde(int ptosispalpebralsobreborde) {
        this.ptosispalpebralsobreborde = ptosispalpebralsobreborde;
    }

    public int getPtosispalpebraldebajoborde() {
        return ptosispalpebraldebajoborde;
    }

    public void setPtosispalpebraldebajoborde(int ptosispalpebraldebajoborde) {
        this.ptosispalpebraldebajoborde = ptosispalpebraldebajoborde;
    }

    public char getVisionbinocular() {
        return visionbinocular;
    }

    public void setVisionbinocular(char visionbinocular) {
        this.visionbinocular = visionbinocular;
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
        if (!(object instanceof Episodioagudezasvisuales)) {
            return false;
        }
        Episodioagudezasvisuales other = (Episodioagudezasvisuales) object;
        if ((this.episodio == null && other.episodio != null) || (this.episodio != null && !this.episodio.equals(other.episodio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Episodioagudezasvisuales[episodio=" + episodio + "]";
    }

}
