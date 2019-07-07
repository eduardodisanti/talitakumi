/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Agenda;

/**
 *
 * @author rupertus
 */
public class CargarAgendaConsultorio implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Integer tipolistado;
    private Integer consultorio;
    private Character vino;
    private Date fecha;
    private Boolean odontologo;

    public CargarAgendaConsultorio() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap hm = (HashMap) o;

        consultorio = (Integer) hm.get("consultorio");
        tipolistado = (Integer) hm.get("filtro");
        vino = (Character) hm.get("vino");
        fecha = (Date) hm.get("fecha");
        odontologo = (Boolean) hm.get("odontologo");

        if(odontologo==null) {
            odontologo=false;
        }
    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);
            Date hoy = fecha;
            
            String filtro = "";
            switch (tipolistado) {

                case 0 : filtro = "";
                        break;

                case 1 : filtro = " AND a.actividad = 1";
                        break;

                case 2 : filtro = " AND a.actividad = 2";
                        break;

                case 3: filtro = " AND a.actividad = 15";
                        break;
            }

            String orden = " order by a.agendaPK.fecha desc, a.agendaPK.hora asc";
            String filtroconsultorio = "";

            if(consultorio!=null) {
                filtroconsultorio = " a.agendaPK.consultorio = :consultorio and a.episodio.medico.codigo = 0 AND ";

                if(consultorio==9001) {
                    orden = " order by a.paciente";
                }
            }

            if(odontologo==true) {
            orden = " order by a.episodio.paciente.personas.apellidos, a.episodio.paciente.personas.nombres";
        }

            String qry = "SELECT a FROM Agenda a WHERE " + filtroconsultorio  + " a.vino = :vino AND a.agendaPK.fecha = :hoy " + filtro + orden;
            System.out.println("CargarAgendaConsultorio es : " + qry);
            Query q = em.createQuery(qry);

            if(consultorio!=null)
                q.setParameter ("consultorio", consultorio+"");

            q.setParameter ("vino", 'S');
            q.setParameter("hoy", hoy);
            Collection <Agenda> c = q.getResultList();
            for(Agenda a : c) {
                a.getEpisodio();
                a.getEpisodio().getLibretasdeconducir();
                a.getEpisodio().getCarnetdesalud();
            }
        return(c);
    }
}
