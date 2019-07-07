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
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Libretasdeconducir;

/**
 *
 * @author rupertus
 */
public class CargarLibretaDeConducir implements AbstractService {

    EntityManager em;
    Logger logger;


    public CargarLibretaDeConducir() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Episodios id = (Episodios) o;

        Query q = em.createQuery("SELECT l FROM Libretasdeconducir l where l.episodio = " + id.getId());

        Libretasdeconducir ldc = (Libretasdeconducir) q.getSingleResult();

        return(ldc);
    }

}
