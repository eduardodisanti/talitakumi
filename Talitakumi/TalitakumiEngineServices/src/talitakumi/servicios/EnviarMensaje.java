/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.HashMap;
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
public class EnviarMensaje implements AbstractService {

    private EntityManager em;
    private Logger logger;
    Facturas f;

    public EnviarMensaje() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    public Object invoke(Object o) {

        HashMap hm = (HashMap) o;
        Mensajes m = new Mensajes();

        m.setId(null);
        m.setDe((String) hm.get("de"));
        m.setPara((String) hm.get("para"));
        m.setTexto((String) hm.get("texto"));
        m.setLeido('N');
        em.persist(m);
        em.merge(m);

        return(true);
    }

}
