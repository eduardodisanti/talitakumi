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
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Usuarios;

/**
 *
 * @author rupertus
 */
public class AlmacenarUsuario implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public AlmacenarUsuario() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Boolean nuevo = false;
        Usuarios e = (Usuarios) o;
        Query q = (Query) em.createNamedQuery("Usuarios.findByUsuario");
        q.setParameter("usuario", e.getUsuario());
        
        Usuarios e1 = null;
        try { 
            e1 = (Usuarios) q.getSingleResult();
        } catch(Exception ex) {
            
        }

        if(e1 == null) {

            e1 = new Usuarios();
            nuevo = true;
        }
        
        e1.setCargo(e.getCargo());
        e1.setClave(e.getClave());
        e1.setEmail(e.getEmail());
        e1.setFacturasCollection(e.getFacturasCollection());
        e1.setFuncionario(e.getFuncionario());
        e1.setMedico(e.getMedico());
        e1.setNivel(e.getNivel());
        e1.setRol(e.getRol());
        e1.setUsuario(e.getUsuario());

        System.out.println("Usuario existente " + e1 + " " + e1.getUsuario());
        
        if(nuevo) {
            em.persist(e1);
        } else {
            em.merge(e1);
        }
        em.flush();

       return(e);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
