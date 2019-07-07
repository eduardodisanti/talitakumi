/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Facturas;

/**
 *
 * @author rupertus
 */
public class CargarAgendaPaga implements AbstractService {

    EntityManager em;
    Logger logger;
    Date fechadesde, fechahasta;
    Integer tipoListado;
    String orden;
    Boolean paga;

    public CargarAgendaPaga() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap hm = (HashMap) o;

        fechadesde  = (Date) hm.get("fechadesde");
        fechahasta  = (Date) hm.get("fechahasta");
        tipoListado = (Integer) hm.get("tipolistado");
        orden       = (String) hm.get("orden");
        paga = (Boolean)hm.get("paga");

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

            String qry = "SELECT a FROM Agenda a WHERE a.agendaPK.fecha >= :fechadesde and a.agendaPK.fecha <= :fechahasta  and  (a.agendaPK.consultorio <='9000' or  a.agendaPK.consultorio >='9999') " + condiciones + " " + orden;

            Query q = em.createQuery(qry);

            q.setParameter ("fechadesde", fechadesde);
            q.setParameter ("fechahasta", fechahasta);

            List<Agenda> z = q.getResultList();

            List<Agenda> listaaux = new ArrayList<Agenda>();

            for(Agenda a : z) {
             Boolean pago = null;

             if(a.getEpisodio().getFacturas()!=null) {
                 Facturas f = a.getEpisodio().getFacturas();

                 if(pago== null)
                      pago = f.getEntregado().equals("S");
                 pago = pago && f.getEntregado().equals("S");
                 System.out.println("PAGO ES " +pago);

                if(pago==null)
                   pago = false;
             
                if(pago)
                    listaaux.add(a);
             }
            }

        System.out.println(listaaux.size());
        return(listaaux);

    }
}
