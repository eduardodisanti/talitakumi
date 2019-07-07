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
import talitakumi.model.core.Moldeagenda;
import talitakumi.model.core.Tipodeactividad;

/**
 *
 * @author rupertus
 */
public class ServicioCargarTipoActividad implements AbstractService {

    Integer id;

    EntityManager em;
    Logger logger;


    public ServicioCargarTipoActividad() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        id = (Integer) o;
        Query q = em.createQuery("SELECT p FROM Tipodeactividad p where p.id = " + id);

        Tipodeactividad ta = (Tipodeactividad) q.getSingleResult();

        System.out.println("TA ES " + ta);
        if(ta!=null)
            for(Moldeagenda m : ta.getMoldeagendaCollection()) {}
        
        return(ta);
    }

}
