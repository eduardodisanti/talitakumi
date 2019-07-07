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
import talitakumi.model.core.Consultoriosactivos;

/**
 *
 * @author rupertus
 */
public class CambiarConsultorioActivo implements AbstractService {

    Integer id;

    EntityManager em;
    Logger logger;


    public CambiarConsultorioActivo() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        Boolean result;

        id = (Integer) o;
        result = true;
        Query q = em.createQuery("SELECT p FROM Consultoriosactivos p  where p.consultorio = " + id);
        Consultoriosactivos n = (Consultoriosactivos) q.getSingleResult();

        if(n.getActivo()=='S')
          n.setActivo('N');
        else
          n.setActivo('S');

        em.persist(n);
        em.merge(n);

        return(result);
    }
}
