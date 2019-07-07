/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Episodios;

/**
 *
 * @author victoria
 */
public class CargarEpisodiosPorRangoSinClientes implements AbstractService{

    EntityManager em;
    Logger logger;
    Integer actividadaquitar;
    DataParameter dp;

    public CargarEpisodiosPorRangoSinClientes() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Date fechadesde, fechahasta;
        dp = (DataParameter)o;

         fechadesde = (Date) dp.get("fechadesde");
         fechahasta = (Date) dp.get("fechahasta");
     

         String qry = "SELECT e FROM Episodios e WHERE e.fecha >= :fdesde AND e.fecha <= :fhasta";

         Query q = em.createQuery(qry);
         q.setParameter("fdesde", fechadesde);
         q.setParameter("fhasta", fechahasta);
         

          System.out.println("fdesde =" + fechadesde);
          System.out.println("fhasta =" + fechahasta);
          


         LinkedList<Episodios> z = new LinkedList<Episodios>();
         for(Episodios epi : (List<Episodios>) q.getResultList()) {
                               for(Object t : epi.getEpisodiosestudiosCollection()) {}
                               for(Object t : epi.getEpisodiosvacunasCollection()) {}
          z.add(epi);
         }
         //List<Episodios> z = q.getResultList();

        return(z);
    }
}

