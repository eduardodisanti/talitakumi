/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Estudioshechos;
import talitakumi.model.core.Medicos;

/**
 *
 * @author rupertus
 */
public class ServicioValidarExamenesLaboratorio implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public ServicioValidarExamenesLaboratorio() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        DataParameter dp = (DataParameter) o;
        Integer supervisor = (Integer) dp.get("supervisor");
        Integer validador = (Integer) dp.get("validador");
        Medicos medico = cargarMedico(validador);

        for(Integer id : (List<Integer>)dp.get("examenes_validados")) {
            Estudioshechos eh = em.find(Estudioshechos.class, id);
            eh.setSupervisor(supervisor);
            em.merge(eh);
            em.persist(eh);
            em.flush();
        }

        for(Integer id : (List<Integer>)dp.get("examenes_no_validados")) {
            Estudioshechos eh = em.find(Estudioshechos.class, id);
            eh.setSupervisor(0);
            em.merge(eh);
        }

        for(Integer id : (List<Integer>)dp.get("analisis_validados")) {
            Analisishechos eh = em.find(Analisishechos.class, id);
            eh.setValidado(medico);
            em.merge(eh);
        }

        for(Integer id : (List<Integer>)dp.get("analisis_no_validados")) {
            Analisishechos eh = em.find(Analisishechos.class, id);
            eh.setValidado(cargarMedico(0));
            em.merge(eh);
        }

        em.flush();

       return(true);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Medicos cargarMedico(Integer num) {
        
        Medicos m = em.find(Medicos.class, num);
        
        return(m);
    }

}
