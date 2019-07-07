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
public class CargarMensajes implements AbstractService {

    EntityManager em;
    Logger logger;
    String para;

    public CargarMensajes() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        para = (String) o;
    }

    @Override
    public Object invoke(Object o) {

        //System.out.println("---- invoke de CargarFacturas ----");
        //    System.out.println(o.toString());
            this.setParameters(o);

            String qry = "SELECT f FROM Mensajes f WHERE f.para = :para and f.para >= '" + para + "' and f.leido = 'N'";

            Query q = em.createQuery(qry);

            //q.setParameter ("fechadesde", xfechadesde);
            //q.setParameter ("fechahasta", xfechahasta);
            q.setParameter ("para", para);

        return(q.getResultList());
    }
}
