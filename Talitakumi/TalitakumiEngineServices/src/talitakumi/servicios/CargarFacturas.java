/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Facturas;

/**
 *
 * @author rupertus
 */
public class CargarFacturas implements AbstractService {

    EntityManager em;
    Logger logger;
    Date fechadesde, fechahasta;
    Character entregado;

    public CargarFacturas() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap <String, Object> hm = (HashMap) o;

        fechadesde = (Date) hm.get("fechadesde");
        fechahasta = (Date) hm.get("fechahasta");
        entregado = (Character) hm.get("entregado");

    }

    @Override
    public Object invoke(Object o) {

        //System.out.println("---- invoke de CargarFacturas ----");
        //    System.out.println(o.toString());
            this.setParameters(o);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
            String xfechadesde = sdf.format(fechadesde);
            String xfechahasta = sdf.format(fechahasta);

            String qry = "SELECT f FROM Facturas f WHERE f.entregado = :entregado and f.fechaentregado >= :fechadesde and f.fechaentregado <= :fechahasta order by f.episodio.id";

            System.out.println("QUery es : " + qry);
            Query q = em.createQuery(qry);

            q.setParameter ("fechadesde", fechadesde);
            q.setParameter ("fechahasta", fechahasta);
            q.setParameter ("entregado", entregado+"");

            List<Facturas> l = q.getResultList();
            for(Facturas f : l) {

                    f.getEpisodio();


            }
        return(l);
    }
}
