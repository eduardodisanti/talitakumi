/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Estudios;

/**
 *
 * @author rupertus
 */
public class AlmacenarEstudio implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public AlmacenarEstudio() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Estudios e = (Estudios) o;

        if(e.getId()==null) {
            System.out.println("Estudio nuevo " + e);
            em.persist(e);
        } else {
                Estudios e1 = new Estudios();
                e1 = em.find(Estudios.class, e.getId());

                e1.setAnalisisCollection(e.getAnalisisCollection());
                e1.setDescripcion(e.getDescripcion());
                e1.setEpisodiosExAdicionalesCollection(e.getEpisodiosExAdicionalesCollection());
                e1.setEpisodiosestudiosCollection(e.getEpisodiosestudiosCollection());
                e1.setExamenesactividadCollection(e.getExamenesactividadCollection());
                e1.setId(e.getId());
                e1.setLaboratorio(e.getLaboratorio());
                e1.setPreciobase(e.getPreciobase());
                e1.setTagEstudio(e.getTagEstudio());
                e1.setMedico(e.getMedico());

                System.out.println("Estudio existente " + e1 + " " + e1.getLaboratorio());
                em.merge(e1);
                em.persist(e1);
                em.flush();
        }
        
        return(e);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
