/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Personas;
import talitakumi.engineservices.AbstractService;

/**
 *
 * @author rupertus
 */
public class CargarPersona implements AbstractService {

    EntityManager em;
    Logger logger;
    Object result;

    public Object invoke(Object parametros) {

      result = null;

      em = Sesion.getEntitymanager();

      System.out.println("El parametro es " + parametros);
      String str = "SELECT p FROM Personas p where p.documento = " + (Integer)parametros;
      Personas p = null;

       try {
           Query q = em.createQuery(str);
           try {
            p = (Personas) q.getSingleResult();

            for(Object vd : p.getPacientes().getVencimientosdocCollection()) {};

           } catch(javax.persistence.NoResultException nre) {
               p = null;
           }
       } catch(Exception e) {

           System.out.println("Error CargarPersona : " + e);
       }
       result = p;
       
       return(result);
    }
}
