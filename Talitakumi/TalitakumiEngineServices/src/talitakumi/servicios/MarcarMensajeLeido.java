/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Mensajes;

/**
 *
 * @author rupertus
 */
public class MarcarMensajeLeido implements AbstractService {

    private EntityManager em;
    private Logger logger;
    Facturas f;

    public MarcarMensajeLeido() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    public Object invoke(Object o) {

        Integer idmsg = (Integer) o;
        boolean res = false;
        Mensajes m = em.find(Mensajes.class, idmsg);


        if(m!=null) {
            m.setLeido('S');
            em.persist(m);
            em.merge(m);
            res = true;
            System.out.println("Marcado el mensaje " + idmsg);
        } else {
              res = false;
              System.out.println("No se encuentra el mensaje " + idmsg);
          }

        return(res);
    }
}
