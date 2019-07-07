/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;


/**
 *
 * @author rupertus
 */
public class ServicioAlmacenarLibreta implements AbstractService {

    Logger logger;
    EntityManager em;
    Agenda agenda;
    boolean res;

    public Object invoke(Object o) throws Exception {

            em = Sesion.getEntitymanager();
            logger = Sesion.getLogger();
            res = false;
            agenda = (Agenda) o;
                EntityTransaction tx = em.getTransaction();

                tx.begin();
                em.persist(agenda);
                em.merge(agenda);
//                AbstractLocalService sd = new LocalServiceDelegator().getService("ServicioFacturar");
//                sd.setParameters(agenda.getEpisodio());
//                sd.invoke();

                tx.commit();

                return(true);
    }

    public Object getResultado() {
        return res;
    }
}
