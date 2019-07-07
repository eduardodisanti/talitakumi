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
import talitakumi.model.core.Episodios;

/**
 *
 * @author victoria
 */

public class AlmacenarChequeos implements AbstractService  {

    EntityManager em;
    Logger logger;


    public AlmacenarChequeos() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Episodios id = (Episodios) o;

        Query q = em.createQuery("SELECT e FROM Episodios e where e.tipoepisodio = 15 " + id.getId());

        Episodios c = (Episodios) q.getSingleResult();

        return(c);
    }


}
