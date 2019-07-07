/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class CargarAgendaPaciente implements AbstractService {

    EntityManager em;
    Logger logger;
    Date fechadesde, fechahasta;
    Integer paciente;
    

    public CargarAgendaPaciente() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        DataParameter dp = (DataParameter) o;

        fechadesde  = (Date) dp.get("fechadesde");
        fechahasta  = (Date) dp.get("fechahasta");
        paciente = (Integer) dp.get("paciente");

        if(fechadesde==null) {

            fechadesde = new Date();
            fechadesde.setTime(Integer.MAX_VALUE);
        }

        if(fechahasta==null) {

            fechahasta = new Date();
            GregorianCalendar gc = new GregorianCalendar();
            gc.add(Calendar.MONTH, 2);
            fechahasta = gc.getTime();
        }


    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);

            String qry = "SELECT a FROM Agenda a WHERE a.agendaPK.fecha >= :fechadesde and a.agendaPK.fecha <= :fechahasta and a.paciente = :paciente order by a.agendaPK.fecha desc";

            System.out.println("QRY = " + qry + " fd="+fechadesde + " fh=" + fechahasta + " doc="+paciente);
            Query q = em.createQuery(qry);

            q.setParameter ("fechadesde", fechadesde);
            q.setParameter ("fechahasta", fechahasta);
            q.setParameter ("paciente", paciente);

        return(q.getResultList());
    }
}
