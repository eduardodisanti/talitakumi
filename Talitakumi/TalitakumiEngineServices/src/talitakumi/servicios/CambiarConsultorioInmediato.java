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
import talitakumi.model.core.Consultorios;

/**
 *
 * @author rupertus
 */

public class CambiarConsultorioInmediato implements AbstractService {

    Integer id;

    EntityManager em;
    Logger logger;


    public CambiarConsultorioInmediato() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {
 
        Boolean result;

        id = (Integer) o;
        result = true;
        Query q = em.createQuery("SELECT p FROM Consultorios p  where p.id = " + id);
        Consultorios n = (Consultorios) q.getSingleResult();

        if(n.getHabilitadourgente().equals("S"))
          n.setHabilitadourgente("N");
        else
          n.setHabilitadourgente("S");

        em.persist(n);
        em.merge(n);

        return(result);
    }
}
