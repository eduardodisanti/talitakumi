/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;

/**
 *
 * @author rupertus
 */
public class ServicioGuardarObjeto implements AbstractService {

    Object p;

    EntityManager em;
    Logger logger;

    Object result;


    public ServicioGuardarObjeto() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {


        p = o;
        this.almacenar();
        return(p);
    }

        public void almacenar() {

        String ellog = new Date() + " : Almacenando object ";


        try {
                em.persist(p);
                em.merge(p);
  
                result = p;
        } catch (Exception e) {
            System.out.println(" --------------- almacenando object " + e);
                logger.error(e);
                logger.error(ellog);
        }

    }
}
