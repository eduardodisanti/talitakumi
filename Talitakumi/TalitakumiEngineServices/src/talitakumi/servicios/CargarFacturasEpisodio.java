
//Se le pasa un episodio como parámetro y trae toda la collección de facturas para ese episodio.

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Episodios;

/**
 *
 * @author rupertus
 */

public class CargarFacturasEpisodio implements AbstractService {

    EntityManager em;
    Logger logger;


    public CargarFacturasEpisodio() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Episodios epi = (Episodios) o;


        Episodios e = em.find(Episodios.class, epi.getId());

        return(e.getFacturas());
    }

}
