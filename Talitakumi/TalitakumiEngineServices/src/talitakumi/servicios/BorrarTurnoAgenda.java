/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;

/**
 *
 * @author rupertus
 */
public class BorrarTurnoAgenda implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Date fecha;
    private String hora;
    private String consultorio;
    private Integer turno;

    public BorrarTurnoAgenda() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        DataParameter dp = (DataParameter)o;
        fecha       = (Date) dp.get("fecha");
        hora        = (String) dp.get("hora");
        consultorio = (String) dp.get("consultorio");
        turno       = (Integer) dp.get("turno");
    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);

            String qry = "SELECT a FROM Agenda a WHERE a.agendaPK.fecha=:fecha and a.agendaPK.hora=:hora and a.agendaPK.consultorio=:consultorio and a.agendaPK.turno=:turno";

            Query q = em.createQuery(qry);
            q.setParameter("fecha", fecha);
            q.setParameter("hora", hora);
            q.setParameter("consultorio", consultorio);
            q.setParameter("turno", turno);

            Agenda a = (Agenda) q.getSingleResult();
            em.remove(a);
            em.flush();

        return(a);
    }
}
