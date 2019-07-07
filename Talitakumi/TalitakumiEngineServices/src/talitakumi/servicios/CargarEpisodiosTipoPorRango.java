/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Episodios;

/**
 *
 * @author rupertus
 */
public class CargarEpisodiosTipoPorRango implements AbstractService {

    EntityManager em;
    Logger logger;
    DataParameter dp;
    Episodios episodio;
    Integer tipo_episodio;

    public CargarEpisodiosTipoPorRango() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        dp = (DataParameter)o;
        Date fechadesde, fechahasta;
     
        fechadesde = (Date) dp.get("fechadesde");
        fechahasta = (Date) dp.get("fechahasta");
        tipo_episodio = (Integer) dp.get("tipo_episodio");

                      //System.out.println("***************************Los parámeros son    :   " +dp);

        String qry = "SELECT e FROM Episodios e WHERE e.tipoepisodio.id = :tipo_episodio AND e.fecha <= :fechadesde AND e.fecha >= :fechahasta";

        Query q = em.createQuery(qry);
        q.setParameter("fechadesde", fechadesde);
        q.setParameter("fechahasta", fechahasta);
        q.setParameter("tipo_episodio", tipo_episodio);
                 System.out.println("SE SETEARON PARAMETROS");

        List<Episodios> z = q.getResultList(); //error aca!!!
        

        return(z);
    }
}
