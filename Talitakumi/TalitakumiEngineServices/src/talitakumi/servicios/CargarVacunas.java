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
public class CargarVacunas implements AbstractService {

    Integer id;

    private EntityManager em;
    private Logger logger;


    public CargarVacunas() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        id = (Integer) o;
        Query q = em.createQuery("SELECT p FROM Vacunas p");

        return(q.getResultList());
    }

}
