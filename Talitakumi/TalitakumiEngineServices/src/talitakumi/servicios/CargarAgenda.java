/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import java.util.HashMap;
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
public class CargarAgenda implements AbstractService {

    EntityManager em;
    Logger logger;
    Date fechadesde, fechahasta;
    Integer tipoListado;
    String orden;

    public CargarAgenda() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap<String, Object> hm = (HashMap<String, Object>) o;

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

            //and  (a.agendaPK.consultorio <='9000' or  a.agendaPK.consultorio >='9999')
            String qry = "SELECT a FROM Agenda a WHERE a.agendaPK.fecha >= :fechadesde and a.agendaPK.fecha <= :fechahasta   " + " " + orden;

            System.out.println("======> ORDENAR AGENDA " + qry);
            Query q = em.createQuery(qry);

            q.setParameter ("fechadesde", fechadesde);
            q.setParameter ("fechahasta", fechahasta);

        return(q.getResultList());
    }
}
