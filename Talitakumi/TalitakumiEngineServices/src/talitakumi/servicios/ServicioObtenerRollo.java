/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;

/**
 *
 * @author rupertus
 */
public class ServicioObtenerRollo  implements AbstractService {

    private Integer emisor;

    EntityManager em;
    Logger logger;


    public ServicioObtenerRollo() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        
        emisor = (Integer) o;
        Query q = em.createQuery("SELECT p FROM Rollos p where p.caja = " + emisor);

        Object res = q.getSingleResult();
        return(res);
    }

}
