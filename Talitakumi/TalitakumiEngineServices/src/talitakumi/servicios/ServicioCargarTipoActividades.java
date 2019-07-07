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
public class ServicioCargarTipoActividades implements AbstractService {

    EntityManager em;
    Logger logger;
    String orden;
    Integer tipo;

    public ServicioCargarTipoActividades() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
        orden = "p.descripcion";
    }

    public void setParameters(Object o) {

        tipo = (Integer) o;

        if(tipo == null || tipo==0) {
            tipo = 1;
        }

    }

    @Override
    public Object invoke(Object o) {

        this.setParameters(o);
        Query q = em.createQuery("SELECT p FROM Tipodeactividad p order by p.descripcion");

        return(q.getResultList());
    }

}
