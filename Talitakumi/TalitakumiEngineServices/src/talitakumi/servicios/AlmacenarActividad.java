/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Actividades;

/**
 *
 * @author rupertus
 */
public class AlmacenarActividad implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public AlmacenarActividad() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Actividades e = (Actividades) o;
        Actividades e1 = em.find(Actividades.class, e.getId());

        if(e1 == null) {

            e1 = new Actividades();
        }
        
        e1.setId(e.getId());
        e1.setColor(e.getColor());
        e1.setIcono(e.getIcono());
        e1.setPrecio(e.getPrecio());
        e1.setTipoactividad(e.getTipoactividad());
        e1.setEmisor(e.getEmisor());

        System.out.println("Actividad existente " + e1 + " " + e1.getDescripcion());
        em.merge(e1);
        em.persist(e1);
        em.flush();

       return(e);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
