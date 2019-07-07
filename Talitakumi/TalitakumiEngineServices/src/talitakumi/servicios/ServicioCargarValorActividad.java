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
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Episodios;

/**
 *
 * @author rupertus
 */
public class ServicioCargarValorActividad implements AbstractService {

    Integer id;

    EntityManager em;
    Logger logger;


    public ServicioCargarValorActividad() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        Actividades act = (Actividades) o;
        id = act.getId();

        Query q = em.createQuery("SELECT p FROM Actividades p where p.id = " + id);

        return(((Actividades)q.getSingleResult()).getPrecio());
    }

}
