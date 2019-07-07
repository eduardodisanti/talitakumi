/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;


import java.util.HashMap;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;

/**
 *
 * @author rupertus
 */
public class ServicioDeterminarTipoLibreta implements AbstractService {


    Integer tipo;
    private EntityManager em;
    private Logger logger;

    private void determinarTipoLibreta(boolean aspirante, boolean renovacion, boolean homologacion, boolean cambiocat, boolean solohomologa) {


        tipo = 0;

        if(aspirante)
               tipo = 1;
        else
            if(renovacion)
               tipo = 2;
         else
            if(homologacion)
                tipo = 4;
            else
             if(cambiocat)
                 tipo = 3;
             else
                 if(solohomologa)
                    tipo = 5;
     }

    @Override
    public Object invoke(Object o) {

        setParameters(o);
        
        HashMap <String, Boolean> params = (HashMap<String, Boolean>) o;

        Boolean aspirante = params.get("aspirante");
        Boolean renovacion = params.get("renovacion");
        Boolean homologacion = params.get("homologacion");
        Boolean cambiocat = params.get("cambiocat");
        Boolean solohomologa = params.get("solohomologa");
        
        this.determinarTipoLibreta(aspirante, renovacion, homologacion, cambiocat, solohomologa);
        return(tipo);
    }

    public void setParameters(Object o) {
        
    }
}
