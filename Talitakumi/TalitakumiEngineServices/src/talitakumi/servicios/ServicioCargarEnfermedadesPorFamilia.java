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
public class ServicioCargarEnfermedadesPorFamilia implements AbstractService {

    EntityManager em;
    Logger logger;
    String orden;
    private Integer familia;

    public ServicioCargarEnfermedadesPorFamilia() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
        orden = "p.descripcion";
    }


    @Override
    public Object invoke(Object o) {

        familia = (Integer) o;
        Query q = em.createQuery("SELECT p FROM Enfermedades p where p.familia.id = " + familia + " order by " + orden);

        return(q.getResultList());
    }

}
