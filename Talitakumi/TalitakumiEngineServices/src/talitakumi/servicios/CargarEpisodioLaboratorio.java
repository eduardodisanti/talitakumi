/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Collection;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Estudioshechos;

/**
 *
 * @author rupertus
 */
public class CargarEpisodioLaboratorio implements AbstractService {

    EntityManager em;
    Logger logger;
    Integer idepisodio;

    public CargarEpisodioLaboratorio() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

         idepisodio = (Integer)o;

         Episodios ep = em.find(Episodios.class, idepisodio);

         Collection <Estudioshechos> ees = ep.getEstudioshechosCollection();
           for(Estudioshechos ee : ees) {

               Collection <Analisishechos> ae = ee.getAnalisishechosCollection();
               for(Analisishechos uae : ae) {
                   uae.getId();
               }

             }


        return(ep);
    }

}
