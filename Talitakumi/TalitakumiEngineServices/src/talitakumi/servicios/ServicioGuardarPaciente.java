/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;

/**
 *
 * @author rupertus
 */
public class ServicioGuardarPaciente implements AbstractService {

    Pacientes p;

    EntityManager em;
    Logger logger;

    Object result;


    public ServicioGuardarPaciente() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {


        p = (Pacientes) o;
        this.almacenar();
        return(p);
    }

        public void almacenar() {

        String ellog = new Date() + " : Almacenando paciente";


        try {
                Personas pers = p.getPersonas();
                em.persist(pers);
                em.merge(pers);
                em.flush();

                p.setId(pers.getId());
                em.persist(p);
                em.merge(p);
                em.flush();

  
                result = p;
        } catch (Exception e) {
            System.out.println(" --------------- almacenando paciente " + e);
                logger.error(e);
                logger.error(ellog);
        }

    }
}
