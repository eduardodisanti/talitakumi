/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Tipodeactividad;

/**
 *
 * @author rupertus
 */
public class AlmacenarTipoDeActividad implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public AlmacenarTipoDeActividad() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Tipodeactividad ta = (Tipodeactividad) o;

         Tipodeactividad tipo = em.find(Tipodeactividad.class, ta.getId());

        if(tipo==null)
           tipo = new Tipodeactividad();

        tipo.setMoldeagendaCollection(ta.getMoldeagendaCollection());
        tipo.setId(ta.getId());
        tipo.setDescripcion(ta.getDescripcion());

        Boolean res;
        try {
            em.persist(tipo);
            em.merge(tipo);
        } catch(Exception ex) {
            res = false;
            System.out.println("ERROR Almacenar Moldeagenda" + ex);
            logger.error("ERROR Almacenar Moldeagenda" + ex);
        }
        //tx.commit();
        res = true;
        return(res);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
