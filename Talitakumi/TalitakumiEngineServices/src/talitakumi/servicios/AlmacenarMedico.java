/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Medicos;

/**
 *
 * @author rupertus
 */
public class AlmacenarMedico implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public AlmacenarMedico() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Boolean nuevo = false;
        Medicos e = (Medicos) o;
        Query q = (Query) em.createNamedQuery("Medicos.findByCodigo");
        q.setParameter("codigo", e.getCodigo());
        
        Medicos e1 = null;
        try { 
            e1 = (Medicos) q.getSingleResult();
        } catch(Exception ex) {
            
        }

        if(e1 == null) {

            e1 = new Medicos();
            nuevo = true;
        }
        
        e1.setActivo(e.getActivo());
        e1.setAgendaCollection(e.getAgendaCollection());
        e1.setAnalisishechosCollection(e.getAnalisishechosCollection());
        e1.setCedula(e.getCedula());
        e1.setCodigo(e.getCodigo());
        e1.setEpisodioodontologoCollection(e.getEpisodioodontologoCollection());
        e1.setEpisodiosCollection(e.getEpisodiosCollection());
        e1.setEspecialidad(e.getEspecialidad());
        e1.setExterno(e.getExterno());
        e1.setNombre(e.getNombre());
        System.out.println("Medico existente " + e1 + " " + e1.getCodigo());
        
        if(nuevo) {
            em.persist(e1);
        } else {
            em.merge(e1);
        }
        em.flush();

       return(e);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
