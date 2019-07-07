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
public class ServicioCargarEstudios implements AbstractService {

    EntityManager em;
    Logger logger;
    String orden;
    Integer documento;

    public ServicioCargarEstudios() {


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

            if(!e.getId().equals(6) &&
               !e.getId().equals(99) &&
               !e.getId().equals(20) &&
               !e.getId().equals(25) &&
               !e.getId().equals(100) &&
               !e.getId().equals(2) &&
               !e.getId().equals(7) &&
               !e.getId().equals(23) &&
               !e.getId().equals(1) &&
               !e.getId().equals(19) &&
               !e.getId().equals(9) &&
               !e.getId().equals(10) &&
               !e.getId().equals(4) &&
               !e.getId().equals(5) &&
               !e.getId().equals(29) &&
               !e.getId().equals(34) &&
               !e.getId().equals(35) &&
               !e.getId().equals(18) &&
               !e.getId().equals(17) &&
               !e.getId().equals(3) &&
               !e.getId().equals(20)
              )
            le.add(e);
        }
        return(le);
    }

}
