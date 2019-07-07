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
import talitakumi.model.core.Usuarios;

/**
 *
 * @author rupertus
 */
public class CargarUsuario implements AbstractService {

    String id;

    EntityManager em;
    Logger logger;


    public CargarUsuario() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        id = (String) o;
        Query q = em.createNamedQuery("Usuarios.findByUsuario");
        q.setParameter("usuario", id);
        Usuarios u = (Usuarios) q.getSingleResult();

        Sesion.setNumerousuario(u.getFuncionario());
        Sesion.setNombreUsuario(u.getUsuario());
        Sesion.putVariableEntorno("usuario", u);
        
        return(u);
    }
}
