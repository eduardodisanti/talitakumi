/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Estudios;

/**
 *
 * @author rupertus
 */
public class ServicioCargarEstudiosALL implements AbstractService {

    EntityManager em;
    Logger logger;
    String orden;
    Integer documento;

    public ServicioCargarEstudiosALL() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    public void setParameters(Object o) {

        documento = (Integer) o;
    }

    @Override
    public Object invoke(Object o) {

        this.setParameters(o);
        Query q = em.createQuery("SELECT e FROM Estudios e order by e.descripcion");

        List<Estudios> le = new ArrayList<Estudios>();

        for(Estudios e : (List<Estudios>)q.getResultList()) { // TODO PONER UNA MARCA EN LA BASE

            if(!e.getId().equals(100) &&
               !e.getId().equals(99) &&
               !e.getId().equals(8) &&
               !e.getId().equals(23) &&
               !e.getId().equals(20)
              )
            le.add(e);
        }
        return(le);
    }

}
