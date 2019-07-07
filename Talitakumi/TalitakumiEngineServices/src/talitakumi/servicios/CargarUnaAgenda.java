/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import java.util.HashMap;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Libretasdeconducir;

/**
 *
 * @author rupertus
 */
public class CargarUnaAgenda implements AbstractService {

    EntityManager em;
    Logger logger;
    Date fechadesde, fechahasta;
    Integer tipoListado;

    public CargarUnaAgenda() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap hm = (HashMap) o;

        fechadesde = (Date) hm.get("fechadesde");
        fechahasta = (Date) hm.get("fechahasta");
        tipoListado = (Integer) hm.get("tipolistado");

    }

    @Override
    public Object invoke(Object o) {

        //System.out.println("---- invoke de CargarUnaAgenda ----");
            this.setParameters(o);
        //    System.out.println(o.toString());


        Agenda a = (Agenda) o;
        Agenda aa = em.find(Agenda.class, a.getAgendaPK());
        aa.getEpisodio().getLibretasdeconducir();
        aa.getEpisodio().getCarnetdesalud();
        return(aa);
    }
}
