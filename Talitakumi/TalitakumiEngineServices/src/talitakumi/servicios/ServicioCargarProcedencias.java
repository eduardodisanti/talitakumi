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
public class ServicioCargarProcedencias implements AbstractService {

    EntityManager em;
    Logger logger;
    String orden;

    public ServicioCargarProcedencias() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
        orden = "p.descripcion";
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
        System.out.println("ServicioCargarProcedencias: SELECT p FROM Procedencias p where habilitado='S' order by " + orden);
        Query q = em.createQuery("SELECT p FROM Procedencias p where p.habilitado='S' order by " + orden);

        return(q.getResultList());
    }

}
