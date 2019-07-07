/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Feriados;

/**
 *
 * @author rupertus
 */
public class MarcarFeriado implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public MarcarFeriado() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Boolean res;
        Date d = (Date) o;

        Feriados f = new Feriados();
        f.setFecha(d);
        try {
            em.persist(f);
            em.merge(f);
            em.flush();
        } catch(Exception ex) {
            res = false;
            System.out.println("ERROR Almacenar Objeto" + ex);
            logger.error("ERROR Almacenar Objeto" + ex);
        }
        //tx.commit();
        res = true;
        return(res);
    }
}
