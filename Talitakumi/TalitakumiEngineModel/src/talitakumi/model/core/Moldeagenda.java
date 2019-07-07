/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.model.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rupertus
 */
@Entity
@Table(name = "moldeagenda")
@NamedQueries({
    @NamedQuery(name = "Moldeagenda.findAll", query = "SELECT m FROM Moldeagenda m"),
    @NamedQuery(name = "Moldeagenda.findById", query = "SELECT m FROM Moldeagenda m WHERE m.id = :id"),
    @NamedQuery(name = "Moldeagenda.findByDia", query = "SELECT m FROM Moldeagenda m WHERE m.dia = :dia"),
    @NamedQuery(name = "Moldeagenda.findByTurno", query = "SELECT m FROM Moldeagenda m WHERE m.turno = :turno"),
    @NamedQuery(name = "Moldeagenda.findByHora", query = "SELECT m FROM Moldeagenda m WHERE m.hora = :hora"),
    @NamedQuery(name = "Moldeagenda.findByDuracion", query = "SELECT m FROM Moldeagenda m WHERE m.duracion = :duracion"),
    @NamedQuery(name = "Moldeagenda.findByLugares", query = "SELECT m FROM Moldeagenda m WHERE m.lugares = :lugares"),
    @NamedQuery(name = "Moldeagenda.findByConsultorio", query = "SELECT m FROM Moldeagenda m WHERE m.consultorio = :consultorio")})
public class Moldeagenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dia")
    private int dia;
    @Basic(optional = false)
    @Column(name = "turno")
    private int turno;
    @Basic(optional = false)
    @Column(name = "hora")
    private Integer hora;
    @Basic(optional = false)
    @Column(name = "minutos")
    private Integer minutos;
    @Basic(optional = false)
    @Column(name = "duracion")
    private int duracion;
    @Basic(optional = false)
    @Column(name = "lugares")
    private int lugares;
    @Basic(optional = false)
    @Column(name = "consultorio")
    private String consultorio;
    @JoinColumn(name = "servicio", referencedColumnName = "id")
    @ManyToOne
    private Tipodeactividad servicio;


    public Moldeagenda() {
    }

    public Moldeagenda(Integer id) {
        this.id = id;
    }

    public Moldeagenda(Integer id, int dia, int turno, Integer hora, Integer minutos, int duracion, int lugares, String consultorio) {
        this.id = id;
        this.dia = dia;
        this.turno = turno;
        this.hora = hora;
        this.minutos = minutos;
        this.duracion = duracion;
        this.lugares = lugares;
        this.consultorio = consultorio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getLugares() {
        return lugares;
    }

    public void setLugares(int lugares) {
        this.lugares = lugares;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
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
        if (!(object instanceof Moldeagenda)) {
            return false;
        }
        Moldeagenda other = (Moldeagenda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "talitakumi.model.core.Moldeagenda[id=" + id + "]";
    }

    /**
     * @return the servicio
     */
    public Tipodeactividad getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(Tipodeactividad servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the minutos
     */
    public Integer getMinutos() {
        return minutos;
    }

    /**
     * @param minutos the minutos to set
     */
    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

}
