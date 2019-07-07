/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Estudioshechos;

/**
 *
 * @author rupertus
 */
public class CargarEstudioHecho implements AbstractService {

    Integer id;

    EntityManager em;
    Logger logger;


    public CargarEstudioHecho() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        id = (Integer) o;
        Query q = em.createQuery("SELECT p FROM Estudioshechos p where p.id = " + id);

        Estudioshechos eh = (Estudioshechos) q.getSingleResult();
        for(Analisishechos ah : eh.getAnalisishechosCollection()) {}
        
        return(eh);
    }

}
