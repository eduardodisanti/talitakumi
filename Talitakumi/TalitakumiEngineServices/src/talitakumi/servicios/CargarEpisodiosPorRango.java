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
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;

/**
 *
 * @author rupertus
 */
public class CargarEpisodiosPorRango implements AbstractService {

    EntityManager em;
    Logger logger;
    Integer actividadaquitar;
    DataParameter dp;

    public CargarEpisodiosPorRango() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Date fechadesde, fechahasta;
        Clientes cli;
        dp = (DataParameter)o;

         fechadesde = (Date) dp.get("fechadesde");
         fechahasta = (Date) dp.get("fechahasta");
         cli        = (Clientes) dp.get("cliente");

         em.refresh(this); // REFESH AL SERVICIO..
         String qry = "SELECT e FROM Episodios e WHERE e.cliente = :idcliente AND e.fecha <= :fdesde AND e.fecha >= :fhasta";

         Query q = em.createQuery(qry);
         q.setParameter("fdesde", fechadesde);
         q.setParameter("fhasta", fechahasta);
         q.setParameter("idcliente", cli.getId());

         List<Episodios> z = q.getResultList();
                  
        return(z);
    }
}
