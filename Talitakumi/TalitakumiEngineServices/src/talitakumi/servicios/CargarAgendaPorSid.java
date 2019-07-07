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
public class CargarAgendaPorSid implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Long sid;

    public CargarAgendaPorSid() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        sid = (Long) o;

    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);

            String qry = "SELECT a FROM Agenda a WHERE a.sesid=:sid";
            
            Query q = em.createQuery(qry);
            q.setMaxResults(1);
            q.setParameter("sid", sid);

        return(q.getSingleResult());
    }
}
