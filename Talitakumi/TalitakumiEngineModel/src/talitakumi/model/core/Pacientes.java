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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "pacientes")
@NamedQueries({
    @NamedQuery(name = "Pacientes.findAll", query = "SELECT p FROM Pacientes p"),
    @NamedQuery(name = "Pacientes.findByFechaingreso", query = "SELECT p FROM Pacientes p WHERE p.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Pacientes.findById", query = "SELECT p FROM Pacientes p WHERE p.id = :id")})
public class Pacientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
    private Collection<Vencimientosdoc> vencimientosdocCollection;
    @OneToMany(mappedBy = "paciente")
    private Collection<Episodios> episodiosCollection;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Personas personas;
    @JoinColumn(name = "seguro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Seguros seguro;

    public Pacientes() {
    }

    public Pacientes(Integer id) {
        this.id = id;
    }

    public Pacientes(Integer id, Date fechaingreso) {
        this.id = id;
        this.fechaingreso = fechaingreso;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<Vencimientosdoc> getVencimientosdocCollection() {
        return vencimientosdocCollection;
    }

    public void setVencimientosdocCollection(Collection<Vencimientosdoc> vencimientosdocCollection) {
        this.vencimientosdocCollection = vencimientosdocCollection;
    }

    public Collection<Episodios> getEpisodiosCollection() {
        return episodiosCollection;
    }

    public void setEpisodiosCollection(Collection<Episodios> episodiosCollection) {
        this.episodiosCollection = episodiosCollection;
    }

    public Personas getPersonas() {
        return personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
    }

    public Seguros getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguros seguro) {
        this.seguro = seguro;
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
        if (!(object instanceof Pacientes)) {
            return false;
        }
        Pacientes other = (Pacientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Pacientes[id=" + id + "]";
    }

}
