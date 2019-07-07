/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class CargarFeriados implements AbstractService {

    EntityManager em;
    Logger logger;
    Date fechadesde, fechahasta;
    Integer tipoListado;
    String orden;

    public CargarFeriados() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);


            String qry = "SELECT a FROM Feriados a where a.fecha >=:fechadesde";
            
            Query q = em.createQuery(qry);

            q.setParameter ("fechadesde", new Date());

        return(q.getResultList());
    }
}
