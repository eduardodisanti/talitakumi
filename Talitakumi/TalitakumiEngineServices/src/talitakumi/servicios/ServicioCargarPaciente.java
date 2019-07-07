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

/**
 *
 * @author rupertus
 */
public class ServicioCargarPaciente implements AbstractService {

    Integer cedula;

    EntityManager em;
    Logger logger;
    String orden;

    public ServicioCargarPaciente() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
        orden = "p.descripcion";
    }


    @Override
    public Object invoke(Object o) {

        cedula = (Integer) o;
        Query q = em.createQuery("SELECT p FROM Pacientes p where p.id = " + cedula);

        return(q.getSingleResult());
    }

}
