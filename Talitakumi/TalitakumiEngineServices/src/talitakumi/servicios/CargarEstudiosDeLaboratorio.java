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
public class CargarEstudiosDeLaboratorio implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private int actividad;

    public CargarEstudiosDeLaboratorio() {

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

            String qry = "SELECT ea FROM Examenesactividad ea where ea.tipoactividad.id = :actividad order by ea.examen.descripcion";
            //String qry = "SELECT f FROM Estudios f where f.order by f.descripcion";

            Query q = em.createQuery(qry);
            q.setParameter("actividad", actividad);
            //q.setParameter ("fechadesde", xfechadesde);
            //q.setParameter ("fechahasta", xfechahasta);
            //q.setParameter ("para", para);

        return(q.getResultList());
    }
}
