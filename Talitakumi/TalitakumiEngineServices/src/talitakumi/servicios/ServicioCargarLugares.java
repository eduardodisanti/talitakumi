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
public class ServicioCargarLugares implements AbstractService {

    EntityManager em;
    Logger logger;
    String orden;

    public ServicioCargarLugares() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
        orden = "c.descripcion";

    }

    public void setParameters(Object o) {

        String s = (String) o;

        if(s!=null && (!s.equals(""))) {
            orden = s;
        }

    }

    @Override
    public Object invoke(Object o) {
        
        this.setParameters(o);
        
        Query q = em.createQuery("SELECT c FROM Consultorios c order by " + orden);

        return(q.getResultList());
    }

}
