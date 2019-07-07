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
public class CargarAgendaPorEpisodio implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Integer sid;

    public CargarAgendaPorEpisodio() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        sid = (Integer) o;

    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);

            String qry = "SELECT a FROM Agenda a WHERE a.episodio.id=:sid order by a.episodio.fecha desc";
            
            Query q = em.createQuery(qry);
            q.setMaxResults(1);
            q.setParameter("sid", sid);

        return(q.getSingleResult());
    }
}
