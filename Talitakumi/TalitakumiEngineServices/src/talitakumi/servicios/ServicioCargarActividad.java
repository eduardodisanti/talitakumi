/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class ServicioCargarActividad implements AbstractService {

    Integer id;

    EntityManager em;
    Logger logger;


    public ServicioCargarActividad() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

         id = (Integer) o;
        Query q = em.createQuery("SELECT p FROM Actividades p where p.id = " + id);
        System.out.println("****************SELECT p FROM Actividades p where p.id = " + id);

        return(q.getSingleResult());
    }

}
