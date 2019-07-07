/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Carnetdesalud;

/**
 *
 * @author rupertus
 */
public class ObtenerEstadoCarnetDeSaludMedico implements AbstractService {

    EntityManager em;
    Logger logger;
    Carnetdesalud cds;

    public ObtenerEstadoCarnetDeSaludMedico() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        int score = 0;

        cds = (Carnetdesalud) o;
        if(cds.getVigenciamedico()>6) {
            score = 3;
        }
        if(cds.getVigenciamedico()==6) {
            score = 2;
        }

        if(cds.getVigenciamedico()==0) {
            score = 1;
        }

        if(cds.getCausamedica()=='S') {
            score = 1;
        }

        return(score);
    }


}
