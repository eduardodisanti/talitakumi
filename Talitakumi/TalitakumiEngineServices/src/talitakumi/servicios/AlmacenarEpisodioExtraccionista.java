/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;


/**
 *
 * @author victoria
 */

public class AlmacenarEpisodioExtraccionista {

    private EntityManager em;
    private Logger logger;


    public AlmacenarEpisodioExtraccionista(){

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    public void invoke(){}

}
