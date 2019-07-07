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
public class ServicioCargarCliente implements AbstractService {

    EntityManager em;
    Logger logger;
    String orden;
    Integer id;

    public ServicioCargarCliente() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
        orden = "p.nombre";
    }

    public void setParameters(Object o) {

        id = (Integer) o;

        if(id == null || id==0) {
            id = 1;
        }

    }

    @Override
    public Object invoke(Object o) {

        this.setParameters(o);
        Query q = em.createQuery("SELECT p FROM Clientes p where p.id = " + id);

        return(q.getSingleResult());
    }

}
