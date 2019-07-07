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
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Episodios;

/**
 *
 * @author rupertus
 */

public class CargarCarneDeSalud implements AbstractService {

    EntityManager em;
    Logger logger;


    public CargarCarneDeSalud() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Episodios id = (Episodios) o;

        Query q = em.createQuery("SELECT c FROM Carnetdesalud c where c.episodio = " + id.getId());

        Carnetdesalud cds = (Carnetdesalud) q.getSingleResult();

        return(cds);
    }

}
