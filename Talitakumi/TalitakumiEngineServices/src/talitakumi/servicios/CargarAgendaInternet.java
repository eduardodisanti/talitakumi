/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Feriados;

/**
 *
 * @author rupertus
 */
public class CargarAgendaInternet implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Date fechadesde, fechahasta;
    private Integer tipoListado;
    private String orden;
    private List<Feriados> lf;

    public CargarAgendaInternet() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap hm = (HashMap) o;

        fechadesde  = (Date) hm.get("fechadesde");
        fechahasta  = (Date) hm.get("fechahasta");
        tipoListado = (Integer) hm.get("tipolistado");
        orden       = (String) hm.get("orden");

        if(orden==null) {
            orden = "";
        }
    }

    @Override
    public Object invoke(Object o) {

            lf = (List<Feriados>) new CargarFeriados().invoke(new Date());
            this.setParameters(o);

            String condiciones = "";

            if(tipoListado==1)
                condiciones = "";
            else
                if(tipoListado==0)
                   condiciones = " and a.episodio.terminado = 'N'";
                else
                   condiciones = " and a.episodio.terminado <> 'N'";

            String elorden = "a.agendaPK.fecha, a.agendaPK.hora";

            if(orden.equals("alfabetico")) {

                orden = "order by " + elorden; //" order by a.episodio.paciente.personas.apellidos, " +  elorden;
            } else {
                     orden = "order by " + elorden;
            }

            String qry = "SELECT a FROM Agenda a WHERE a.agendaPK.fecha >= :fechadesde and a.agendaPK.fecha <= :fechahasta  and  (a.agendaPK.consultorio ='INET') " + condiciones + " " + orden;
            
            Query q = em.createQuery(qry);

            q.setParameter ("fechadesde", fechadesde);
            q.setParameter ("fechahasta", fechahasta);

            List<Agenda> l = new ArrayList<Agenda>();

            for(Agenda a : (List<Agenda>)q.getResultList()) {

                if(!esFeriado(a.getAgendaPK().getFecha()))
                    l.add(a);
            }

        return(l);
    }

    private boolean esFeriado(Date fecha) {

            boolean encontre = false;
            int i = 0;
            Feriados f;
            while(!encontre && i != lf.size()) {
              
                f = lf.get(i);
                if(sonFechasIguales(fecha, f.getFecha()))
                    encontre = true;
                else
                    i++;
            }

        System.out.println("El día " + fecha + " es feriado " + encontre);
       return(encontre);
    }

    private boolean sonFechasIguales(Date fanot, Date fa) {

        GregorianCalendar anot = new GregorianCalendar();
        GregorianCalendar actual = new GregorianCalendar();

        anot.setTime(fanot);
        anot.set(Calendar.SECOND, 0);
        anot.set(Calendar.MINUTE, 0);
        anot.set(Calendar.HOUR, 0);

        actual.setTime(fa);
        actual.set(Calendar.SECOND, 0);
        actual.set(Calendar.MINUTE, 0);
        actual.set(Calendar.HOUR, 0);

        System.out.println("sonFechasIguales(" + anot.getTime() + "," + actual.getTime() + ") " + actual.get(Calendar.HOUR));

        boolean resp = actual.get(Calendar.YEAR) == anot.get(Calendar.YEAR) &&
                       actual.get(Calendar.MONTH) == anot.get(Calendar.MONTH) &&
                       actual.get(Calendar.DAY_OF_MONTH) == anot.get(Calendar.DAY_OF_MONTH);

        System.out.println("sonFechasIguales(" + anot.getTime() + "," + actual.getTime() + ") " + actual.get(Calendar.HOUR) + " " + resp);
        return(resp);
    }
}
