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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "chequeos")
@NamedQueries({
    @NamedQuery(name = "Chequeos.findAll", query = "SELECT c FROM Chequeos c"),
    @NamedQuery(name = "Chequeos.findById", query = "SELECT c FROM Chequeos c WHERE c.id = :id"),
    @NamedQuery(name = "Chequeos.findByMedico", query = "SELECT c FROM Chequeos c WHERE c.medico = :medico"),
    @NamedQuery(name = "Chequeos.findByOdontologo", query = "SELECT c FROM Chequeos c WHERE c.odontologo = :odontologo"),
    @NamedQuery(name = "Chequeos.findByLaboratorio", query = "SELECT c FROM Chequeos c WHERE c.laboratorio = :laboratorio"),
    @NamedQuery(name = "Chequeos.findByPsicologo", query = "SELECT c FROM Chequeos c WHERE c.psicologo = :psicologo"),
    @NamedQuery(name = "Chequeos.findByVacuna", query = "SELECT c FROM Chequeos c WHERE c.vacuna = :vacuna"),
    @NamedQuery(name = "Chequeos.findByPap", query = "SELECT c FROM Chequeos c WHERE c.pap = :pap"),
    @NamedQuery(name = "Chequeos.findByMamo", query = "SELECT c FROM Chequeos c WHERE c.mamo = :mamo"),
    @NamedQuery(name = "Chequeos.findByNocorrespondelab", query = "SELECT c FROM Chequeos c WHERE c.nocorrespondelab = :nocorrespondelab"),
    @NamedQuery(name = "Chequeos.findByNocorrespondefoto", query = "SELECT c FROM Chequeos c WHERE c.nocorrespondefoto = :nocorrespondefoto"),
    @NamedQuery(name = "Chequeos.findByNocorrespondeodo", query = "SELECT c FROM Chequeos c WHERE c.nocorrespondeodo = :nocorrespondeodo"),
    @NamedQuery(name = "Chequeos.findByNocorrespondepsico", query = "SELECT c FROM Chequeos c WHERE c.nocorrespondepsico = :nocorrespondepsico"),
    @NamedQuery(name = "Chequeos.findByFoto", query = "SELECT c FROM Chequeos c WHERE c.foto = :foto"),
    @NamedQuery(name = "Chequeos.findByDictamenapto", query = "SELECT c FROM Chequeos c WHERE c.dictamenapto = :dictamenapto"),
    @NamedQuery(name = "Chequeos.findByTextlabo", query = "SELECT c FROM Chequeos c WHERE c.textlabo = :textlabo"),
    @NamedQuery(name = "Chequeos.findByVigencia", query = "SELECT c FROM Chequeos c WHERE c.vigencia = :vigencia")})
public class Chequeos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "medico")
    private int medico;
    @Basic(optional = false)
    @Column(name = "odontologo")
    private int odontologo;
    @Basic(optional = false)
    @Column(name = "laboratorio")
    private int laboratorio;
    @Column(name = "psicologo")
    private Integer psicologo;
    @Column(name = "vacuna")
    @Temporal(TemporalType.DATE)
    private Date vacuna;
    @Column(name = "pap")
    @Temporal(TemporalType.DATE)
    private Date pap;
    @Column(name = "mamo")
    @Temporal(TemporalType.DATE)
    private Date mamo;
    @Basic(optional = false)
    @Column(name = "nocorrespondelab")
    private String nocorrespondelab;
    @Column(name = "nocorrespondefoto")
    private String nocorrespondefoto;
    @Column(name = "nocorrespondeodo")
    private String nocorrespondeodo;
    @Column(name = "nocorrespondepsico")
    private String nocorrespondepsico;
    @Column(name = "foto")
    private String foto;
    @Column(name = "dictamenapto")
    private char dictamenapto;
    @Column(name = "textlabo")
    private String textlabo;
    @Column(name = "vigencia")
    private String vigencia;
    @JoinColumn(name = "episodio", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Episodios episodio;

    public Chequeos() {
    }

    public Chequeos(Integer id) {
        this.id = id;
    }

    public Chequeos(Integer id, int medico, int odontologo, int laboratorio, String nocorrespondelab) {
        this.id = id;
        this.medico = medico;
        this.odontologo = odontologo;
        this.laboratorio = laboratorio;
        this.nocorrespondelab = nocorrespondelab;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMedico() {
        return medico;
    }

    public void setMedico(int medico) {
        this.medico = medico;
    }

    public int getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(int odontologo) {
        this.odontologo = odontologo;
    }

    public int getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(int laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Integer getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(Integer psicologo) {
        this.psicologo = psicologo;
    }

    public Date getVacuna() {
        return vacuna;
    }

    public void setVacuna(Date vacuna) {
        this.vacuna = vacuna;
    }

    public Date getPap() {
        return pap;
    }

    public void setPap(Date pap) {
        this.pap = pap;
    }

    public Date getMamo() {
        return mamo;
    }

    public void setMamo(Date mamo) {
        this.mamo = mamo;
    }

    public String getNocorrespondelab() {
        return nocorrespondelab;
    }

    public void setNocorrespondelab(String nocorrespondelab) {
        this.nocorrespondelab = nocorrespondelab;
    }

    public String getNocorrespondefoto() {
        return nocorrespondefoto;
    }

    public void setNocorrespondefoto(String nocorrespondefoto) {
        this.nocorrespondefoto = nocorrespondefoto;
    }

    public String getNocorrespondeodo() {
        return nocorrespondeodo;
    }

    public void setNocorrespondeodo(String nocorrespondeodo) {
        this.nocorrespondeodo = nocorrespondeodo;
    }

    public String getNocorrespondepsico() {
        return nocorrespondepsico;
    }

    public void setNocorrespondepsico(String nocorrespondepsico) {
        this.nocorrespondepsico = nocorrespondepsico;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public char getDictamenApto() {
        return dictamenapto;
    }

    public void setDictamenApto(char dictamenapto) {
        this.dictamenapto = dictamenapto;
    }

    public String getTextLabo() {
        return textlabo;
    }

    public void setTextLabo(String textlabo) {
        this.textlabo = textlabo;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }
    
    public Episodios getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Episodios episodio) {
        this.episodio = episodio;
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
        if (!(object instanceof Chequeos)) {
            return false;
        }
        Chequeos other = (Chequeos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Chequeos[id=" + id + "]";
    }

}
