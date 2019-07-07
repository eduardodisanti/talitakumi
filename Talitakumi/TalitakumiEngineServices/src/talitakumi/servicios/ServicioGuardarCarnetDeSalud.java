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
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Medicos;

/**
 *
 * @author rupertus
 */
public class ServicioGuardarCarnetDeSalud implements AbstractService {

    Carnetdesalud ldc;

    EntityManager em;
    Logger logger;

    Object result;


    public ServicioGuardarCarnetDeSalud() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        ldc = (Carnetdesalud) o;
        this.almacenar();
        return(ldc);
    }

    public void almacenar() {

        Episodios epi = ldc.getEpisodios();
        //Carnetdesalud cds = em.find(Carnetdesalud.class, epi.getId());

        try {
                Medicos m = em.find(Medicos.class, Sesion.getNumeromedico());
                epi.setMedico(m);
                epi.setCarnetdesalud(ldc);
                em.persist(epi);
                em.merge(epi);
        } catch (Exception ex) {
             System.out.println("-------- Error es : " + ex);
        }
    }
}
