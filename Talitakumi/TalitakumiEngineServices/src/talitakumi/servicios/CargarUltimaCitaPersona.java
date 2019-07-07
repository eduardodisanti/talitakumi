/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Personas;

/**
 *
 * @author rupertus
 */
public class CargarUltimaCitaPersona implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Date fechadesde, fechahasta;
    private Integer tipoListado;
    private String orden;
    private Personas p;

    public CargarUltimaCitaPersona() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        p = (Personas) o;

    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);

            String qry = "SELECT a FROM Agenda a WHERE a.paciente = :paciente order by a.agendaPK.fecha desc";
            
            Query q = em.createQuery(qry);
            q.setMaxResults(1);
            q.setParameter("paciente", p.getId());

            Object resp = null;
            try {
                resp = q.getSingleResult();
            } catch (Exception ex) {
                System.err.println("Error CargarUltimaCitaPersona : " + ex);
            }

        return(resp);
    }
}
