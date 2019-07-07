
//Servicio que trae los episodios con un tipoepisodio pasado como parámetro.

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
public class CargarEpisodiosPorTipo implements AbstractService {

    EntityManager em;
    Logger logger;
    DataParameter dp;
    Episodios episodio;
    Integer tipo_episodio;
    Date fechadesde, fechahasta;

    public CargarEpisodiosPorTipo() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        dp = (DataParameter)o;

        tipo_episodio = (Integer) dp.get("tipo_episodio");
//        fechadesde = (Date) dp.get("fechadesde");
//        fechahasta = (Date) dp.get("fechahasta");


//        String qry = "SELECT e FROM Episodios e WHERE e.tipoepisodio.id = :tipo_episodio AND e.fecha <= :fechadesde AND e.fecha >= :fechahasta";
        String qry = "SELECT e FROM Episodios e WHERE e.tipoepisodio.id = :tipo_episodio";

        Query q = em.createQuery(qry);

        q.setParameter("tipo_episodio", tipo_episodio);
//        q.setParameter("fechadesde", fechadesde);
//        q.setParameter("fechahasta", fechahasta);
         
        List<Episodios> z = q.getResultList();
        
//        for(Episodios e: z){
//            if(e.getFacturasCollection()!=null){
//                System.out.print("El episodio "+e);
//                System.out.println(" tiene una coleccion de facturas .");
//                for(Facturas factura: e.getFacturasCollection()){};
//            }
       // }
        return(z);

    }
}
