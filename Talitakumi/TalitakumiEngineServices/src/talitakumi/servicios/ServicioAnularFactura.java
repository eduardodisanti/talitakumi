/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Facturas;

/**
 *
 * @author rupertus
 */
public class ServicioAnularFactura  implements AbstractService {

    Integer idfactura;

    EntityManager em;
    Logger logger;


    public ServicioAnularFactura() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

      Boolean res = false;
      idfactura = (Integer) o;

      Facturas f = (Facturas) em.find(Facturas.class, idfactura);
      f.setEntregado("N");
      f.setTipo('C');
      f.setFechaentregado(new Date());
        try {
                em.merge(f);
                em.flush();

                res = true;

        } catch (Exception e) {
                logger.error(new Date() + " Error cambiando el rollo");
                logger.error(e);
                res = false;
        }

      return(res);
    }
}
