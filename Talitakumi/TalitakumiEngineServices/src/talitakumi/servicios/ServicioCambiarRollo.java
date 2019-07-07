/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Rollos;

/**
 *
 * @author rupertus
 */
public class ServicioCambiarRollo  implements AbstractService {

    private Integer emisor, rollo;

    EntityManager em;
    Logger logger;


    public ServicioCambiarRollo() {

        emisor = 1;
        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

       DataParameter dp = (DataParameter) o;
       rollo = (Integer) dp.get("rollo");
       emisor = (Integer) dp.get("emisor");
      Query q = em.createQuery("SELECT p FROM Rollos p where p.caja = " + emisor);

      Rollos r = (Rollos) q.getSingleResult();
      Boolean res = false;
      
        try {
                r.setId(rollo);
                r.setFechainicio(new Date());
                em.persist(r);
                em.merge(r);
                res = true;

        } catch (Exception e) {
                logger.error(new Date() + " Error cambiando el rollo");
                logger.error(e);
                res = false;
        }

      return(res);
    }
}
