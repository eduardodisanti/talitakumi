/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Facturas;

/**
 *
 * @author rupertus
 */
public class CargarUnaFacturaPorEpisodio implements AbstractService {

    EntityManager em;
    Logger logger;
    Object result;

    public Object invoke(Object parametros) {

      result = null;

      em = Sesion.getEntitymanager();

      System.out.println("El parametro es " + parametros);
      String str = "SELECT p FROM Facturas p where p.episodio.id = "+(Integer)parametros;
      Facturas p = null;

       try {
           System.out.println("El str es : " + str);
           Query q = em.createQuery(str);
           System.out.println(str);
           p = (Facturas) q.getSingleResult();
           System.out.println("Volvi");
       } catch(Exception e) {

           System.out.println("Error CargarFactura : " + e);
       }
       result = p;

       return(result);
    }
}
