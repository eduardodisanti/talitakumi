/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios.libretadeconducir;


import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.services.AbstractService;

/**
 *
 * @author rupertus
 */
public class ServicioDeterminarTipoLibreta implements AbstractService {


    Integer tipo;
    private EntityManager em;
    private Logger logger;

    public  ServicioDeterminarTipoLibreta(boolean aspirante, boolean renovacion, boolean homologacion, boolean cambiocat, boolean solohomologa) {


        tipo = 0;

        if(aspirante)
               tipo = 1;
        else
            if(renovacion)
               tipo = 2;
            else
             if(cambiocat)
                 tipo = 3;
             else
                 if(solohomologa)
                    tipo = 5;

       if(homologacion)
                tipo = 4;

     }

    @Override
    public Object invoke() {

        return(tipo);
    }

    @Override
    public void setParameters(Object o) {
        
    }

    @Override
    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
