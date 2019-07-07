/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Vector;
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
public class ServicioCargarRoles implements AbstractService {

    String id;

    EntityManager em;
    Logger logger;
    private Vector<String> listaroles;


    public ServicioCargarRoles() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        id = (String) o;
        listaroles = new Vector<String>();
        try {
            Query q = em.createNamedQuery("Usuarios.findByUsuario");
            q.setParameter("usuario", id);
            Usuarios u = (Usuarios) q.getSingleResult();
            String s = u.getRol();
            for(int i=0;i<s.length();i++) {
                
                listaroles.add(s.charAt(i)+"");
            }

        } catch (Exception ex) {
            logger.error(ex);
        }

        return(listaroles);
    }
}
