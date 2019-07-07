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
public class ServicioCargarClientes implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private String orden;
    private String condiciones;

    public ServicioCargarClientes() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
        orden = "p.nombre";
    }

    public void setParameters(Object o) {

        String x = (String) o;

        if(x != null) {
            condiciones =  "where p.habilitado='" + o + "'";
        } else {
            condiciones="";
        }

    }

    @Override
    public Object invoke(Object o) {

        this.setParameters(o);
        Query q = em.createQuery("SELECT p FROM Clientes p " + condiciones + " order by p.nombre");

        return(q.getResultList());
    }

}
