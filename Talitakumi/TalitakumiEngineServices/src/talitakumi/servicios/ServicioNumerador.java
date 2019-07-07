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
import talitakumi.model.core.Numerador;

/**
 *
 * @author rupertus
 */
public class ServicioNumerador implements AbstractService {

    Integer id;

    EntityManager em;
    Logger logger;


    public ServicioNumerador() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        id = (Integer) o;
        Query q = em.createQuery("SELECT p FROM Numerador p  where p.id = " + id);
        // Numerador n = (Numerador) em.getReference(Numerador.class, id);
        Numerador n = (Numerador) q.getSingleResult();
        // em.lock(n, LockModeType.READ);
        Integer i = n.getValor();
        i++;
        n.setValor(i);
        em.persist(n);
        em.merge(n);
        //em.flush();
        
        return(i);
    }

}
