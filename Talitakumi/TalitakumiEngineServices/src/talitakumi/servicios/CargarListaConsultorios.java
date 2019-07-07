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
public class CargarListaConsultorios implements AbstractService {

    EntityManager em;
    Logger logger;

    public CargarListaConsultorios() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {
        Query q = em.createQuery("SELECT p FROM Consultorios p where p.id < 800 and p.id > 0 order by p.id");

        return(q.getResultList());
    }

}
