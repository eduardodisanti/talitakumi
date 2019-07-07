/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class CargarEstudiosLaboratorioPorPaciente implements AbstractService {

    EntityManager em;
    Logger logger;
    String orden;
    Integer documento;

    public CargarEstudiosLaboratorioPorPaciente() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    public void setParameters(Object o) {

        documento = (Integer) o;
    }

    @Override
    public Object invoke(Object o) {

        this.setParameters(o);
        Query q = em.createQuery("SELECT e FROM Estudioshechos e where e.episodio.paciente.id = :documento order by e.episodio.fecha");
        q.setParameter("documento", documento);
        return(q.getResultList());
    }

}
