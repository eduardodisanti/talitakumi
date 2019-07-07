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
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Examenesactividad;

/**
 *
 * @author rupertus
 */
public class CargarEstudios implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private int actividad;

    public CargarEstudios() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        actividad = (Integer) o;
    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);


            Estudios e;
            Examenesactividad ea;

            String qry = "SELECT e FROM Estudios e order by e.descripcion";
            //String qry = "SELECT f FROM Estudios f where f.order by f.descripcion";

            Query q = em.createQuery(qry);
            //q.setParameter ("fechadesde", xfechadesde);
            //q.setParameter ("fechahasta", xfechahasta);
            //q.setParameter ("para", para);

        return(q.getResultList());
    }
}
