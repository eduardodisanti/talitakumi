/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Listaprecios;

/**
 *
 * @author rupertus
 */
public class ServicioAlmacenarPrecioXactividad implements AbstractService {

    Integer empresa;
    Integer actividad;
    Float precio;
    String tipo;

    EntityManager em;
    Logger logger;


    public ServicioAlmacenarPrecioXactividad() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        DataParameter dp = (DataParameter) o;
        empresa = (Integer) dp.get("cliente");
        actividad = (Integer) dp.get("actividad");
        tipo = (String) dp.get("tipo");
        precio = (Float) dp.get("precio");

        Query q;
        if(tipo.equals("A"))
           q = em.createQuery("SELECT p FROM Listaprecios p where p.empresa = :empresa and p.actividad = :actividad");
        else
           q = em.createQuery("SELECT p FROM Listaprecios p where p.empresa = :empresa and p.estudio = :actividad");

        q.setParameter("empresa", empresa);
        q.setParameter("actividad", actividad);

        Listaprecios lp=null;
        try {
            lp = (Listaprecios) q.getSingleResult();
            } catch(Exception ex) {
                System.out.println("Error almacenando precio x actividad = " + ex);
          }
          if(lp==null) {
                
                lp = new Listaprecios();
                lp.setId(null);
                lp.setEmpresa(empresa);
                if(tipo.equals("A")) {
                    lp.setActividad(actividad);
                    lp.setEstudio(null);
                } else {
                        lp.setEstudio(actividad);
                        lp.setActividad(null);
                  }
            }

            System.out.println("Almacenando : " + lp.getEmpresa() + " - " + lp.getEstudio() + " " + precio);
            lp.setPrecio(precio);
            em.persist(lp);
            em.merge(lp);
            em.flush();
        
        return(precio);
    }

}
