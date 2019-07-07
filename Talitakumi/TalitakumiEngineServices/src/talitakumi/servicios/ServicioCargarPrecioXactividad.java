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
public class ServicioCargarPrecioXactividad implements AbstractService {

    public Integer empresa;
    public Integer actividad;
    public Integer estudio;

    EntityManager em;
    Logger logger;


    public ServicioCargarPrecioXactividad() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        Float precio = null;
        DataParameter dp = (DataParameter) o;
        empresa = (Integer) dp.get("cliente");
        actividad = (Integer) dp.get("actividad");
        estudio = (Integer) dp.get("estudio");

        Query q;
        if(estudio==null) {
            q = em.createQuery("SELECT p FROM Listaprecios p where p.empresa = :empresa and p.actividad = :actividad");
            q.setParameter("actividad", actividad);
        } else {
            q = em.createQuery("SELECT p FROM Listaprecios p where p.empresa = :empresa and p.estudio = :actividad");
            q.setParameter("actividad", estudio);
        }
        q.setParameter("empresa", empresa);

        try {
            Listaprecios lp = (Listaprecios) q.getSingleResult();
            if(lp!=null)
                precio = lp.getPrecio();
        } catch(Exception ex) {}
        return(precio);
    }

}
