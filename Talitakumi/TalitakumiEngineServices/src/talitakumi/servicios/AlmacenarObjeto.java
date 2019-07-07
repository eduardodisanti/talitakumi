/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class AlmacenarObjeto implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public AlmacenarObjeto() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Boolean res;
        try {
            em.persist(o);
            em.merge(o);
        } catch(Exception ex) {
            res = false;
            System.out.println("ERROR Almacenar Objeto" + ex);
            logger.error("ERROR Almacenar Objeto" + ex);
        }
        //tx.commit();
        res = true;
        return(res);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
