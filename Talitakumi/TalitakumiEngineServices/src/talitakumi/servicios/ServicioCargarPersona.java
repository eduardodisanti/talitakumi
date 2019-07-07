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
import talitakumi.model.core.Personas;
import talitakumi.model.core.Vencimientosdoc;

/**
 *
 * @author rupertus
 */
public class ServicioCargarPersona implements AbstractService {

    Integer documento;

    EntityManager em;
    Logger logger;


    public ServicioCargarPersona() {
        
        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        documento = (Integer) o;
        Personas p;
        Query q = em.createQuery("SELECT p FROM Personas p where p.documento = " + documento);

        try {
              p = (Personas) q.getSingleResult();
              if(p.getPacientes()!=null) {
                 for(Vencimientosdoc vd : p.getPacientes().getVencimientosdocCollection()) {}
              }
        } catch(Exception e) {

            p=null;
        }
        return(p);
    }

}
