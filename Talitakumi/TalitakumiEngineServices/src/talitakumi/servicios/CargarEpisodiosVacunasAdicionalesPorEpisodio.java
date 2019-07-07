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
public class CargarEpisodiosVacunasAdicionalesPorEpisodio implements AbstractService {

    Integer episodio;

    private EntityManager em;
    private Logger logger;


    public CargarEpisodiosVacunasAdicionalesPorEpisodio() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        episodio = (Integer) o;
        Query q = em.createQuery("SELECT e FROM EpisodiosVacunasAdicionales e WHERE e.episodio.id =  " +episodio);

        return(q.getResultList());
    }

}
